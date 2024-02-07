package br.silveira.orderpicking.outbound.repository;

import br.silveira.orderpicking.outbound.entity.Packing;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface PackingRepository extends JpaRepository<Packing, Long> {
}
