package br.silveira.orderpicking.auth.resource;

import br.silveira.orderpicking.auth.jwt.JwtTokenUtil;
import br.silveira.orderpicking.auth.payload.AuthResponse;
import br.silveira.orderpicking.auth.payload.Register;
import br.silveira.orderpicking.auth.payload.RegisterCheck;
import br.silveira.orderpicking.auth.service.RegisterService;
import br.silveira.orderpicking.auth.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {
    private static final Logger log = LoggerFactory.getLogger(AuthResource.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestParam("username") String username,@Valid @RequestParam("password") String password) {
        try {
           log.info("{} Sign in", username);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(userDetails.getCompanyId(), userDetails.getUsername(), jwt));
        }catch(BadCredentialsException e){
            log.warn("{} BadCredentialsException: Message {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch(Exception e){
            log.error("Sign in error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody Register register) {
        try {
            registerService.register(register);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(" register Errors - DTO: "+register , e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/registerCheck/{identificationNo}/{username}")
    public ResponseEntity<RegisterCheck> registerCheck(@Valid @PathVariable String identificationNo,@Valid @PathVariable String username) {
        try {
            RegisterCheck dto = registerService.registerCheck(identificationNo, username);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            log.error(" registerCheck Errors , identification {} and username {} ", identificationNo, username , e);
            return ResponseEntity.badRequest().build();
        }
    }


}
