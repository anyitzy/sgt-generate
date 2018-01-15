package com.yryz.generate.util.email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

//import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.core.env.Environment;


import freemarker.template.TemplateException;


/**
 * @Description: Email tools
 * @Author: eric.zhong
 * @CreateDate: 2017-03-27
 */
public class EmailUtil {
	static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

	/**
	 * @Desc: send email
	 * @author: eric.zhong
	 * @date: 2016/10/13
	 * @param receiver
	 */
	public void sendMail(String receiver, String cc, String bcc, String subject, String templateName, Map<String, String> templateMap) {
		/*Environment env = (Environment) SpringUtil.getBean("environment");

		String server = env.getProperty("mail.smtp.server", String.class);
		String port = env.getProperty("mail.smtp.port", String.class);
		String templatLogo = env.getProperty("email.template.logo.url", String.class);

		String starttls = env.getProperty("mail.smtp.starttls.enable", String.class);
		boolean auth = env.getProperty("mail.smtp.auth", Boolean.class);
		String sender = env.getProperty("mail.smtp.sender", String.class);

		String username = env.getProperty("mail.smtp.username", String.class);
		String password = env.getProperty("mail.smtp.password", String.class);
		String nickname = env.getProperty("mail.smtp.nickname", String.class);*/

		/*String server = "smtp.163.com";
		String port = "25";
		String templatLogo = "";
		String starttls = "true";
		boolean auth = true;
		String sender ="anyitzy@163.com";
		String username = "anyitzy@163.com";
		String password = "qwe123";
		String nickname = "eric";*/

		String server = "smtp.exmail.qq.com";
		String port = "25";
		String templatLogo = "";
		String starttls = "true";
		boolean auth = true;
		String sender ="support@ichinaresource.com";
		String username = "support@ichinaresource.com";
		String password = "LXBchinaresource1219...";
		String nickname = "eric";

		try {
			templateMap.put("templatLogo", templatLogo);

			MailSender mail = new MailSender(server, port, starttls);

			mail.setNeedAuth(auth);
			mail.setNamePass(username, password, nickname);
			String maiBody = TemplateFactory.generateHtmlFromFtl(templateName, templateMap);
			mail.setSubject(subject);
			mail.setBody(maiBody);
			mail.setReceiver(receiver);
			if (cc!= null && !"".equals(cc)) {
				mail.setCopyTo(cc);
			}

			if (bcc!= null && !"".equals(bcc)) {
				mail.setBlindCopyTo(bcc);
			}
			mail.setSender(sender);
			mail.sendout();
		}
		catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.toString(), e);
		}
		catch (TemplateException e) {
			e.printStackTrace();
			LOGGER.error(e.toString(), e);
		}
		catch (MessagingException e) {
			e.printStackTrace();
			LOGGER.error(e.toString(), e);
		}
	}

	public static void main(String[] args) {
		EmailUtil emailUtil = new EmailUtil();
		//String receiver="zhongying@yryz.com";
		//String receiver="281264212@qq.com";
		String receiver="anyitzy@163.com";
		String subject = "你好";
		String templateName = "test.ftl";
		Map<String, String> templateMap = new HashMap <String, String>();

		emailUtil.sendMail( receiver, null, null,  subject,  templateName, templateMap);
	}
}
