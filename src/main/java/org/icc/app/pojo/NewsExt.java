/**
 * 
 */
package org.icc.app.pojo;


/**
 * @author tomwei
 *
 */
public class NewsExt extends News {
	private String parent_id;
	private String enabled;
	private String sort;
	
	
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

}
