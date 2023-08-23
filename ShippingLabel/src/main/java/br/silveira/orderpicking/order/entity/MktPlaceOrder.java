package br.silveira.orderpicking.order.entity;

import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "companyId")
})
public class MktPlaceOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    private MktPlaceEnum mktPlace;
    private String sellerId;
    private String mktPlaceOrderid;
    private String mktPlaceStatus;
    private LocalDateTime orderCreationDate;
    private String receiverCityName;
    private String receiverZipcode;
    private String shippingId;
    @Column(nullable = false)
    private LocalDateTime insertDate;
    @Column(nullable = false)
    private String insertId;
    private LocalDateTime updateDate;
    private String updateId;
    @OneToMany(mappedBy = "mktPlaceOrder")
    private List<MktPlaceOrderItem> mktPlaceOrderItems;


}
