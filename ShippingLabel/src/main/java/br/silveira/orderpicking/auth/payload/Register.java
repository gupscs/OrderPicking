package br.silveira.orderpicking.auth.payload;

import br.silveira.orderpicking.organizational.entity.Company;
import br.silveira.orderpicking.sysadmin.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Register {

    private String identificationNo;
    private String name;
    private Long phone;
    private String email;
    private String userName;
    private String password;

    public Company toCompany() {
        Company company = new Company();
        company.setIdentificationNo(identificationNo);
        company.setName(name);
        company.setPhone(phone);
        company.setInsertDate(LocalDateTime.now());
        company.setInsertId(email);
        return company;
    }

    public User toUser() {
        User user = new User();
        user.setName(userName);
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.setInsertDate(LocalDateTime.now());
        user.setInsertId(email);
        user.setEnable(true);
        return user;
    }
}
