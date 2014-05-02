package org.icc.app.crawler;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.icc.app.common.springmvc.SpringContextHolder;
import org.icc.app.parser.ParseUtils;
import org.icc.app.pojo.Article;
import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.WebPage;
import org.icc.app.service.ArticleService;
import org.icc.app.service.WebPageService;
import org.icc.app.util.DateUtil;
import org.icc.app.util.ServiceTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class NetEaseCrawler extends WebCrawler {
	private final static Logger log = LoggerFactory.getLogger(NetEaseCrawler.class);
	
	private final static int DEFAULT_WEB_ID = 1;
	
	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	private static ArticleService articleService = SpringContextHolder.getBean("articleServiceImpl");
	private static WebPageService webPageService = SpringContextHolder.getBean("webPageServiceImpl");

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches()
				&& href.endsWith(".html");
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		log.debug("Visited URL: " + url); 
		
		String title = "";
		String content = "";
		String publisher = "";
		String dateTime = "";

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();

			List<ParagraphTag> pts = ParseUtils.parseTags(html, ParagraphTag.class, "class", "ep-summary");
			if(!ServiceTools.isEmpty(pts)) {
				for(ParagraphTag p : pts) {
					content = p.getStringText();
				}
			}
			
			if(StringUtils.isEmpty(content)) {
				List<Div> cps = ParseUtils.parseTags(html, Div.class, "id", "endText");
				if(!ServiceTools.isEmpty(cps)) {
					String paraContent = cps.get(0).getStringText();
					
					List<ParagraphTag> pcs = ParseUtils.parseTags(paraContent, ParagraphTag.class);
					
					if(!ServiceTools.isEmpty(pcs)) {
						content = pcs.get(1).getStringText();
					}
				}
			}

			List<HeadingTag> hts = ParseUtils.parseTags(html, HeadingTag.class,	"class", "ep-h1");
			if(!ServiceTools.isEmpty(hts)) {
				for(HeadingTag t : hts) {
					title = t.getStringText();
				}
			}

			List<Div> froms = ParseUtils.parseTags(html, Div.class, "class", "left");
			if(!ServiceTools.isEmpty(froms)) {
				String fromString = "";
				for(Div d : froms) {
					fromString = d.getStringText();
				}
				
				dateTime = fromString.substring(0, 19);
				
				List<LinkTag> linkTags = ParseUtils.parseTags(fromString, LinkTag.class, "target", "_blank");
				if(!ServiceTools.isEmpty(linkTags)) {
					publisher = linkTags.get(0).getStringText();
				}
			}

			Article article = new Article();
			
			String baseUrl = ServiceTools.getBaseUrl(url);
			
			if(checkValidateUrl(baseUrl)) {
				article.setWebId(getWebPageId(baseUrl));
				article.setTitle(title);
				article.setContent(content);
				article.setPublisher(publisher);
				article.setPublishDate(dateTime);
				article.setWebUrl(url);
				article.setCreateDate(DateUtil.getCurrentDate("MM/dd/yyyy HH:mm:ss"));
				
				log.info("Save Article: " + article);
				
				if(check(article)) {
					Criteria example = new Criteria();
					example.put("article", article);
					
					articleService.saveArticle(example);
				}
			}
		}
	}
	
	private int getWebPageId(String url) {
		int id = DEFAULT_WEB_ID;
		
		Criteria example = new Criteria();
		example.put("webUrl", url);
		
		List<WebPage> webPageList = webPageService.selectByExample(example);
		if(!ServiceTools.isEmpty(webPageList)) {
			WebPage webPage = webPageList.get(0);
			id = webPage.getId();
		}
		return id;
	}
	
	private boolean check(Article article) {
		if (StringUtils.isEmpty(article.getTitle())
				|| StringUtils.isEmpty(article.getContent())
				|| StringUtils.isEmpty(article.getWebUrl())) {
			return false;
		}

		return true;
	}
	
	private boolean checkValidateUrl(String url) {
		Map<String, Boolean> map = webPageService.selectUrlMap();
		
		return map.get(url);
	}
}