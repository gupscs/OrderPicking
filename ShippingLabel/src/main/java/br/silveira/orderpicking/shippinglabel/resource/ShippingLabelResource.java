package br.silveira.orderpicking.shippinglabel.resource;

import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelOrderDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shippinglabel")
public class ShippingLabelResource {


    @GetMapping("/getShippingLabelZplCodeOrderBySku")
    public String getShippingLabelZplCodeOrderBySku(@RequestBody List<ShippingLabelOrderDto> orders){
        String ret = null;
        //retornar arquivo para download , limitar por qtd de orders
        return ret;
    }


    /*
    @GetMapping("/getShippingLabelZplCodeOnFileOrderBySku")
    public File getShippingLabelZplCodeOnFileOrderBySku(@RequestBody List<ShippingLabelOrderDto> orders){
        String ret = null;
        //retornar arquivo para download , limitar por qtd de orders
        return ret;
    }

     */


}
