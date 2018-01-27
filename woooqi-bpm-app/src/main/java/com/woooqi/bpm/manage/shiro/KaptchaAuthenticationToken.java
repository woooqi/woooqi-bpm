package com.woooqi.bpm.manage.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class KaptchaAuthenticationToken extends UsernamePasswordToken{  

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String kaptcha;

    public KaptchaAuthenticationToken (){}  

    public KaptchaAuthenticationToken (String username, String password,boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.kaptcha = captcha;
    } 

    public void setKaptcha(String kaptcha){  
        this.kaptcha= kaptcha;  
    }  

    public String getKaptcha(){  
        return this.kaptcha;  
    }
    
  }
