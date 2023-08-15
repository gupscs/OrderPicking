package br.silveira.orderpicking.organizational.repository;

import br.silveira.orderpicking.organizational.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    public boolean existsByIdentificationNo(String identificationNo);

}
