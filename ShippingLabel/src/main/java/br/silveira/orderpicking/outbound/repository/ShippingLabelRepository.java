package br.silveira.orderpicking.outbound.repository;

import br.silveira.orderpicking.outbound.entity.Outbound;
import br.silveira.orderpicking.outbound.entity.ShippingLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface ShippingLabelRepository extends JpaRepository<ShippingLabel, Long> {


    List<ShippingLabel> findAllByMktPlaceShippingId(List<String> mktPlaceShippingIds);
}
