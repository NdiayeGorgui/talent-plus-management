package com.gogo.notification_service.rabbitmq;

import com.gogo.base_domaine_service.dto.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class NotificationListener {

    private final JavaMailSender mailSender;
    private final WebClient webClient;

    public NotificationListener(JavaMailSender mailSender,
                                @Qualifier("candidatWebClient") WebClient candidatWebClient) {
        this.mailSender = mailSender;
        this.webClient = candidatWebClient;
    }

    @RabbitListener(queues = "notificationQueue")
    public void receiveNotification(NotificationMessage message) {
        // Récupérer l'email du candidat via candidat-service (WebClient)
        log.info("📩 Notification reçue: {}", message);

        // Appel REST pour récupérer l'email du candidat
        String candidatEmail = webClient.get()
                .uri("/{id}/email", message.candidatId())
                .retrieve()
                .bodyToMono(String.class)
                .block(); // synchrone (on bloque ici)

        log.info("📩 Email candidat: {}", candidatEmail);

        // Envoi de l’email
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(candidatEmail);
        mail.setSubject("Mise à jour de votre candidature");
        mail.setText("Votre candidature a été mise à jour. Nouveau statut : " + message.nouveauStatut());
        mailSender.send(mail);
    }

}

