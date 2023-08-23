package br.silveira.orderpicking.shippinglabel.resource;

import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelOrderDto;
import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelZipFileDto;
import br.silveira.orderpicking.shippinglabel.service.ShippingLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/shippinglabel")
@Slf4j
public class ShippingLabelResource {

    @Autowired
    private ShippingLabelService shippingLabelService;

    @GetMapping("/getZplCodeOrdered/{ordered}/{mixMktPlace}")
    public ResponseEntity<String> getZplCodeOrdered(@Valid @RequestBody List<ShippingLabelOrderDto> orders, @Valid @PathVariable(value = "ordered")  String ordered, @Valid @PathVariable(value = "mixMktPlace") Boolean mixMktPlace){
        try {
            String zplCodeOrdered = shippingLabelService.getZplCodeOrdered(orders, ordered, mixMktPlace);
            return ResponseEntity.ok().body(zplCodeOrdered);
        }catch(Exception e){
            log.error("getShippingLabelZplCodeOrdered - Ordered by {}",ordered, e);
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping("/getShippingLabelWithZplCodeOrderedInFile/{ordered}/{mixMktPlace}")
    public ResponseEntity<Resource> getShippingLabelWithZplCodeOrderedInFile(@RequestBody List<ShippingLabelOrderDto> orders, @Valid @PathVariable(value = "ordered")  String ordered, @Valid @PathVariable(value = "mixMktPlace") Boolean mixMktPlace){
        try {
            ShippingLabelZipFileDto zipFileDto = shippingLabelService.getShippingLabelWithZplCodeOrderedInFile(orders, ordered, mixMktPlace);
            return ResponseEntity.ok()
                    .header("content-disposition", String.format("attachment; filename = %s", zipFileDto.getFileName()))
                    .contentLength(zipFileDto.getFileLenght())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipFileDto.getContent());
        } catch (IOException e) {
            log.error("Erro to create/read temporary zipfile, check the transformation file to bytes", e);
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Erro to get shipping label in file with pdf and zpl codes", e);
            return ResponseEntity.internalServerError().build();
        }
    }




}
