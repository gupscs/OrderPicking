package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository;

import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.entity.MercadoLivreSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MercadoLivreSetupRepository extends JpaRepository<MercadoLivreSetup, Long> {


    public Optional<MercadoLivreSetup> findByCompanyIdAndSellerId(String companyId, String sellerId);

    public Optional<MercadoLivreSetup> findByCompanyIdAndAuthorizationCode(Long companyId, String authorizationCode);
}
