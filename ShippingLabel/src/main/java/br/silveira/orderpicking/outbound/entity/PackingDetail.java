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
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "companyId")
})
@AllArgsConstructor
@NoArgsConstructor
public class PackingDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @ManyToOne
    private Packing packing;
    private PackingStatus packingStatus;
    @OneToOne
    private ShippingLabel shippingLabel;
    @OneToMany(mappedBy = "packingDetail")
    private List<PackingOrderItem> packingOrderItems;
    private String remarks;
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

    public PackingDetail(Long companyId, Packing packing, ShippingLabel shippingLabel) {
        this.companyId = companyId;
        this.packing = packing;
        this.shippingLabel = shippingLabel;
        this.packingStatus = PackingStatus.PENDING;
    }

    public void addPackingOrderItem(PackingOrderItem packingOrderItem){
        if(packingOrderItems==null) packingOrderItems = new ArrayList<>();
        packingOrderItems.add(packingOrderItem);
    }
}
