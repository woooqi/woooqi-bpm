package com.titan.manage.shiro;

import java.io.Serializable;

import org.apache.shiro.util.SimpleByteSource;

public class ShiroSimpleByteSource extends SimpleByteSource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5753551885990158480L;

	public ShiroSimpleByteSource(byte[] bytes) {
		super(bytes);
	}

}
