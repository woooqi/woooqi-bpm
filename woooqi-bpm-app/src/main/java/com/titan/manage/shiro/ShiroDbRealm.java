package com.titan.manage.shiro;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.titan.entity.organization.User;
import com.titan.service.organization.UserService;

public class ShiroDbRealm extends AuthorizingRealm {
	
	@Autowired
	private ShiroCredentialsMatcher shiroCredentialsMatcher;
	@Autowired
	private UserService userService;
	@Autowired
	private EhCacheManager cacheManager; 

	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(shiroCredentialsMatcher);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		
        User user = userService.getByLoginName(((User)principalCollection.fromRealm(getName()).iterator().next()).getLoginName());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); 
        if(user!=null){  
            info.addRole(user.getRoleNames());  
        }  
        return info; 
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        
		KaptchaAuthenticationToken token = (KaptchaAuthenticationToken) authenticationToken;  
		if(token.isRememberMe()){
			token.setRememberMe(true);
		}
		User user = userService.getByLoginName(token.getUsername());
		if(user != null){  
        	return new SimpleAuthenticationInfo(user, user.getPassword(), new ShiroSimpleByteSource(user.getName().getBytes()), getName());
        } 
		return null;

	}
	
	public void clearAuthorizationInfoCached() {
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		String username = user.getLoginName();
		
		SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
		simplePrincipalCollection.add(user, getName());
		clearCachedAuthorizationInfo(simplePrincipalCollection);
		
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
            	if(key.equals(username))
                cache.remove(key);
            }
        }
    }
	
	public void clearAuthenticationInfoCached() {
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		String username = user.getLoginName();
		
		SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
		simplePrincipalCollection.add(user, getName());
		clearCachedAuthenticationInfo(simplePrincipalCollection);
		
		Cache<Object, AuthenticationInfo> cache = getAuthenticationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
            	if(key.equals(username))
                cache.remove(key);
            }
        }
	}

}
