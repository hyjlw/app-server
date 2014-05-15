package org.mob.app.parser;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParseUtils {

	/**
	 * 提取具有某个属性值的标签列表
	 * 
	 * @param html
	 *            被提取的html文本
	 * @param tagType
	 *            标签的类型
	 * @param attributeName
	 *            某个属性的名称
	 * @param attributeValue
	 *            属性应取的值
	 * @return
	 */
	public static <T extends TagNode> List<T> parseTags(String html,
			final Class<T> tagType, final String attributeName,
			final String attributeValue) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);

			NodeList tagList = parser.parse(new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					if (node.getClass() == tagType) {
						T tn = (T) node;
						String attrValue = tn.getAttribute(attributeName);
						if (attrValue != null
								&& attrValue.equals(attributeValue)) {
							return true;
						}
					}
					return false;
				}
			});
			List<T> tags = new ArrayList<T>();
			for (int i = 0; i < tagList.size(); i++) {
				T t = (T) tagList.elementAt(i);
				tags.add(t);
			}
			return tags;
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 提取具有某个属性值的标签
	 * 
	 * @param html
	 *            被提取的html文本
	 * @param tagType
	 *            标签的类型
	 * @param attributeName
	 *            某个属性的名称
	 * @param attributeValue
	 *            属性应取的值
	 * @return
	 */
	public static <T extends TagNode> T parseTag(String html,
			final Class<T> tagType, final String attributeName,
			final String attributeValue) {
		List<T> tags = parseTags(html, tagType, attributeName, attributeValue);
		if (tags != null && tags.size() > 0) {
			return tags.get(0);
		}
		return null;
	}

	/**
	 * 提取具有某个属性值的标签
	 * 
	 * @param html
	 *            被提取的html文本
	 * @param tagType
	 *            标签的类型
	 * @return
	 */
	public static <T extends TagNode> T parseTag(String html,
			final Class<T> tagType) {
		return parseTag(html, tagType, null, null);
	}

	/**
	 * 提取具有某个属性值的标签列表
	 * 
	 * @param html
	 *            被提取的html文本
	 * @param tagType
	 *            标签的类型
	 * @return
	 */
	public static <T extends TagNode> List<T> parseTags(String html,
			final Class<T> tagType) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);

			NodeList tagList = parser.parse(new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					return true;
				}
			});
			List<T> tags = new ArrayList<T>();
			for (int i = 0; i < tagList.size(); i++) {
				T t = (T) tagList.elementAt(i);
				tags.add(t);
			}
			return tags;
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static <T extends TextNode> List<T> parseTexts(String html,
			final Class<T> textType) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);

			NodeList tagList = parser.parse(new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					return true;
				}
			});
			List<T> tags = new ArrayList<T>();
			for (int i = 0; i < tagList.size(); i++) {
				if(tagList.elementAt(i) instanceof TextNode) {
					T t = (T) tagList.elementAt(i);
					tags.add(t);
				}
			}
			return tags;
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void main(String []args) {
		String str = "<p class=\"f_center\"><img alt=\"有理由获得高人气 全新福美来M5价格快评\" src=\"http://img5.cache.netease.com/auto/2014/5/9/201405090249587357d_550.jpg\"><br></p>";
//		String str = "<p>I love <strong>Annie</strong>!!!!</p>";
		List<ParagraphTag> pcs = ParseUtils.parseTags(str, ParagraphTag.class);
	
		String imgStr = pcs.get(0).getStringText();
		System.out.println(imgStr);
		
		List<ImageTag> imgs = ParseUtils.parseTags(imgStr, ImageTag.class);
		
		System.out.println(imgs.get(0).getImageURL());
	}
}
