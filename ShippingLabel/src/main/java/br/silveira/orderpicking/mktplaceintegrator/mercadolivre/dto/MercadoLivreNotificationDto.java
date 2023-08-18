package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.dto;

import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.entity.MercadoLivreNotification;
import lombok.Data;

@Data
public class MercadoLivreNotificationDto {

    private String resource;
    private Long user_id;
    private String topic;
    private Long application_id;
    private Integer attempts;
    private String sent;
    private String received;

    public MercadoLivreNotification toEntity(){
        MercadoLivreNotification mercadoLivreNotification = new MercadoLivreNotification();
        mercadoLivreNotification.setResource(resource);
        mercadoLivreNotification.setUserId(user_id);
        mercadoLivreNotification.setTopic(topic);
        mercadoLivreNotification.setApplicationId(application_id);
        mercadoLivreNotification.setAttempts(attempts);
        mercadoLivreNotification.setSent(sent);
        mercadoLivreNotification.setReceived(received);
        return mercadoLivreNotification;
    }

}
