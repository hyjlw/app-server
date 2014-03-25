/**
 * 
 */
package org.icc.app.pojo;


/**
 * @author LL
 *
 */
public class Site {
	private Integer id;
	private String name;
	private String crawlerClass;
	private String siteUrl;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the crawlerClass
	 */
	public String getCrawlerClass() {
		return crawlerClass;
	}
	/**
	 * @param crawlerClass the crawlerClass to set
	 */
	public void setCrawlerClass(String crawlerClass) {
		this.crawlerClass = crawlerClass;
	}
	/**
	 * @return the siteUrl
	 */
	public String getSiteUrl() {
		return siteUrl;
	}
	/**
	 * @param siteUrl the siteUrl to set
	 */
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
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
