package br.silveira.orderpicking.outbound.repository;

import br.silveira.orderpicking.outbound.entity.Picking;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface PickingRepository extends JpaRepository<Picking, Long> {
}
