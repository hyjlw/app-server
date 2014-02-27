package org.icc.app.crawler;

import java.util.List;
import java.util.regex.Pattern;

import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.icc.app.parser.ParseUtils;
import org.icc.app.pojo.Article;
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
	
	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.endsWith(".html");
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
		String subscriber = "";
		String dateTime = "";

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();

			List<ParagraphTag> pts = ParseUtils.parseTags(html, ParagraphTag.class, "class", "ep-summary");
			if(!ServiceTools.isEmpty(pts)) {
				content = pts.get(0).getStringText();
			}

			List<HeadingTag> hts = ParseUtils.parseTags(html, HeadingTag.class,	"class", "ep-h1");
			if(!ServiceTools.isEmpty(hts)) {
				title = hts.get(0).getStringText();
			}

			List<Div> froms = ParseUtils.parseTags(html, Div.class, "class", "left");
			if(!ServiceTools.isEmpty(froms)) {
				String fromString = froms.get(0).getStringText();
				
				dateTime = fromString.substring(0, 19);
				
				List<LinkTag> linkTags = ParseUtils.parseTags(fromString, LinkTag.class, "rel", "nofollow");
				if(!ServiceTools.isEmpty(linkTags)) {
					subscriber = linkTags.get(0).getStringText();
				}
			}

			Article article = new Article();
			article.setTitle(title);
			article.setContent(content);
			article.setSubscriber(subscriber);
			article.setSubscribeDate(dateTime);
			article.setCreateDate(DateUtil.getCurrentDate("MM/dd/yyyy HH:mm:ss"));
		}
	}
}