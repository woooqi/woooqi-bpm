package com.titan.controller.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.titan.manage.shiro.KaptchaFormAuthenticationFilter;

@RestController
@Scope("session")
public class KaptchaController {
	
	@Autowired
	private DefaultKaptcha kaptchaProducer;

	@RequestMapping(value = "/static/kaptcha" , method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    response.setDateHeader("Expires", 0L);
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	    response.setHeader("Pragma", "no-cache");
	    response.setContentType("image/jpeg");
	    String kapText = kaptchaProducer.createText();
	    
	    request.getSession().setAttribute(KaptchaFormAuthenticationFilter.DEFAULT_CAPTCHA_SESSION, kapText);

	    BufferedImage bi = kaptchaProducer.createImage(kapText);
	    OutputStream out = response.getOutputStream();
	
	    ImageIO.write(bi, "jpg", out);
	    try {
	      out.flush();
	    } finally {
	      out.close();
	    }
	} 
}
