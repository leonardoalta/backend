package com.blacksystem.system.services.email;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

        private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

        @Autowired
        private JavaMailSender mailSender;

        @Value("${spring.mail.username}")
        private String fromEmail;


        public void sendVerificationCode(String toEmail, String code) {
            try {
                logger.info("Enviando código de verificación a: {}", toEmail);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(toEmail);
                message.setSubject("Código de Verificación - Pet Health Record");
                message.setText(
                        "Hola,\n\n" +
                                "Tu código de verificación es: " + code + "\n\n" +
                                "Este código expirará en 10 minutos.\n\n" +
                                "Si no solicitaste este código, ignora este mensaje.\n\n" +
                                "Saludos,\n" +
                                "Equipo de Pet Health Record"
                );

                mailSender.send(message);

                logger.info("Email enviado exitosamente a: {}", toEmail);
            } catch (Exception e) {
                logger.error("Error enviando email a {}: {}", toEmail, e.getMessage());
                throw new RuntimeException("Failed to send verification email: " + e.getMessage());
            }
        }

        public void sendVerificationCodeHtml(String toEmail, String code) {
            try {
                logger.info("Enviando código de verificación HTML a: {}", toEmail);

                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                helper.setFrom(fromEmail);
                helper.setTo(toEmail);
                helper.setSubject("Código de Verificación - Pet Health Record");

                String htmlContent = buildVerificationEmail(code);
                helper.setText(htmlContent, true);

                mailSender.send(mimeMessage);

                logger.info("Email HTML enviado exitosamente a: {}", toEmail);
            } catch (MessagingException e) {
                logger.error("Error enviando email HTML a {}: {}", toEmail, e.getMessage());
                throw new RuntimeException("Failed to send verification email: " + e.getMessage());
            }
        }

    public void sendVaccineReminder(
            String to,
            String petName,
            String vaccineName,
            String nextDate
    ) {
        String subject = "💉 Recordatorio de vacuna - " + petName;

        String body = """
        Hola 👋
        
        Te recordamos que tu mascota %s
        tiene programada la siguiente vacuna:
        
        🦠 Vacuna: %s
        📅 Fecha: %s
        
        Cuida su salud ❤️
        
        — Carnet Veterinario Digital
        """.formatted(petName, vaccineName, nextDate);

        sendEmail(to, subject, body);
    }

        private String buildVerificationEmail(String code) {
            return "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "    <meta charset='UTF-8'>" +
                    "    <style>" +
                    "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                    "        .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                    "        .header { text-align: center; color: #4CAF50; margin-bottom: 30px; }" +
                    "        .code-box { background-color: #f0f0f0; padding: 20px; text-align: center; border-radius: 5px; margin: 20px 0; }" +
                    "        .code { font-size: 32px; font-weight: bold; color: #333; letter-spacing: 5px; }" +
                    "        .footer { text-align: center; color: #888; font-size: 12px; margin-top: 30px; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class='container'>" +
                    "        <div class='header'>" +
                    "            <h1>Pet Health Record</h1>" +
                    "        </div>" +
                    "        <h2>Verificación de Cuenta</h2>" +
                    "        <p>Hola,</p>" +
                    "        <p>Tu código de verificación es:</p>" +
                    "        <div class='code-box'>" +
                    "            <div class='code'>" + code + "</div>" +
                    "        </div>" +
                    "        <p>Este código expirará en <strong>10 minutos</strong>.</p>" +
                    "        <p>Si no solicitaste este código, por favor ignora este mensaje.</p>" +
                    "        <div class='footer'>" +
                    "            <p>© 2025 Pet Health Record - Todos los derechos reservados</p>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";
        }


        public void sendEmail(String to, String subject, String body) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(to);
                message.setSubject(subject);
                message.setText(body);

                mailSender.send(message);
                logger.info("Email enviado a: {}", to);
            } catch (Exception e) {
                logger.error("Error enviando email: {}", e.getMessage());
                throw new RuntimeException("Failed to send email: " + e.getMessage());
            }
        }
    }

