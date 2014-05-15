/**
 * 
 */
package org.mob.app.pojo;

/**
 * @author LL
 *
 */
public class NewsType {
	private Integer id;
	private String name;
	private Integer enabled;
	private Integer isSubscribe;
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
	 * @return the isSubsribe
	 */
	public Integer getIsSubscribe() {
		return isSubscribe;
	}
	/**
	 * @param isSubsribe the isSubsribe to set
	 */
	public void setIsSubscribe(Integer isSubscribe) {
		this.isSubscribe = isSubscribe;
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
