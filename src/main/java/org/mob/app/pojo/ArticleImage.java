/** ********************************************************* *
** Function: 
**	
** PGM ID: app-server
** PGM Name: ArticleImage.java
** @author Kats
** @date   27 Mar, 2014
** ********************************************************* */
package org.mob.app.pojo;

/**
 * @author Kats
 *
 * @version 1.0.0
 */
public class ArticleImage {
	private Integer id;
	private Integer articleId;
	private String imgUrl;
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
	 * @return the articleId
	 */
	public Integer getArticleId() {
		return articleId;
	}
	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
