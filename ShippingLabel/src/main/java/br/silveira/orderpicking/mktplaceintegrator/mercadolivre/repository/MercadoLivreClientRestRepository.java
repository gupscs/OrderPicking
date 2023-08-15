package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository;

import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.dto.MercadoLivreTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "mercadoLivreService", url = "${mercadolivre.url}")
public interface MercadoLivreClientRestRepository {

    @RequestMapping(method = RequestMethod.POST, value="/oauth/token" , produces = "application/json" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public MercadoLivreTokenDto getToken(@RequestBody Map<String, ?> form);

    @RequestMapping(method = RequestMethod.POST, value="/oauth/token" )
    public MercadoLivreTokenDto refreshToken(@RequestParam("grant_type") String grantType, @RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret, @RequestParam("refresh_token") String refreshToken);

    /**
     *
     * @param bearerToken
     * @param sellerId
     * @param filterParams
     *       item: ID o título
     *       tags: pode haver vários estados separados por ','
     *       tags.not: pode haver vários estados separados por ','
     *       q: é um campo genérico que permite pesquisar por:
     *
     *       ID da order
     *       ID do item
     *       título do item
     *       nickname da contraparte
     *       order.status: pode haver vários estados separados por ','
     *       order.date_last_updated.from : data da última modificação da order
     *       order.date_last_updated.to: data da última modificação da order
     *       order.date_created.from
     *       order.date_created.to
     *       order.date_closed.from
     *       order.date_closed.to
     *       mediations.stage: pode haver vários estados separados por ','
     *       mediations.status: pode haver vários estados separados por ','
     *       feedback.status: pode haver vários estados separados por ','
     *       feedback.sale.rating: pode haver vários estados separados por ','
     *       feedback.sale.fulfilled
     *       feedback.purchase.rating: pode haver vários estados separados por ','
     *       feedback.purchase.fulfilled
     * @return
     * Example: curl -X GET -H 'Authorization: Bearer $ACCESS_TOKEN'  https://api.mercadolibre.com/orders/search?seller=$SELLER_ID&order.date_created.from=2015-07-01T00:00:00.000-00:00&order.date_created.to=2015-07-31T00:00:00.000-00:00
     */
    @RequestMapping(method = RequestMethod.GET, value = "orders/search?seller={$SELLER_ID}&{$FILTERS}", produces = "application/json")
    public Object listOrder(@RequestHeader(value = "Authorization", required = true) String bearerToken, @PathVariable("$SELLER_ID") String sellerId, @PathVariable("$FILTERS") String filterParams);

    //TODO TESTAR SHIPMENTS SEPARADOS POR VIRGULA
    @RequestMapping(method = RequestMethod.GET, value = "/shipment_labels?shipment_ids={$SHIPMENT_ID}&response_type=zpl2", produces = "application/json")
    public Object getShipmentLabelsInTypeZpl2(@RequestHeader(value = "Authorization", required = true) String bearerToken, @PathVariable("$SHIPMENT_ID") String shipmentId);

    @RequestMapping(method = RequestMethod.GET, value = "/shipment_labels?shipment_ids={$SHIPMENT_ID}&savePdf=Y", produces = "application/json")
    public Object getShipmentLabelsInPdf(@RequestHeader(value = "Authorization", required = true) String bearerToken, @PathVariable("$SHIPMENT_ID") String shipmentId);
}
