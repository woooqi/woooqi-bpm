package com.titan.manage.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class KaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_SESSION = "com.nt.kaptcha";
	private String kaptchaParam = "kaptcha";

	public String getKaptchaParam() {
		return kaptchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getKaptchaParam());
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new KaptchaAuthenticationToken(username, password, rememberMe, host, captcha);
	}

	protected void doCaptchaValidate(HttpServletRequest request, KaptchaAuthenticationToken token) {
		String captcha = (String) request.getSession().getAttribute(DEFAULT_CAPTCHA_SESSION);

		if (StringUtils.isEmpty(token.getKaptcha()) || !token.getKaptcha().equalsIgnoreCase(captcha)) {
			throw new CaptchaIncorrectException("验证码错误！");
		}
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		KaptchaAuthenticationToken token = (KaptchaAuthenticationToken) createToken(request, response);
		final Map<Object, Object> attributes = new LinkedHashMap<Object, Object>();
		try {
			if(ShiroCredentialsMatcher.LOGINCOUNT >= 3){
				doCaptchaValidate((HttpServletRequest) request, token);
			}

			Subject subject = getSubject(request, response);

			org.apache.shiro.session.Session session = subject.getSession();
			final Collection<Object> keys = session.getAttributeKeys();
			for (Object key : keys) {
				final Object value = session.getAttribute(key);
				if (value != null) {
					attributes.put(key, value);
				}
			}
			session.stop();

			subject.login(token);

			session = subject.getSession();

			for (final Object key : attributes.keySet()) {
				session.setAttribute(key, attributes.get(key));
			}

			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {

			Subject subject = getSubject(request, response);

			if (subject != null) {
				org.apache.shiro.session.Session session = subject.getSession();
				if (session != null) {
					for (final Object key : attributes.keySet()) {
						session.setAttribute(key, attributes.get(key));
					}
				}
			}

			return onLoginFailure(token, e, request, response);
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {// 不是ajax请求
			issueSuccessRedirect(request, response);
		} else {
			httpServletResponse.setCharacterEncoding("UTF-8");
			PrintWriter out = httpServletResponse.getWriter();
			out.println("{success:true,message:'登入成功'}");
			out.flush();
			out.close();
		}
		return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException exception, ServletRequest request,
			ServletResponse response) {
		if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {// 不是ajax请求
			setFailureAttribute(request, exception);
			return true;
		}
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String message = exception.getClass().getSimpleName();
			if ("IncorrectCredentialsException".equals(message)) {
				out.println("{success:false,message:'密码错误'}");
			} else if ("UnknownAccountException".equals(message)) {
				out.println("{success:false,message:'账号不存在'}");
			} else if ("LockedAccountException".equals(message)) {
				out.println("{success:false,message:'账号被锁定'}");
			} else {
				out.println("{success:false,message:'未知错误'}");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
