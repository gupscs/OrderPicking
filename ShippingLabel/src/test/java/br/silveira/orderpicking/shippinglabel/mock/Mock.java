package br.silveira.orderpicking.shippinglabel.mock;

import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity.MercadoLivreSetup;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreClientRestRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreSetupRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.service.MercadoLivreService;
import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelOrderDto;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Mock {

    @MockBean
    MercadoLivreClientRestRepository mercadoLivreClientRestRepository;

    @MockBean
    MercadoLivreSetupRepository mercadoLivreSetupRepository;

    public void init(){
        MercadoLivreSetup mercadoLivreSetup = new MercadoLivreSetup(Long.valueOf("1"), Long.valueOf("1"), "1234567890", 1, "1234567890", "0000", LocalDateTime.now(), 9999999, "", LocalDateTime.now(), "mock", null, null, true);

        Mockito.when(mercadoLivreSetupRepository.findByCompanyIdAndSellerId(Long.valueOf("1"), "1"))
                .thenReturn(Optional.<MercadoLivreSetup>of(mercadoLivreSetup));

        Mockito.when(mercadoLivreClientRestRepository.getShipmentLabelsInTypeZpl2("Bearer 1234567890", "1"))
                .thenReturn("etiqueta-01");

        Mockito.when(mercadoLivreClientRestRepository.getShipmentLabelsInTypeZpl2("Bearer 1234567890", "2"))
                .thenReturn("etiqueta-02");

        Mockito.when(mercadoLivreClientRestRepository.getShipmentLabelsInTypeZpl2("Bearer 1234567890", "3"))
                .thenReturn("etiqueta-03");


    }

}
