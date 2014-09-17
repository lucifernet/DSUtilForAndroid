package ischool.dsa.utility.mail;
//
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.util.Properties;
//
//import android.os.Message;
//import android.service.textservice.SpellCheckerService.Session;
//
public class JavaMail {
//	private static final String DEFAULT_SMTP_HOST = "smtp.gmail.com";
//	private static final int DEFAULT_SMTP_PORT = 587;
//	private static final String DEFAULT_SENDER = "ischool.no2@gmail.com";
//	private static final String DEFAULT_SENDER_PASSWORD = "dsa4admin";
//
//	public static void sendMail(String subject, String msgBody,
//			String targetEmail) throws Exception {
//		sendMail(DEFAULT_SMTP_HOST, DEFAULT_SMTP_PORT, DEFAULT_SENDER,
//				DEFAULT_SENDER_PASSWORD, subject, msgBody, targetEmail);
//	}
//
//	public static void sendMail(String host, int port, final String sender,
//			final String senderpwd, String subject, String msgBody,
//			String targetEmail) throws AddressException, MessagingException {
//
//		Properties props = new Properties();
//		props.put("mail.smtp.host", host);
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.port", port);
//
//		Session session = Session.getInstance(props, new Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(sender, senderpwd);
//			}
//		});
//
//		Message message = new MimeMessage(session);
//		message.setFrom(new InternetAddress(sender));
//
//		message.setRecipients(Message.RecipientType.TO,
//				InternetAddress.parse(targetEmail));
//
//		message.setSubject(subject);
//		message.setText(msgBody);
//
//		Transport transport = session.getTransport("smtp");
//		transport.connect(host, port, sender, senderpwd);
//
//		Transport.send(message);
//	}
}
