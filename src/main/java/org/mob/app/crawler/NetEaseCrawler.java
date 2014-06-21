package org.mob.app.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.mob.app.common.springmvc.SpringContextHolder;
import org.mob.app.parser.ParseUtils;
import org.mob.app.pojo.Article;
import org.mob.app.pojo.ArticleImage;
import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.WebPage;
import org.mob.app.service.ArticleService;
import org.mob.app.service.WebPageService;
import org.mob.app.util.DateUtil;
import org.mob.app.util.ServiceTools;
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
		List<String> imgs = new ArrayList<String>();

		try {
			if (page.getParseData() instanceof HtmlParseData) {
				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String html = htmlParseData.getHtml();
				//log.debug("Parsed html: " + html);

				List<ParagraphTag> pts = ParseUtils.parseTags(html, ParagraphTag.class, "class", "ep-summary");
				if(!ServiceTools.isEmpty(pts)) {
					for(ParagraphTag p : pts) {
						content = p.getStringText();
						log.info("content: " + content);
					}
				}
				
				content = replaceBlank(content);
				
				List<Div> cps = ParseUtils.parseTags(html, Div.class, "id", "endText");
				if(!ServiceTools.isEmpty(cps)) {
					String paraContent = cps.get(0).getStringText();
					log.info("Page's paraContent: " + paraContent);
					if(StringUtils.isBlank(content)) {
						List<TextNode> pcs = ParseUtils.parseTexts(paraContent, TextNode.class);
						if(!ServiceTools.isEmpty(pcs)) {
							content = pcs.get(0).getText();
						}
					}
					log.info("final content: " + content);
					
					List<ParagraphTag> pcs = ParseUtils.parseTags(paraContent, ParagraphTag.class, "class", "f_center");
					log.info("ParagraphTag: " + pcs.size());
					if(!ServiceTools.isEmpty(pcs)) {
						for(ParagraphTag p : pcs) {
							String imgContent = p.getStringText();
							log.info("Image Content: " + imgContent);
							imgs.add(getImageUrl(imgContent));
						}
					}
				}
				log.info("images for the page: " + imgs);
				

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
					log.info("from time string: " + fromString);
					if(!StringUtils.isBlank(fromString)) {
						dateTime = fromString.substring(0, 19);
					}
					
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
						log.info("Saved Article's id: " + article.getId());
						
						if(!ServiceTools.isEmpty(imgs)) {
							for(String src : imgs) {
								Criteria exampleImage = new Criteria();
								ArticleImage articleImage = new ArticleImage();
								articleImage.setArticleId(article.getId());
								articleImage.setImgUrl(src);
								
								log.info("+++++++++save article image+++++++++" + articleImage.getImgUrl());
								exampleImage.put("articleImage", articleImage);
								articleService.saveArticleImage(exampleImage);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			ServiceTools.logException(log, "Parsing HTML error", e);
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
		if (StringUtils.isEmpty(article.getTitle().trim())
				|| StringUtils.isEmpty(article.getContent().trim())
				|| StringUtils.isEmpty(article.getWebUrl().trim())) {
			return false;
		}

		return true;
	}
	
	private boolean checkValidateUrl(String url) {
		Map<String, Boolean> map = webPageService.selectUrlMap();
		
		Boolean bool = map.get(url);
		if(bool == null) {
			bool = false;
		}
		return bool;
	}
	
	private String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|t|r|n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	private String getImageUrl(String imgNode) {
		log.info("--------image node-------" + imgNode);
		
		int start = 0;
		int end = 0;
		String res = "";
		String strIndex = "src=\"";
		start = imgNode.indexOf(strIndex) + strIndex.length();
		if(start > 0) {
			end = imgNode.indexOf("\"", start);
			
			res = imgNode.substring(start, end);
		}
		
		return res;
	}
}