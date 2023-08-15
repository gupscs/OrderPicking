package br.silveira.orderpicking.organizational.resource;

import br.silveira.orderpicking.organizational.entity.Company;
import br.silveira.orderpicking.organizational.repository.CompanyRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organizational")
public class OrganizationalResource {
    private static final Logger log = LoggerFactory.getLogger(OrganizationalResource.class);
    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/save")
    @ApiOperation(value = "Save Company")
    @ApiResponse(code = 200 , message = "saved")
    public ResponseEntity<Company> save(@RequestBody Company company){
        try {
            Company save = companyRepository.save(company);
            return ResponseEntity.ok(save);
        }catch(Exception e){
            log.error("Error to save company name {}", company.getName(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/existByIdentificationNo/{identificationNo}")
    @ApiOperation(value = "Check if Company exist by Identification No")
    @ApiResponse(code = 200 , message = "success check")
    public ResponseEntity<Boolean> existByIdentificationNo(@Valid @PathVariable String identificationNo) {
        try {
            boolean ret = companyRepository.existsByIdentificationNo(identificationNo);
            return ResponseEntity.ok(ret);
        }catch(Exception e){
            log.error("Error to check exist  company identificationNo  {}", identificationNo, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete company by Id")
    @ApiResponse(code = 200 , message = "successfully deleted")
    public ResponseEntity<?> delete(@PathVariable Long id){
        companyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
