package br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "companyId"),
        @Index(columnList = "sellerId")
})
public class MercadoLivreSetup {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    private String authorizationCode;
    private Integer sellerId;
    private String apiToken;
    private String apiRefreshToken;
    private LocalDateTime lastApiTokenUpdated;
    private Integer expiresIn;
    public String scope;
    @Column(nullable = false)
    private LocalDateTime insertDate;
    @Column(nullable = false)
    private String insertId;
    private LocalDateTime updateDate;
    private String updateId;
    private Boolean enable;

    public MercadoLivreSetup(){
    }

    public MercadoLivreSetup(Long companyId, String authorizationCode, String insertId, LocalDateTime insertDate) {
        this.companyId = companyId;
        this.authorizationCode = authorizationCode;
        this.insertDate = insertDate;
        this.insertId = insertId;
    }
}
