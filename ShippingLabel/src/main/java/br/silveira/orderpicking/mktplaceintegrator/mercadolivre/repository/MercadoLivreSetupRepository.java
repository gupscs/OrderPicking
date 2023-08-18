package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository;

import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.entity.MercadoLivreSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MercadoLivreSetupRepository extends JpaRepository<MercadoLivreSetup, Long>, JpaSpecificationExecutor<MercadoLivreSetup> {


    public Optional<MercadoLivreSetup> findByCompanyIdAndSellerId(Long companyId, String sellerId);

    public Optional<MercadoLivreSetup> findByCompanyIdAndAuthorizationCode(Long companyId, String authorizationCode);

    public List<MercadoLivreSetup> findAllByCompanyId(Long companyId);
}
