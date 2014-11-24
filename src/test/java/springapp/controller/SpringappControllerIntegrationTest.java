package springapp.controller;

import com.springapp.spring.config.ApplicationConfig;
import com.springapp.spring.config.SecurityConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Main SpringApp controller integration test
 * User: Gerardo Fernández
 * Date: 15/11/2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration ( classes = { SecurityConfig.class, ApplicationConfig.class } )
@WebAppConfiguration
//@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class SpringappControllerIntegrationTest {

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMVC;

    @Before
    public void setUp() {
        mockMVC = MockMvcBuilders.webAppContextSetup( context ).addFilter( springSecurityFilterChain ).build();
    }

    @Test
    public void testBadLogin() throws Exception {
        mockMVC.perform( post( "/login" )
                .param( "username", "foo" )
                .param( "password", "bar" ) )
                .andExpect( status().isFound() )
                .andExpect( redirectedUrl( "/login?error=true" ) );
    }

    @Test
    public void testLogin() throws Exception {
        mockMVC.perform( post( "/login" )
                         .param( "username", "admin" )
                         .param( "password", "admin" ) )
                .andExpect( status().isFound() )
                .andExpect( redirectedUrl( "/home" ) );
   }

    @Test
    public void testHomePage() throws Exception {
        HttpSession session = login();

        Assert.assertNotNull( session );

        mockMVC.perform( get( "/" ).session( ( MockHttpSession) session ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "person", hasProperty( "name", equalTo( "Administrador" ) ) ) );
    }

    @Test
    public void testChangePerson() throws Exception {
        HttpSession session = login();

        Assert.assertNotNull( session );

        mockMVC.perform( post( "/identify" ).param( "name", "Foo" ).session( ( MockHttpSession) session ) )
                .andExpect( status().isFound() )
                .andExpect( redirectedUrl( "/home" ) );

        mockMVC.perform( get( "/home" ).session( ( MockHttpSession) session ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "person", hasProperty( "name", equalTo( "Foo" ) ) ) );
    }

    private HttpSession login() throws Exception {
        return mockMVC.perform( post( "/login" )
                                .param( "username", "admin" )
                                .param( "password", "admin" ) )
                       .andExpect( status().isFound() )
                       .andExpect( redirectedUrl( "/home" ) )
                       .andReturn()
                       .getRequest()
                       .getSession();
    }

}