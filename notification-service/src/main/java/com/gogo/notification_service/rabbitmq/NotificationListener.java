package com.gogo.notification_service.rabbitmq;

import com.gogo.base_domaine_service.dto.NotificationMessage;
import com.gogo.notification_service.dto.UserDto;
import com.gogo.notification_service.enums.ReceiverType;
import com.gogo.notification_service.model.Notification;
import com.gogo.notification_service.service.NotificationService;
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
    private final WebClient userWebClient;
    private final NotificationService notificationService;

    public NotificationListener(JavaMailSender mailSender,
                                @Qualifier("candidatWebClient") WebClient candidatWebClient,
                                @Qualifier("userWebClient") WebClient userWebClient,
                                NotificationService notificationService) {
        this.mailSender = mailSender;
        this.webClient = candidatWebClient;
        this.notificationService = notificationService;
        this.userWebClient = userWebClient;
    }

    @RabbitListener(queues = "notificationQueue")
    public void receiveNotification(NotificationMessage message) {
        log.info("📩 Notification reçue: {}", message);

        try {
            // ✅ Récupération de l'email du candidat
            String candidatEmail = webClient.get()
                    .uri("/{id}/email", message.candidatId())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (candidatEmail == null || candidatEmail.isEmpty()) {
                log.warn("⚠️ Email du candidat introuvable pour l'ID {}", message.candidatId());
                return;
            }

            // ✅ Envoi de l'e-mail
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(candidatEmail);
            mail.setSubject("Mise à jour de votre candidature");
            mail.setText("Votre candidature a été mise à jour. Nouveau statut : " + message.nouveauStatut());
            mailSender.send(mail);

            log.info("✅ Email envoyé au candidatId={}", message.candidatId());

            // Récupération du User correspondant à cet email
            UserDto user = userWebClient.get()
                    .uri("/by-email/{email}", candidatEmail)
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .block();

            Long userId = user.getId();
            log.info("🔍 User récupéré depuis email {} → userId={}", candidatEmail, userId);
            // ✅ Sauvegarde de la notification dans la base pour l’application Angular
            Notification notif = Notification.builder()
                    .receiverId(userId)
                    .receiverType(ReceiverType.CANDIDAT)
                    .message("Le statut de votre candidature est : " + message.nouveauStatut())
                    .typeEvenement(message.nouveauStatut())
                    .isRead(false)
                    .build();

            notificationService.save(notif);
            log.info("✅ Notification enregistrée pour l'application (candidatId={})", message.candidatId());

        } catch (WebClientResponseException e) {
            log.error("❌ Erreur REST: candidatId={}, code={}", message.candidatId(), e.getStatusCode());
        } catch (WebClientRequestException e) {
            log.error("❌ Problème de connexion au service candidat", e);
        } catch (MailException e) {
            log.error("❌ Erreur lors de l'envoi de l'email", e);
        } catch (Exception e) {
            log.error("❌ Erreur inattendue", e);
        }
    }
}
