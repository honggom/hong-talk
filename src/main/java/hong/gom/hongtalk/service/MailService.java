package hong.gom.hongtalk.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	
	private static final String FROM = "HongTalk";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final JavaMailSender javaMailSender;
	
	private final SpringTemplateEngine templateEngine;
	
    public void sendTo(String email) {
    	MimeMessage message = javaMailSender.createMimeMessage();
    	
    	try {
        	MimeMessageHelper helper = new MimeMessageHelper(message, true);
        	
        	Context context = new Context();
        	context.setVariable("from", FROM);
        	
        	helper.setSubject("테스트 메일 전송");
        	helper.setTo(email);
        	String html = templateEngine.process("mail/mail-template", context);
        	helper.setText(html, true);
    	} catch (MessagingException e) {
    		logger.info("==== 메일 전송 중 오류 발생 ====");
    		
    		// TODO 예외 조치 추가 ...
    		
    		logger.info("==== 메일 전송 중 오류 발생 ====");
    	}
        javaMailSender.send(message);
    }
}