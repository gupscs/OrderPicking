package br.silveira.orderpicking.outbound.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Pack;
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
public class Packing {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @ManyToOne
    private Outbound outbound;
    private PackingStatus packingStatus;
    private Double completedStatus;
    @OneToMany(mappedBy = "packing")
    private List<PackingDetail> packingDetail;
    private Employee employee;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
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

    public Packing(Long companyId, Outbound outbound, Employee employee) {
        this.setOutbound(outbound);
        this.setPackingStatus(PackingStatus.PENDING);
        this.setEmployee(employee);
        this.setCompanyId(companyId);
        this.setCompletedStatus(0.0);
    }
}
