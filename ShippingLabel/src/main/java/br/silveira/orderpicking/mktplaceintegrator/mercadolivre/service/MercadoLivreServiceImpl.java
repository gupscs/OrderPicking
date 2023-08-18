package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.service;

import br.silveira.orderpicking.common.service.CommonService;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.dto.MercadoLivreTokenDto;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.entity.MercadoLivreSetup;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository.MercadoLivreClientRestRepository;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository.MercadoLivreSetupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MercadoLivreServiceImpl extends CommonService implements MercadoLivreService {

    private static final Logger log = LoggerFactory.getLogger(MercadoLivreServiceImpl.class);
    @Value("${meracodlivre.appId}")
    private String appId;
    @Value("${mercadolivre.client-secret}")
    private String clientSecret;
    @Value("${mercadolivre.redirect-url}")
    private String redirectUrl;
    private static final String BEARER = "Bearer ";
    private static final String  REFRESH_TOKEN = "refresh_token";
    @Autowired
    private MercadoLivreClientRestRepository mercadoLivreClientRestRepository;
    @Autowired
    private MercadoLivreSetupRepository mercadoLivreSetupRepository;

    @Override
    public String getToken(String sellerId) {
        Long companyId = getCompanyId();
        log.info("getToken - Company Id {} , Seller Id {}", companyId, sellerId);
        Optional<MercadoLivreSetup> mercadoLivreSetup = mercadoLivreSetupRepository.findByCompanyIdAndSellerId(companyId, sellerId);
        MercadoLivreSetup entity = mercadoLivreSetup.orElseThrow(() -> new EntityNotFoundException(String.format("Mercado Livre Setup not found for Company Id: %s and Selelr Id: %s", companyId, sellerId)));

        String apiToken = entity.getApiToken();
        LocalDateTime lastUpdate = entity.getLastApiTokenUpdated();
        Integer expireIn = entity.getExpiresIn();
        LocalDateTime now = LocalDateTime.now();

        if(lastUpdate.plusSeconds(expireIn).isBefore(now)) {
            log.debug("Token expired, refreshing token");
            MercadoLivreTokenDto token = mercadoLivreClientRestRepository.refreshToken(REFRESH_TOKEN, appId, clientSecret, entity.getApiRefreshToken());

            entity.setApiToken(token.getAccess_token());
            entity.setApiRefreshToken(token.getRefresh_token());
            entity.setLastApiTokenUpdated(now);
            entity.setExpiresIn(token.getExpires_in());
            entity.setScope(token.getScope());
            entity.setUpdateDate(now);
            entity.setUpdateId("MercadoLivreServiceImpl");
            mercadoLivreSetupRepository.save(entity);

            apiToken = token.getAccess_token();
        }

        return BEARER+apiToken;
    }

    @Override
    public void saveMercadoLivreSetup( String authorizationCode){
        Long companyId = getCompanyId();
        log.info("Creating Mercado Livre Setup - Company Id {}", companyId);
        LocalDateTime now = LocalDateTime.now();

        Optional<MercadoLivreSetup> mercadoLivreSetup = mercadoLivreSetupRepository.findByCompanyIdAndAuthorizationCode(companyId, authorizationCode);
        MercadoLivreSetup entity = mercadoLivreSetup.orElse(new MercadoLivreSetup(companyId, authorizationCode,getLoggedUser(), now ));

        Map<String, String> tokenForm = new HashMap<>();
        tokenForm.put("grant_type", "authorization_code");
        tokenForm.put("client_id", appId);
        tokenForm.put("client_secret", clientSecret);
        tokenForm.put("code", authorizationCode);
        tokenForm.put("redirect_uri", redirectUrl);
        MercadoLivreTokenDto token = mercadoLivreClientRestRepository.getToken(tokenForm);

        entity.setApiToken(token.getAccess_token());
        entity.setApiRefreshToken(token.getRefresh_token());
        entity.setSellerId(token.getUser_id());
        entity.setExpiresIn(token.getExpires_in());
        entity.setLastApiTokenUpdated(now);
        entity.setScope(token.getScope());
        entity.setEnable(true);
        if(entity.getId() != null){
            entity.setUpdateDate(now);
            entity.setUpdateId(getLoggedUser());
        }
        mercadoLivreSetupRepository.save(entity);

    }
}
