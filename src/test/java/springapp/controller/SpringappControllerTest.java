package springapp.controller;

import com.springapp.model.Person;
import com.springapp.spring.config.ApplicationConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Main SpringApp controller test
 * User: Gerardo Fernández
 * Date: 15/11/2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration ( classes = { ApplicationConfig.class } )
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class SpringappControllerTest {

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
    public void test_BadLogin() throws Exception {
        mockMVC.perform( post( "/j_spring_security_check" )
                         .param( "j_username", "foo" )
                         .param( "j_password", "bar" ) )
                .andExpect( status().isMovedTemporarily() )
                .andExpect( redirectedUrl( "/login?error=true" ) );
    }

    @Test
    public void test_Login() throws Exception {
        mockMVC.perform( post( "/j_spring_security_check" )
                         .param( "j_username", "admin" )
                         .param( "j_password", "admin" ) )
                .andExpect( status().isMovedTemporarily() )
                .andExpect( redirectedUrl( "/home" ));
   }

    @Test
    public void test_01_LoadHomePage() throws Exception {
        HttpSession session = mockMVC.perform( post( "/j_spring_security_check" )
                                               .param( "j_username", "admin" )
                                               .param( "j_password", "admin" ) )
                                      .andExpect( status().isMovedTemporarily() )
                                      .andExpect( redirectedUrl( "/home" ) )
                                      .andReturn()
                                      .getRequest()
                                      .getSession();

        Assert.assertNotNull( session );

        mockMVC.perform( get( "/" ).session( ( MockHttpSession) session ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "name", "Administrador" ) );
    }

    @Test
    public void test_02_ChangeGreetings() throws Exception {
        HttpSession session = mockMVC.perform( post( "/j_spring_security_check" )
                                               .param( "j_username", "admin" )
                                               .param( "j_password", "admin" ) )
                                      .andExpect( status().isMovedTemporarily() )
                                      .andExpect( redirectedUrl( "/home" ) )
                                      .andReturn()
                                      .getRequest()
                                      .getSession();

        Assert.assertNotNull( session );

        Person person = new Person();
        person.setName( "foo" );

        mockMVC.perform( post( "/identify", new Object[0] ).param( "name", "foo" ).session( ( MockHttpSession) session ) )
                .andExpect( status().isMovedTemporarily() )
                .andExpect( redirectedUrl( "/home" ) );

        mockMVC.perform( get( "/home" ).session( ( MockHttpSession) session ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "name", "foo" ) );
    }

}
