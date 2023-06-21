package com.samha.service;

import com.samha.commons.BusinessException;
import com.samha.domain.Servidor;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;

@Service
public class EmailService {

    public void enviarEmail(String remetente, String senha, String mensagem, String assunto, byte[] anexo, String filename) {
        try {

            Properties props = getProperties(remetente, senha);
            Session session = Session.getDefaultInstance(props, null);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(remetente));

            Multipart mp = new MimeMultipart();

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(mensagem);

            mp.addBodyPart(mbp);

            MimeBodyPart mbpAnexo = new MimeBodyPart();
            DataSource dataSource = new ByteArrayDataSource(anexo, "application/octet-stream");
            mbpAnexo.setDataHandler(new DataHandler(dataSource));
            mbpAnexo.setFileName(filename);
            mp.addBodyPart(mbpAnexo);

            message.setSubject(assunto);
            message.setContent(mp);

            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), senha);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException e) {
            throw new BusinessException("Falha no envio de e-mail", e);
        }
    }

    private Properties getProperties(String remetente, String senha) {
        Properties p = new Properties();

        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.port", "587");
        p.put("mail.smtp.user", remetente);
        p.put("mail.smtp.password", senha);
        p.put("mail.smtp.host", "smtp.ifes.edu.br");

        return p;
    }

    public String montarMensagem(Servidor servidor, int ano, int semestre) {
        String mensagem = "\nOlá " + servidor.getNome() + "!\n"
                + "Segue em anexo o arquivo contendo seus horários de aulas para o semestre de " + ano + "/" + semestre + ".\n\n";
        mensagem = mensagem + "\nQualquer dúvida procure seu coordenador de curso.\n\n"
                + "Atenciosamente,";

        return mensagem;
    }
}
