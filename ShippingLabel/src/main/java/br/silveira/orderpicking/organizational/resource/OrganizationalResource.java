package br.silveira.orderpicking.organizational.resource;

import br.silveira.orderpicking.organizational.entity.Company;
import br.silveira.orderpicking.organizational.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizational")
public class OrganizationalResource {
    private static final Logger log = LoggerFactory.getLogger(OrganizationalResource.class);
    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/save")
    public ResponseEntity<Company> save(@RequestBody Company company){
        try {
            Company save = companyRepository.save(company);
            return ResponseEntity.ok(save);
        }catch(Exception e){
            log.error("Error to save company name {}", company.getName(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/existByCnpj/{cnpj}")
    public ResponseEntity<Boolean> existByCnpj(@PathVariable String cnpj) {
        try {
            boolean ret = companyRepository.existsByCnpj(cnpj);
            return ResponseEntity.ok(ret);
        }catch(Exception e){
            log.error("Error to check exist  company cnpj  {}", cnpj, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        companyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
