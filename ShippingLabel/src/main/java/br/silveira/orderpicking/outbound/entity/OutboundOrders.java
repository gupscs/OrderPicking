package br.silveira.orderpicking.outbound.entity;

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
        @Index(columnList = "companyId")
})
@AllArgsConstructor
@NoArgsConstructor
public class OutboundOrders {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @ManyToOne
    private Outbound outbound;
    private Long orderId;
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
}
