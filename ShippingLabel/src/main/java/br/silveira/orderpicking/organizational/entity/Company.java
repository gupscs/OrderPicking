package br.silveira.orderpicking.organizational.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String cnpj;
    @Column(nullable = false)
    private Long phone;
    @Column(nullable = false)
    private LocalDateTime insertDate;
    @Column(nullable = false)
    private String insertId;
    private LocalDateTime updateDate;
    private String updateId;
}
