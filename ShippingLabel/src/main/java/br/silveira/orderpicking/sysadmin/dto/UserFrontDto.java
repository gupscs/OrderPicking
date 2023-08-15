package br.silveira.orderpicking.sysadmin.dto;

import br.silveira.orderpicking.sysadmin.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class UserFrontDto {

    private Long id;
    private Long companyId;
    private String fullName;
    private String username;
    private String avatar;
    private String email;
    private String role;
    private List<Ability> ability;

    public void addAbility(String action, String subject) {
        if(ability == null) {
            ability = new ArrayList<Ability>();
        }
        ability.add(new Ability(action, subject));
    }

    @Data
    public class Ability{

        public Ability() {}

        public Ability(String action, String subject) {
            this.action = action;
            this.subject = subject;
        }
        private String action;
        private String subject;
    }

    public static UserFrontDto build(User user) {
        UserFrontDto dto = new UserFrontDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setCompanyId(user.getCompanyId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getName());
        dto.setAvatar("@/assets/images/avatars/13-small.png");
        dto.setRole("CUSTOMER");
        dto.addAbility("all","CUSTOMER");
        dto.addAbility("all","ADMIN");
        return dto;
    }


}
