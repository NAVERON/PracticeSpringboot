package com.eron.practice.utils;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


public class MailSenderUtils { 
	
	private static final Logger log = LoggerFactory.getLogger(MailSenderUtils.class);
	
	@Resource 
	private static JavaMailSender mailSender;
	
	@Value(value = "${spring.mail.username}") 
	public static String fromEmail; 
	
	@Value(value = "spring.mail.username")  // for testing 
	public static String toEmail;
	
	/**
	 * 发送普通文本邮件 
	 * @param from from_email addr 
	 * @param to to_email addr 
	 * @param subject 邮件主题
	 * @param content 邮件内容 
	 */
	public static void sendSimpleMail(String from, String to, String subject, String content) { 
		// 普通邮件发送实现 
		log.info("simple mail sender params, from : {}, to: {}, \nsubject: {},\ncontent: {}", from, to, subject, content);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		
		MailSenderUtils.mailSender.send(message);
	}
	
	/**
	 * 发送html格式的邮件 
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 */
	public static void senmHTMLMail(String from, String to, String subject, String content) {
		// html 格式邮件发送 
	}
	
	/**
	 * 发送带附件的邮件 
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 */
	public static void sendAttachmentMail(String from, String to, String subject, String content) {
		// 带附件的邮件 
	}
	
	// 发送带模板的邮件 
	// 记录邮件发送的状态使用 Redis 同步 
	// 使用队列异步发送 
	
}










