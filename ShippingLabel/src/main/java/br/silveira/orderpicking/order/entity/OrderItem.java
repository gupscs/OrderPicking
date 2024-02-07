package br.silveira.orderpicking.order.entity;

import lombok.Data;
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
public class OrderItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @ManyToOne
    private Order order;
    private String mktPlaceItemId;
    private String title;
    private String itemCd;
    private String itemDesc;
    private Integer quantity;
    private double unitPrice;
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
}
