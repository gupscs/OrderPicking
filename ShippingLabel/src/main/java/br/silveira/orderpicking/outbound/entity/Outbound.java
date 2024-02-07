package br.silveira.orderpicking.outbound.entity;

import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "companyId")
})
@AllArgsConstructor
@NoArgsConstructor
public class Outbound {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    private MktPlaceEnum mktPlace;
    private PickingStatus pickingStatus;
    private PackingStatus packingStatus;
    private Double completedStatus;

    @OneToMany(mappedBy = "outbound")
    private List<OutboundOrders> outboundOrders;

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
