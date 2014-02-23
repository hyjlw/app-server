/**
 * 
 */
package org.icc.app.pojo;

/**
 * @author LL
 *
 */
public class Article {
	private Integer id;
	private Integer webId;
	private String webUrl;
	private String content;
	private String subscriber;
	private String subscribeDate;
	private String createDate;
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
	 * @return the subscriber
	 */
	public String getSubscriber() {
		return subscriber;
	}
	/**
	 * @param subscriber the subscriber to set
	 */
	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}
	/**
	 * @return the subscribeDate
	 */
	public String getSubscribeDate() {
		return subscribeDate;
	}
	/**
	 * @param subscribeDate the subscribeDate to set
	 */
	public void setSubscribeDate(String subscribeDate) {
		this.subscribeDate = subscribeDate;
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
}
