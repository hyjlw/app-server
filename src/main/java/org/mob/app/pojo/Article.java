/**
 * 
 */
package org.mob.app.pojo;

import java.util.List;

/**
 * @author LL
 *
 */
public class Article {
	private Integer id;
	private Integer webId;
	private String webUrl;
	private String title;
	private String content;
	private String publisher;
	private String publishDate;
	private String createDate;
	
	private List<ArticleImage> imgs;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the webId
	 */
	public Integer getWebId() {
		return webId;
	}
	/**
	 * @param webId the webId to set
	 */
	public void setWebId(Integer webId) {
		this.webId = webId;
	}
	/**
	 * @return the webUrl
	 */
	public String getWebUrl() {
		return webUrl;
	}
	/**
	 * @param webUrl the webUrl to set
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the publishDate
	 */
	public String getPublishDate() {
		return publishDate;
	}
	/**
	 * @param publishDate the publishDate to set
	 */
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	/**
	 * @return the imgs
	 */
	public List<ArticleImage> getImgs() {
		return imgs;
	}
	/**
	 * @param imgs the imgs to set
	 */
	public void setImgs(List<ArticleImage> imgs) {
		this.imgs = imgs;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Article [webUrl=" + webUrl + ", title=" + title + ", content="
				+ content + ", publisher=" + publisher + "]";
	}
}
