/**
 * 
 */
package org.mob.app.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomwei
 *
 */
public class News {
	private String id;
	private String title;
	private String domain;
	private String encoding;
	private String rss_encoding;
	private String regex_img;
	private String regex_content;
	private String regex_content_filter;
	private String regex_url_node;
	private String regex_url_node_full;
	private String rss_url;
	private String is_mobile;
	private String path_node;
	private String path_link;
	private String path_description;
	private String path_thumbnail;
	private List<News> children;
	
	
	public News() {
		
	}


	public News(String id, String title, String domain, String encoding,
			String rss_encoding, String regex_img, String regex_content,
			String regex_content_filter, String regex_url_node,
			String regex_url_node_full, String rss_url, String is_mobile,
			String path_node, String path_link, String path_description,
			String path_thumbnail, List<News> children) {
		this.id = id;
		this.title = title;
		this.domain = domain;
		this.encoding = encoding;
		this.rss_encoding = rss_encoding;
		this.regex_img = regex_img;
		this.regex_content = regex_content;
		this.regex_content_filter = regex_content_filter;
		this.regex_url_node = regex_url_node;
		this.regex_url_node_full = regex_url_node_full;
		this.rss_url = rss_url;
		this.is_mobile = is_mobile;
		this.path_node = path_node;
		this.path_link = path_link;
		this.path_description = path_description;
		this.path_thumbnail = path_thumbnail;
		this.children = children;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public String getRss_encoding() {
		return rss_encoding;
	}


	public void setRss_encoding(String rss_encoding) {
		this.rss_encoding = rss_encoding;
	}


	public String getRegex_img() {
		return regex_img;
	}


	public void setRegex_img(String regex_img) {
		this.regex_img = regex_img;
	}


	public String getRegex_content() {
		return regex_content;
	}


	public void setRegex_content(String regex_content) {
		this.regex_content = regex_content;
	}


	public String getRegex_content_filter() {
		return regex_content_filter;
	}


	public void setRegex_content_filter(String regex_content_filter) {
		this.regex_content_filter = regex_content_filter;
	}


	public String getRegex_url_node() {
		return regex_url_node;
	}


	public void setRegex_url_node(String regex_url_node) {
		this.regex_url_node = regex_url_node;
	}


	public String getRegex_url_node_full() {
		return regex_url_node_full;
	}


	public void setRegex_url_node_full(String regex_url_node_full) {
		this.regex_url_node_full = regex_url_node_full;
	}


	public String getRss_url() {
		return rss_url;
	}


	public void setRss_url(String rss_url) {
		this.rss_url = rss_url;
	}


	public String getIs_mobile() {
		return is_mobile;
	}


	public void setIs_mobile(String is_mobile) {
		this.is_mobile = is_mobile;
	}


	public String getPath_node() {
		return path_node;
	}


	public void setPath_node(String path_node) {
		this.path_node = path_node;
	}


	public String getPath_link() {
		return path_link;
	}


	public void setPath_link(String path_link) {
		this.path_link = path_link;
	}


	public String getPath_description() {
		return path_description;
	}


	public void setPath_description(String path_description) {
		this.path_description = path_description;
	}


	public String getPath_thumbnail() {
		return path_thumbnail;
	}


	public void setPath_thumbnail(String path_thumbnail) {
		this.path_thumbnail = path_thumbnail;
	}


	public List<News> getChildren() {
		if(children == null) {
			children = new ArrayList<News>();
		}
		return children;
	}


	public void setChildren(List<News> children) {
		this.children = children;
	}
}
