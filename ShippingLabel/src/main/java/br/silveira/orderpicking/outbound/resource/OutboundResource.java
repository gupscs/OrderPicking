package br.silveira.orderpicking.outbound.resource;

import br.silveira.orderpicking.common.resource.CommonResource;
import br.silveira.orderpicking.outbound.dto.OutboundCreateDto;
import br.silveira.orderpicking.outbound.entity.Outbound;
import br.silveira.orderpicking.outbound.repository.OutboundRepository;
import br.silveira.orderpicking.outbound.service.OutboundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/outbound")
@Slf4j
public class OutboundResource extends CommonResource {

    @Autowired
    private OutboundRepository outboundRepository;

    @Autowired
    private OutboundService outboundService;



    @PostMapping
    public ResponseEntity<Void> createOutbound(@Valid @RequestBody OutboundCreateDto dto){
        try {
            Long id = outboundService.createOutbound(dto);
            return ResponseEntity.created(URI.create("/"+id)).build();
        }catch(Exception e){
            log.error(getLogPrefix()+" createOutbound error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Outbound> getOutboundById(@Valid @PathVariable Long id){
        try {
            Optional<Outbound> outbound = outboundRepository.findById(id);
            if(outbound.isPresent()){
                return ResponseEntity.ok(outbound.get());
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            log.error(getLogPrefix()+" getOutboundById error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
