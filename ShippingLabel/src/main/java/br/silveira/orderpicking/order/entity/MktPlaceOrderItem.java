package br.silveira.orderpicking.order.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "companyId")
})
public class MktPlaceOrderItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @ManyToOne
    private MktPlaceOrder mktPlaceOrder;
    private String mktPlaceItemId;
    private String title;
    private String sellerSku;
    private double quantity;
    private double unitPrice;
    @Column(nullable = false)
    private LocalDateTime insertDate;
    @Column(nullable = false)
    private String insertId;
    private LocalDateTime updateDate;
    private String updateId;
}
