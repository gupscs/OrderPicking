package br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.resource;

import br.silveira.orderpicking.common.resource.CommonResource;
import br.silveira.orderpicking.common.specification.CommonSpecificationBuilder;
import br.silveira.orderpicking.common.specification.SearchRequest;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.dto.MercadoLivreNotificationDto;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity.MercadoLivreSetup;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreNotificationRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreSetupRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.service.MercadoLivreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mercado-livre")
@Slf4j
public class MercadoLivreResource extends CommonResource {

    @Value("${meracodlivre.appId}")
    public String APP_ID;
    @Value("${mercadolivre.redirect-url}")
    public String REDIRECT_URL;
    @Autowired
    private MercadoLivreNotificationRepository mercadoLivreNotificationRepository;
    @Autowired
    private MercadoLivreSetupRepository mercadoLivreSetupRepository;
    @Autowired
    private MercadoLivreService mercadoLivreService;

    @PostMapping("/notification")
    public ResponseEntity<?> notification(@RequestBody MercadoLivreNotificationDto notificationDto) {
        try{
            mercadoLivreNotificationRepository.save(notificationDto.toEntity());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Mercado Livre Notification receive error - Notification: {}"+notificationDto, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/MercadoLivreSetup")
    public ResponseEntity<List<MercadoLivreSetup> > getMercadoLivreSetup(@RequestBody(required = false) SearchRequest searchRequest){
        try {
            Specification<MercadoLivreSetup> specification = (Specification<MercadoLivreSetup>) getSpecification(searchRequest, new CommonSpecificationBuilder<MercadoLivreSetup>());
            List<MercadoLivreSetup> list = mercadoLivreSetupRepository.findAll(specification);
            if (list == null || list.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(list);
            }
        }catch(Exception e){
            log.error("Error to get mercado livre setup", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/MercadoLivreSetup")
    public ResponseEntity<Void> saveMercadoLivreSetup(@Valid @RequestBody String authorizationCode){
        try{
            mercadoLivreService.saveMercadoLivreSetup(authorizationCode);
            return ResponseEntity.ok().build();
        }catch(Exception e ){
            log.error("Error to save the authorization code at MercadoLivreSetup", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/configuration")
    public ResponseEntity<Map<String, String>> getConfiguration(){
        Map<String, String> ret = new HashMap<>();
        ret.put("appId", APP_ID);
        ret.put("redirectUrl", REDIRECT_URL);
        return ResponseEntity.ok(ret);
    }
}
