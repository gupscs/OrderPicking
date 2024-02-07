package br.silveira.orderpicking.shippingLabel.resource;

import br.silveira.orderpicking.auth.jwt.JwtTokenUtil;
import br.silveira.orderpicking.auth.security.UserDetailsImpl;
import br.silveira.orderpicking.shippingLabel.dto.ShippingLabelDownloadRequestDto;
import br.silveira.orderpicking.shippingLabel.mock.Mock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShippingLabelResourceTest extends Mock{

    @Autowired
    ShippingLabelResource shippingLabelResource;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @BeforeAll
    public void init() {
        super.init();
        UserDetailsImpl userDetails = new UserDetailsImpl("gupscs@gmail.com", Long.valueOf("1"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void getShippingLabelWithZplCodeOrderedInFileTest() {
        //ResponseEntity<Resource> ret = shippingLabelResource.getShippingLabelWithZplCodeOrderedInFile(null, null, null);
        //Assert.isTrue(ret.getStatusCode().isError());
        List<ShippingLabelDownloadRequestDto> orders = createShippingLabelOrderDto();
        ResponseEntity<Resource> ret = shippingLabelResource.getShippingLabelWithZplCodeOrderedInFile(orders, "title", false);

    }



}
