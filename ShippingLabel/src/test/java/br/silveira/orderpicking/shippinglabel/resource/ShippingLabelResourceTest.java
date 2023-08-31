package br.silveira.orderpicking.shippinglabel.resource;

import br.silveira.orderpicking.auth.jwt.JwtTokenUtil;
import br.silveira.orderpicking.auth.security.UserDetailsImpl;
import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelOrderDto;
import br.silveira.orderpicking.shippinglabel.mock.Mock;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
        ResponseEntity<Resource> ret = shippingLabelResource.getShippingLabelWithZplCodeOrderedInFile(null, null, null);
        Assert.isTrue(ret.getStatusCode().isError());
        List<ShippingLabelOrderDto> orders = createShippingLabelOrderDto();
        ret = shippingLabelResource.getShippingLabelWithZplCodeOrderedInFile(orders, "title", false);

    }



}
