package com.titan.manage.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authc.ExcessiveAttemptsException;  
import org.apache.shiro.cache.Cache;  
import org.apache.shiro.cache.CacheManager;

import com.titan.utils.Md5Utils;

import java.util.concurrent.atomic.AtomicInteger;  


public class ShiroCredentialsMatcher extends SimpleCredentialsMatcher {
	
	public static Integer LOGINCOUNT = 0;
	
	private Cache<String, AtomicInteger> passwordRetryCache;  
	  
    public ShiroCredentialsMatcher(CacheManager cacheManager) {  
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");  
    }  

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authenticationToken,AuthenticationInfo authenticationInfo) {
		KaptchaAuthenticationToken token = (KaptchaAuthenticationToken) authenticationToken;
		
		String username = (String) token.getPrincipal();  
        AtomicInteger retryCount = passwordRetryCache.get(username);  
        if (retryCount == null) {  
            retryCount = new AtomicInteger(0);  
            passwordRetryCache.put(username, retryCount);  
        } 
        int loginCount = 5;
        LOGINCOUNT = retryCount.incrementAndGet();
        if ( LOGINCOUNT >= loginCount) {  
            throw new ExcessiveAttemptsException();  
        }  
		
		Object tokenCredentials = Md5Utils.getMD5(toString(toBytes(token.getCredentials())));
		Object accountCredentials = getCredentials(authenticationInfo);
		boolean isMatche =  equals(tokenCredentials, accountCredentials);
		if (isMatche) {  
            passwordRetryCache.remove(username); 
            LOGINCOUNT = 0;
        }  
        return isMatche; 
	}

}