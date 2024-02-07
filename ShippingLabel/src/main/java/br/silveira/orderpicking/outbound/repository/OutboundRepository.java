package br.silveira.orderpicking.outbound.repository;

import br.silveira.orderpicking.outbound.entity.Outbound;
import br.silveira.orderpicking.shippingLabel.entity.ShippingLabelOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface OutboundRepository extends JpaRepository<Outbound, Long> {
}
