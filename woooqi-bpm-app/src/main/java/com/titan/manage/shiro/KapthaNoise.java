package com.titan.manage.shiro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.google.code.kaptcha.impl.DefaultNoise;

public class KapthaNoise extends DefaultNoise {

	@Override
	public void makeNoise(BufferedImage image, float factorOne,float factorTwo, float factorThree, float factorFour) {

		int width = image.getWidth();
		int height = image.getHeight();
		
		Random rand = new Random();
		
		Graphics2D graph = (Graphics2D) image.getGraphics();
		graph.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
								
		for (int i = 0; i < 5; ++i) {
			//干扰线粗细
			graph.setStroke(new BasicStroke(Math.round(rand.nextFloat()*5)));
			//直线干扰线
			//graph.setColor(new Color(Math.round(rand.nextFloat()*255), Math.round(rand.nextFloat()*255), Math.round(rand.nextFloat()*255)));
			graph.setColor(new Color(80,80,190));
			graph.drawLine(Math.round(rand.nextFloat()*width), Math.round(rand.nextFloat()*height),Math.round(rand.nextFloat()*width), Math.round(rand.nextFloat()*height));
			//椭圆干扰线
//			graph.setColor(new Color(Math.round(rand.nextFloat()*255), Math.round(rand.nextFloat()*255), Math.round(rand.nextFloat()*255)));
//			graph.drawOval(Math.round(rand.nextFloat()*width), Math.round(rand.nextFloat()*height),Math.round(rand.nextFloat()*width), Math.round(rand.nextFloat()*height));
		}
		graph.dispose();
	}

}
