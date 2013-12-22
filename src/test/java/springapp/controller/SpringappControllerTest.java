package springapp.controller;

import com.springapp.controller.SpringappController;
import com.springapp.model.Person;
import com.springapp.spring.config.ApplicationConfig;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private WebApplicationContext context;

    private MockMvc mockMVC;

    @Before
    public void setUp() {
        mockMVC = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    @Test
    public void test_01_LoadHomePage() throws Exception {
        mockMVC.perform( get( "/" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "name", "SpringApp" ) );
    }

    @Test
    public void test_02_ChangeGreetings() throws Exception {
        Person person = new Person();
        person.setName( "foo" );

        mockMVC.perform( post( "/identify", new Object[0] ).param( "name", "foo" ) )
                .andExpect( status().isMovedTemporarily() )
                .andExpect( redirectedUrl( "/home" ) );

        mockMVC.perform( get( "/home" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "name", "foo" ) );
    }
}
