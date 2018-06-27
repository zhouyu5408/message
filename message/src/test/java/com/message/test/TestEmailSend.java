package com.message.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.message.mail.MailSender;
import com.message.mail.MailSenderInfo;

/**
 * 邮件发送测试（可以考虑集成Spring 邮件模块JavaMailSenderImpl）
 * 
 * @Date: 2017年12月27日 上午9:47:31 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class TestEmailSend {
	/**
	 * 测试文本发送
	 * 
	 * @author zhouyu
	 * @throws Exception
	 */
	@Test
	public void testSendEmailBText() throws Exception {
		System.out.println("--- Begin ---");
		String mailServerHost = "smtp.163.com";
		String mailServerPort = "25";
		String mailUserName = "zhouyu5408@163.com";
		String mailPassword = "zy1913";
		MailSender sender = new MailSender();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(mailServerHost);
		mailInfo.setMailServerPort(mailServerPort);
		mailInfo.setUserName(mailUserName);
		mailInfo.setPassword(mailPassword);
		mailInfo.setFromAddress(mailUserName);
		mailInfo.setValidate(true);
		String toAddress = "zhouyu5408@163.com";
		mailInfo.setToAddress(toAddress);
		String subject = "测试文本发送";
		mailInfo.setSubject(subject);
		String textContent = "测试文本一";
		mailInfo.setContent(textContent);
		sender.sendTextMail(mailInfo);
		// sender.sendHtmlMail(mailInfo);
		System.out.println("--- End ---");
	}

	/**
	 * 测试网页发送
	 * 
	 * @author zhouyu
	 * @throws Exception
	 */
	@Test
	public void testSendEmailByHtml() throws Exception {
		System.out.println("--- Begin send Html---");
		String mailServerHost = "smtp.163.com";
		String mailServerPort = "25";
		String mailUserName = "zhouyu5408@163.com";
		String mailPassword = "zy1913";
		MailSender sender = new MailSender();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(mailServerHost);
		mailInfo.setMailServerPort(mailServerPort);
		mailInfo.setUserName(mailUserName);
		mailInfo.setPassword(mailPassword);
		mailInfo.setFromAddress(mailUserName);
		mailInfo.setValidate(true);
		String toAddress = "1786846080@qq.com";
		mailInfo.setToAddress(toAddress);
		String subject = "测试HTML发送";
		mailInfo.setSubject(subject);
		String model = "<div id='dvContent' class='minHeight200'><div style='line-height:1.7;color:#000000;font-size:14px;font-family:Arial'><div><span style='font-family: &quot;Times New Roman&quot;; font-size: 12pt; color: rgb(255, 0, 0);'><font face='宋体'><b>本</b></font></span><span style='color: rgb(255, 0, 0);'><span class='15' style='color: rgb(255, 0, 0); font-family: 宋体; font-size: 12pt;'><b>酒店管理</b></span><span class='15' style='color: rgb(255, 0, 0); font-family: &quot;Times New Roman&quot;; font-size: 12pt;'><font face='宋体'><b>系统的总目标是为用户提供迅速</b></font></span></span></div><div><span class='15' style='mso-spacerun:'yes';font-family:'Times New Roman';mso-fareast-font-family:宋体;font-size:12.0000pt;'><font face='宋体'>高效的服务，减免手工处理的繁琐与误差，及时、准确地反映酒店的工作情况、经营情况，</font></span></div><div><span class='15' style='mso-spacerun:'yes';font-family:'Times New Roman';mso-fareast-font-family:宋体;font-size:12.0000pt;'><font face='宋体'>从而提高酒店的服务质量，获得更好的经济效益；实现客房管理的规范化、自动化。</font></span></div><div><span class='15' style='mso-spacerun:'yes';font-family:'Times New Roman';mso-fareast-font-family:宋体;font-size:12.0000pt;'><font face='宋体'>具体的目标包括</font></span></div></div></div>";
		mailInfo.setContent(model);
		List<File> fileList = new ArrayList<File>();
		fileList.add(new File("F:\\testIMG\\1.png"));
		fileList.add(new File("F:\\testIMG\\2.png"));
		fileList.add(new File("F:\\testIMG\\3.png"));
		mailInfo.setFileList(fileList );
		sender.sendHtmlMail(mailInfo);
		// sender.sendHtmlMail(mailInfo);
		System.out.println("--- End ---");
	}
}
