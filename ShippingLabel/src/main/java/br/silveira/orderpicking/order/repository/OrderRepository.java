package br.silveira.orderpicking.order.repository;

import br.silveira.orderpicking.order.entity.MktPlaceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface OrderRepository extends JpaRepository<MktPlaceOrder, Long> {
}
