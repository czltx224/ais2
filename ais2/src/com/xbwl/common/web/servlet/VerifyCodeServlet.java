package com.xbwl.common.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xbwl.common.utils.Constants;


public class VerifyCodeServlet extends HttpServlet {

	private static final long serialVersionUID = 8999240537893154924L;

	private int width = 85;// 验证码宽度

	private int height = 26; // 验证码高度

	private int codeCount = 4; // 验证码个数

	private int x = 0;

	private int fontHeight; // 字体高度

	private int codeY;

	public void init() throws ServletException {

		// 高度
		String imgWidth = this.getInitParameter("imgWidth");
		// 宽度
		String imgHeight = this.getInitParameter("imgHeight");
		// 个数
		String codeCount = this.getInitParameter("codeCount");

		try {
			if (imgWidth != null && imgWidth.length() != 0) {
				width = Integer.parseInt(imgWidth);
			}

			if (imgHeight != null && imgHeight.length() != 0) {
				height = Integer.parseInt(imgHeight);
			}

			if (codeCount != null && codeCount.length() != 0) {
				this.codeCount = Integer.parseInt(codeCount);
			}
		} catch (NumberFormatException e) {
		}

		x = width / (this.codeCount + 1);
		fontHeight = height - 2;
		codeY = height - 4;

	}

	char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '2', '3', '4', '5', '6', '7', '8', '9' };

	public VerifyCodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 定义图像对象
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		
		//随机数生成器类
		Random random = new Random();
		//将图像绘制成白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		//创建字体，字体大小应该根据图片高度来定。
		Font font = new Font(/*"Courier New"*/"arial",Font.PLAIN,fontHeight);
		//设置字体
		g.setFont(font);
		
		//画边框
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);
		
		//随机数的干扰线  160条
		/*g.setColor(Color.BLACK);
		for(int i = 0; i < 10; i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(12);
			int y1 = random.nextInt(12);
			g.drawLine(x, y, x+x1, y+y1);
		}*/
		
		//保存随即生成的验证码
		StringBuffer randomCode = new StringBuffer();
		int red = 21;
		int green = 111;
		int blue = 199;
		
		//随机生成codeCount字数的验证码
		for(int i = 0; i < codeCount; i++){
			
			//得到随机产生的验证码数字
			String strRand = String.valueOf(codeSequence[random.nextInt(34)]);
			//产生随机颜色
			/*red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);*/
			
			//用随机产生的颜色将验证码绘制到图像中
			g.setColor(new Color(red,green,blue));
			g.drawString(strRand,(i + 1) * x, codeY);
			
			//将产生的四个随机数组合在一起.
			randomCode.append(strRand);
			
		}
		
		//将验证码存入session以用来验证
		HttpSession session = request.getSession();
		session.setAttribute(Constants.VERIFY_CODE_NAME, randomCode.toString());
		
		//禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-cControl", "no-cache");
		response.setDateHeader("Expires", 0);
		
		
		response.setContentType("image/jpeg");
		//输出图像
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg",out);
		out.close();
	}

}