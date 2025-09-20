package com.gogo.notification_service.rabbitmq;

import com.gogo.base_domaine_service.dto.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
        log.info("📩 Notification reçue: {}", message);

        try {
            // Appel REST pour récupérer l'email du candidat
            String candidatEmail = webClient.get()
                    .uri("/{id}/email", message.candidatId())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (candidatEmail == null || candidatEmail.isEmpty()) {
                log.warn("⚠️ Email du candidat introuvable pour l'ID {}", message.candidatId());
                return; // on ne tente pas d'envoyer l'email
            }

            log.info("📩 Email candidat: {}", candidatEmail);

            // Envoi de l’email
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(candidatEmail);
            mail.setSubject("Mise à jour de votre candidature");
            mail.setText("Votre candidature a été mise à jour. Nouveau statut : " + message.nouveauStatut());

            mailSender.send(mail);
            log.info("✅ Email envoyé au candidatId={}", message.candidatId());

        } catch (WebClientResponseException e) {
            log.error("❌ Erreur lors de l'appel REST pour candidatId={} : {}",
                    message.candidatId(), e.getStatusCode());
        } catch (WebClientRequestException e) {
            log.error("❌ Problème de connexion au service candidat pour candidatId={}", message.candidatId(), e);
        } catch (MailException e) {
            log.error("❌ Erreur lors de l'envoi de l'email au candidatId={}", message.candidatId(), e);
        } catch (Exception e) {
            log.error("❌ Erreur inattendue pour candidatId={}", message.candidatId(), e);
        }
    }
}
