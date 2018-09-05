/**
 * 
 */
package com.cnbn.apollo.charging.sncmcc.zx.bean;

import java.io.Serializable;

/**
 * @author DuQiyu
 *
 */
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 播放地址
	 * */
	private String playUrl;

	public OrderBean(String playUrl) {
		super();
		this.playUrl = playUrl;
	}

	public OrderBean() {
		super();
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

}
