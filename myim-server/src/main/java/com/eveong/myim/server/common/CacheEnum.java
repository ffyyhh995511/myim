package com.eveong.myim.server.common;

public enum CacheEnum {

	/**
	 *	
	 */
	Login("Login");
	
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
