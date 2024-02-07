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
public class PickingDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @ManyToOne
    private Picking picking;
    private PickingStatus pickingStatus;
    private String itemCd;
    @OneToMany(mappedBy = "pickingDetail")
    private List<PickingOrderItem> pickingOrderItems;
    private String itemDesc;
    private Integer pickingQty;
    private Integer pickedQty;
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

    public PickingDetail(Long companyId, Picking picking, String itemCd, String itemDesc) {
        this.companyId = companyId;
        this.picking = picking;
        this.itemCd = itemCd;
        this.itemDesc = itemDesc;
        this.pickingQty = 0;
        this.pickedQty = 0;
        this.pickingStatus = PickingStatus.PENDING;
    }

    public void addPickingOrderItemAndSumPickingQty(PickingOrderItem pickingOrderItem, Integer addQty){
        pickingOrderItem.setPickingDetail(this);
        if(pickingOrderItems == null) pickingOrderItems = new ArrayList<>();
        pickingOrderItems.add(pickingOrderItem);
        pickingQty = pickingQty + addQty;
    }
}
