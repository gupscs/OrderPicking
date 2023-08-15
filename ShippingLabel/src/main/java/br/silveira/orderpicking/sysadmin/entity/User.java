package br.silveira.orderpicking.sysadmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "companyId"),
        @Index(columnList = "username")
})
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDateTime insertDate;
    @Column(nullable = false)
    private String insertId;
    private LocalDateTime updateDate;
    private String updateId;
    @Column(nullable = false)
    private Boolean enable;


}
