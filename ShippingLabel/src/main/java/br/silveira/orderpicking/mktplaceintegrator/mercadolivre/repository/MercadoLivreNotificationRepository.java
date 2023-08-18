package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository;

import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.entity.MercadoLivreNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercadoLivreNotificationRepository extends JpaRepository<MercadoLivreNotification, Long> {
}
