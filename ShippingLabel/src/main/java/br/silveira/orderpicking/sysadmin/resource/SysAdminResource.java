package br.silveira.orderpicking.sysadmin.resource;

import br.silveira.orderpicking.sysadmin.dto.UserFrontDto;
import br.silveira.orderpicking.sysadmin.entity.User;
import br.silveira.orderpicking.sysadmin.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/sysadmin")
@CrossOrigin(origins = "http://localhost:8080/")
public class SysAdminResource {

    private static final Logger log = LogManager.getLogger(SysAdminResource.class);

    private static PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/findUserByUsername/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()){
                return ResponseEntity.ok().body(user.get());
            }else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error to save the User", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findUserFrontModelByUsername/{username}")
    public ResponseEntity<UserFrontDto> findUserFrontModelByUsername(@PathVariable String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()){
                return ResponseEntity.ok().body(UserFrontDto.build(user.get()));
            }else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error to find user ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user){
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user = userRepository.save(user);
            user.setPassword("");
            return ResponseEntity.ok(user);
        }catch(Exception e){
            log.error("Erro to save the User {}", user.getUsername(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("existByUsername/{username}")
    public ResponseEntity<Boolean> existByUsername(@PathVariable String username) {
        try {
            boolean ret = userRepository.existsByUsername(username);
            return ResponseEntity.ok(ret);
        }catch(Exception e){
            log.error("Error to check exist  User username:  {}", username, e);
            return ResponseEntity.badRequest().build();
        }
    }
}
