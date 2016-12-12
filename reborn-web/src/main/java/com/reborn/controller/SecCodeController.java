package com.reborn.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reborn.common.util.Const;
import com.reborn.common.util.Jurisdiction;




/**
 * 登录验证码
 * 
 *
 */
@Controller
@RequestMapping("/code")
public class SecCodeController {
	@RequestMapping()
	public void generate(HttpServletResponse response,HttpServletRequest request){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = drawImg(output);
		/*Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();*/
			Jurisdiction.getSession().setAttribute(Const.SESSION_SECURITY_CODE, code);
		try {
			ServletOutputStream out = response.getOutputStream(); //用打印方式把验证码图片给前端
			output.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String drawImg(ByteArrayOutputStream output){
		String code = "";
		for(int i=0; i<4; i++){
			code += randomChar();
		}
		int width = 70;
		int height = 25;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR); //创建一个Image 缓存区
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		Graphics2D g = bi.createGraphics();  //创建一个Graphics2D，可提请到BufferedImage
		g.setFont(font);//设置字体大小
		Color color = new Color(66,2,82);
		g.setColor(color); // 设置字体的颜色
		g.setBackground(new Color(226,226,240)); //设置背景颜色
		g.clearRect(0, 0, width, height);  //清除指定的矩形
		FontRenderContext context = g.getFontRenderContext();    // 获取字体的上下文
		Rectangle2D bounds = font.getStringBounds(code, context); //  获取字体的 Rectangle2D
		//字体居中
		double x = (width - bounds.getWidth()) / 2; 
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int)x, (int)baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output); // 以BufferedImage 以jpg 字节码 放output  ByteArrayOutputStream中 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public  char randomChar(){
		Random random=new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(random.nextInt(s.length()));
	}
	public static void main(String[] args) {
		System.out.print(new SecCodeController().drawImg(new ByteArrayOutputStream()));
	}
}
