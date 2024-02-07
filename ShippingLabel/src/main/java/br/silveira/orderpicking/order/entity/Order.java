package br.silveira.orderpicking.order.entity;

import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import lombok.Data;
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
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    private MktPlaceEnum mktPlace;
    private String sellerId;
    private String mktPlaceOrderId;
    private String mktPlaceStatus;
    private LocalDateTime orderCreationDate;
    private String receiverCityName;
    private String receiverZipcode;
    private String mktPlaceShippingId;
    private Long shippingLabelId;
    private double totalPrice;
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
    @OneToMany(mappedBy = "mktPlaceOrder")
    private List<OrderItem> orderItems;


}
