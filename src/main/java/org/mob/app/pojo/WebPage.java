/**
 * 
 */
package org.mob.app.pojo;

/**
 * @author LL
 *
 */
public class WebPage {
	private Integer id;
	private Integer siteId;
	private Integer typeId;
	private String name;
	private String storageFolder;
	private String webUrl;
	private String crawlerClass;
	private Integer enabled;
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
	 * @return the siteId
	 */
	public Integer getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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
	 * @return the storageFolder
	 */
	public String getStorageFolder() {
		return storageFolder;
	}
	/**
	 * @param storageFolder the storageFolder to set
	 */
	public void setStorageFolder(String storageFolder) {
		this.storageFolder = storageFolder;
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
	 * @return the enabled
	 */
	public Integer getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WebPage [name=" + name + "]";
	}
}
