package br.silveira.orderpicking.shippingLabel.mock;

import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity.MercadoLivreSetup;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreClientRestRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreSetupRepository;
import br.silveira.orderpicking.shippingLabel.dto.ShippingLabelDownloadRequestDto;
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

    protected List<ShippingLabelDownloadRequestDto> createShippingLabelOrderDto() {
        /**
        List<ShippingLabelRequestDto> ret = new ArrayList<>();
        ShippingLabelRequestDto.ShippingLabelRequestItemDto i = new ShippingLabelRequestDto.ShippingLabelRequestItemDto("abc-1", "Foguete Space X", "fsx-01", 100, 1.00, 100.00);
        List<ShippingLabelRequestDto.ShippingLabelRequestItemDto> item = new ArrayList<>();
        item.add(i);
        ShippingLabelRequestDto d = new ShippingLabelRequestDto(1L, MktPlaceEnum.MERCADO_LIVRE, "1", "order-01", "open", LocalDateTime.now(), "São Paulo", "12345001", "1", "", 100.00, item);
        ret.add(d);
        i = new ShippingLabelRequestDto.ShippingLabelRequestItemDto("abc-2", "Lança Chamas", "lc-01", 100, 1.00, 100.00);
        item = new ArrayList<>();
        item.add(i);
        d = new ShippingLabelRequestDto(1L, MktPlaceEnum.MERCADO_LIVRE, "1", "order-02", "open", LocalDateTime.now(), "Campinas", "12345002", "2", "", 100.00, item);
        ret.add(d);
        i = new ShippingLabelRequestDto.ShippingLabelRequestItemDto("abc-3", "Batmóvel", "bm-01", 100, 1.00, 100.00);
        item = new ArrayList<>();
        item.add(i);
        d = new ShippingLabelRequestDto(1L, MktPlaceEnum.MERCADO_LIVRE, "1", "order-03", "open", LocalDateTime.now(), "Valinhos", "12345003", "3", "", 100.00, item);
        ret.add(d);
        return ret;
         **/
        return null;
    }

}
