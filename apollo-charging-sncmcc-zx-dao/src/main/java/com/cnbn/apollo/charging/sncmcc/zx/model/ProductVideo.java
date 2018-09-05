/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DuQiyu
 *
 */
public class ProductVideo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键Id
	 * */
	private int id;
	
	/**
	 * C2接口内容Id(ProgramId)
	 * */
	private String contentId;
	
	/**
	 * 频道Id
	 * */
	private String chnId;
	
	/**
	 * 专辑Id
	 * */
	private String albumId;
	
	/**
	 * 专辑名称
	 * */
	private String albumName;
	
	/**
	 * 剧集Id
	 * */
	private String tvId;
	
	/**
	 * 剧集名称
	 * */
	private String tvName;
	
	private Date createTime;
	
	private Date updateTime;

	public ProductVideo(int id, String contentId, String chnId, String albumId, String albumName, String tvId,
			String tvName, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.contentId = contentId;
		this.chnId = chnId;
		this.albumId = albumId;
		this.albumName = albumName;
		this.tvId = tvId;
		this.tvName = tvName;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public ProductVideo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getTvId() {
		return tvId;
	}

	public void setTvId(String tvId) {
		this.tvId = tvId;
	}

	public String getTvName() {
		return tvName;
	}

	public void setTvName(String tvName) {
		this.tvName = tvName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getChnId() {
		return chnId;
	}

	public void setChnId(String chnId) {
		this.chnId = chnId;
	}
	
}
