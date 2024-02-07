package br.silveira.orderpicking.outbound.entity;

import br.silveira.orderpicking.order.entity.OrderItem;
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
public class PickingOrderItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @ManyToOne
    private PickingDetail pickingDetail;
    private Long orderId;
    private Long orderItemId;
    private String mktPlaceShippingId;
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

    public PickingOrderItem(Long companyId, PickingDetail pickingDetail, Long orderId, Long orderItemId, String mktPlaceShippingId) {
        this.companyId = companyId;
        this.pickingDetail = pickingDetail;
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.mktPlaceShippingId = mktPlaceShippingId;
    }


    public PackingOrderItem toPackingOrderItem(OrderItem orderItem) {
        PackingOrderItem poi = new PackingOrderItem();
        poi.setCompanyId(companyId);
        poi.setOrderId(orderId);
        poi.setOrderItemId(orderItemId);
        poi.setMktPlaceOrderId(orderItem.getOrder().getMktPlaceOrderId());
        poi.setItemCd(orderItem.getItemCd());
        poi.setItemDesc(orderItem.getItemDesc());
        poi.setQuantity(orderItem.getQuantity());
        return poi;
    }
}
