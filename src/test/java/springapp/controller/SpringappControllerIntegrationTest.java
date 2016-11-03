package springapp.controller;

import com.springapp.spring.config.ApplicationConfig;
import com.springapp.spring.config.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Main SpringApp controller integration test
 * User: Gerardo Fernández
 * Date: 15/11/2013
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { SecurityConfig.class, ApplicationConfig.class } )
@WebAppConfiguration
//@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class SpringappControllerIntegrationTest {

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMVC;

    @Before
    public void setUp() {
        mockMVC = MockMvcBuilders.webAppContextSetup( context ).addFilter( springSecurityFilterChain ).build();
    }

    @Test
    public void testBadLogin() throws Exception {
        mockMVC.perform( formLogin( "/login" )
                         .user( "foo" )
                         .password ( "bar" ) )
                .andExpect( unauthenticated() )
                .andExpect( status().isFound() )
                .andExpect( redirectedUrl( "/login?error=true" ) );
    }

    @Test
    public void testLogin() throws Exception {
        mockMVC.perform( formLogin( "/login" )
                          .user( "admin" )
                          .password( "admin" ) )
                .andExpect( authenticated() )
                .andExpect( status().isFound() )
                .andExpect( redirectedUrl( "/home" ) );
   }

    @Test
    public void testHomePage() throws Exception {
        mockMVC.perform( get( "/" ).with( admin() ) )
                .andExpect( authenticated() )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "person", hasProperty( "name", equalTo( "Administrador" ) ) ) );
    }

    @Test
    public void testChangePerson() throws Exception {
        mockMVC.perform( post( "/identify" ).param( "name", "Foo" ).with( csrf() ).with( admin() ) )
                .andExpect( authenticated() )
                .andExpect( status().isFound() )
                .andExpect( redirectedUrl( "/home" ) );

        mockMVC.perform( get( "/home" ).with( admin() ) )
                .andExpect( authenticated() )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "person", hasProperty( "name", equalTo( "Foo" ) ) ) );
    }

    private static RequestPostProcessor admin() {
        return user( "admin" ).password( "admin" );
    }

}