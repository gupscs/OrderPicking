package br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "companyId"),
        @Index(columnList = "sellerId")
})
@AllArgsConstructor
@NoArgsConstructor
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
    @CreatedDate
    private LocalDateTime insertDate;
    @Column(nullable = false)
    @CreatedBy
    private String insertId;
    @LastModifiedDate
    private LocalDateTime updateDate;
    @LastModifiedBy
    private String updateId;
    private Boolean enable;

    public MercadoLivreSetup(Long companyId, String authorizationCode, String insertId, LocalDateTime insertDate) {
        this.companyId = companyId;
        this.authorizationCode = authorizationCode;
        this.insertDate = insertDate;
        this.insertId = insertId;
    }
}
