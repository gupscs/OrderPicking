package br.silveira.orderpicking.auth.security;

import br.silveira.orderpicking.sysadmin.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private Long companyId;
    @JsonIgnore
    private String password;
    private Boolean enable;


    public UserDetailsImpl() {
    }

    public UserDetailsImpl(String username, Long companyId) {
        this.username = username;
        this.companyId = companyId;
    }

    public static UserDetailsImpl build(User user){
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getUsername());
        userDetails.setEmail(user.getEmail());
        userDetails.setCompanyId(user.getCompanyId());
        userDetails.setEnable(user.getEnable());
        userDetails.setPassword(user.getPassword());
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
