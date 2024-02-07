package br.silveira.orderpicking.order.resource;

import br.silveira.orderpicking.order.entity.Order;
import br.silveira.orderpicking.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderResource {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        try {
        Optional<Order> order = orderRepository.findById(id);
            if(order.isPresent()){
                return ResponseEntity.ok(order.get());
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            log.error("getMktPlaceOrderById error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Order>> getOrderByIds(@Valid @RequestBody List<Long> ids) {
        try {
            List<Order> allById = orderRepository.findAllById(ids);
            if(allById == null && allById.isEmpty()){
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok(allById);
            }
        }catch(Exception e){
            log.error("getMktPlaceOrderById error", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
