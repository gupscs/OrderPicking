package br.silveira.orderpicking.order.repository;

import br.silveira.orderpicking.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface OrderRepository extends JpaRepository<Order, Long> {
}
