package br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "topic")
})
public class MercadoLivreNotification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String resource;
    private Long userId;
    private String topic;
    private Long applicationId;
    private Integer attempts;
    private String sent;
    private String received;
}
