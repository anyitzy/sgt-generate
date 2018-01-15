/*******************************************************************************
 * Licensed to the OKChem
 *
 * http://www.okchem.com
 *
 *******************************************************************************/
package com.yryz.generate.util.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Desc: Mail to achieve class
 * @author eric.zhong
 */
public class MailSender {
	private MimeMessage mimeMsg; // MIME mail object
	private Session session; // mail session object
	private Properties props; // system properties
	private Multipart mp; // Multipart object ,Mail content, title, attachments and other content are added to the back of the
	private String username;// Sender's user name
	private String password;// Sender's user password
	private String nickname;// Sender's user nick
	private static Logger log = LoggerFactory.getLogger(MailSender.class);

	/**
	 * arguments
	 * 
	 * @param smtp
	 */
	public MailSender(String smtp, String port, String starttls) {
		setSmtpHost(smtp);
		setSmtpPort(port);
		setSmtpStarttls(starttls);
		createMimeMessage();
	}

	/**
	 * setting SMTP host
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param hostName
	 *           SMTP
	 * @Description:
	 * @return void
	 */
	public void setSmtpHost(String hostName) {
		if (props == null)
			props = System.getProperties();
		props.put("mail.smtp.host", hostName);
		log.debug("set system properties success ：mail.smtp.host= " + hostName);
	}


	public void setSmtpPort(String portName) {
		if (props == null)
			props = System.getProperties();
		props.put("mail.smtp.port", portName);
	}

	public void setSmtpStarttls(String starttls) {
		if (props == null)
			props = System.getProperties();
		props.put("mail.smtp.starttls.enable", starttls);
	}


	/**
	 * create maill object
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @return
	 * @Description:
	 * @return boolean
	 */
	public void createMimeMessage() {
		session = Session.getDefaultInstance(props, null);
		mimeMsg = new MimeMessage(session);
		mp = new MimeMultipart();
		log.debug(" create session and mimeMessage success");
	}

	/**
	 * setting auth
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param need
	 * @Description:
	 * @return void
	 */
	public void setNeedAuth(boolean need) {
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		}
		else {
			props.put("mail.smtp.auth", "false");
		}
		log.debug("set smtp auth success：mail.smtp.auth= " + need);

	}

	/**
	 * setting mail subject
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param subject
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @Description:
	 * @return void
	 */
	public void setSubject(String subject) throws UnsupportedEncodingException, MessagingException {
		mimeMsg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		log.debug("set mail subject success, subject= " + subject);

	}

	/**
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param mailBody
	 * @throws MessagingException
	 * @Description:
	 * @return void
	 */
	public void setBody(String mailBody) throws MessagingException {
		BodyPart bp = new MimeBodyPart();
		bp.setContent("" + mailBody, "text/html;charset=utf-8");
		mp.addBodyPart(bp);
		log.debug("set mail body content success,mailBody= " + mailBody);
	}

	/**
	 * add mail attach
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param filePath
	 * @throws MessagingException
	 * @Description:
	 * @return void
	 */
	public void addFileAffix(String filePath) throws MessagingException {
		BodyPart bp = new MimeBodyPart();
		FileDataSource fileds = new FileDataSource(filePath);
		bp.setDataHandler(new DataHandler(fileds));
		bp.setFileName(fileds.getName());
		mp.addBodyPart(bp);
		log.debug("mail add file success,filename= " + filePath);
	}

	/**
	 * setting sender mail
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param sender
	 * @throws UnsupportedEncodingException
	 * @throws AddressException
	 * @throws MessagingException
	 * @Description:
	 * @return void
	 */
	public void setSender(String sender) throws UnsupportedEncodingException, AddressException, MessagingException {
		nickname = MimeUtility.encodeText(nickname, "utf-8", "B");
		mimeMsg.setFrom(new InternetAddress(nickname + " <" + sender + ">"));
		log.debug(" set mail sender and nickname success , sender= " + sender + ",nickname=" + nickname);
	}

	/**
	 * setting email
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param receiver
	 * @throws AddressException
	 * @throws MessagingException
	 * @Description:
	 * @return void
	 */
	public void setReceiver(String receiver) throws AddressException, MessagingException {
		mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
		log.debug("set mail receiver success,receiver = " + receiver);
	}

	/**
	 * setting cc mail
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param cc
	 * 
	 * @throws AddressException
	 * @throws MessagingException
	 * @Description:
	 * @return void
	 */
	public void setCopyTo(String copyto) throws AddressException, MessagingException {
		mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyto));
		log.debug("set mail copyto receiver success,copyto = " + copyto);
	}

	/**
	 * setting bcc mail
	 * 
	 * @param blindCopyto
	 * @throws AddressException
	 * @throws MessagingException
	 * @author Kevin GUO
	 */
	public void setBlindCopyTo(String blindCopyto) throws AddressException, MessagingException {
		mimeMsg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(blindCopyto));
		log.debug("set mail blindCopyto receiver success,blindCopyto = " + blindCopyto);
	}

	/**
	 * send mail operate
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @throws MessagingException
	 * @Description:
	 * @return void
	 */
	public void sendout() throws MessagingException {
		mimeMsg.setContent(mp);
		mimeMsg.saveChanges();
		Session mailSession = Session.getInstance(props, null);
		Transport transport = mailSession.getTransport("smtp");
		transport.connect((String) props.get("mail.smtp.host"), username, password);
		//		transport.sendMessage(mimeMsg,
		//				mimeMsg.getRecipients(Message.RecipientType.TO));
		transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
		transport.close();
		log.debug(" send mail success");
	}

	/**
	 * Injection sender user name, password, nickname
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param username
	 * @param password
	 * @param nickname
	 * @Description:
	 * @return void
	 */
	public void setNamePass(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;

	}

}
