package com.eveong.myim.route.common;

public enum CacheEnum {

	/**
	 *	每个userId对应服务器
	 */
	RouteWithUserId("RouteWithUserId-");
	
	private String key;

	private int expire;

	CacheEnum(){
		
	}
	
	CacheEnum(String key, int expire){
		this.key = key;
		this.expire = expire;
	}
	
	CacheEnum(String key){
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getExpire() {
		return expire;
	}
}
