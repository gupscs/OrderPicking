package br.silveira.orderpicking.auth.service;

import br.silveira.orderpicking.auth.payload.Register;
import br.silveira.orderpicking.auth.payload.RegisterCheck;
import br.silveira.orderpicking.organizational.entity.Company;
import br.silveira.orderpicking.organizational.resource.OrganizationalResource;
import br.silveira.orderpicking.sysadmin.entity.User;
import br.silveira.orderpicking.sysadmin.resource.SysAdminResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegiserServiceImpl implements RegisterService {

    @Autowired
    private OrganizationalResource organizationalResource;
    @Autowired
    private SysAdminResource sysAdminResource;

    @Override
    public void register(Register register) {
        Company company = register.toCompany();
        ResponseEntity<Company> companyResponse = organizationalResource.save(company);
        if(companyResponse.getStatusCode().isError()) throw new RuntimeException(String.format("Error to save the Company"));
        User user = register.toUser();
        user.setCompanyId(company.getId());
        ResponseEntity<User> save = sysAdminResource.save(user);
        if (save.getStatusCode().isError()) {
            organizationalResource.delete(company.getId());
            throw new RuntimeException(String.format("Error to save the Company"));
        }
    }

    @Override
    public RegisterCheck registerCheck(String identificationNo, String username) {
        RegisterCheck ret = new RegisterCheck();
        ret.setExistIdentificationNo(organizationalResource.existByIdentificationNo(identificationNo).getBody());
        ret.setExistUsername(sysAdminResource.existByUsername(username).getBody());
        return ret;
    }
}
