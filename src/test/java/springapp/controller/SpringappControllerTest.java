package springapp.controller;

import com.springapp.controller.SpringappController;
import com.springapp.spring.config.ApplicationConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Main SpringApp controller test
 * User: Gerardo Fernández
 * Date: 15/11/2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration( classes = { ApplicationConfig.class })
public class SpringappControllerTest {

    private MockMvc mockMVC;

    @Before
    public void setUp() {
        mockMVC = MockMvcBuilders.standaloneSetup( new SpringappController() ).build();
    }

    @Test
    public void testLoadHomePage() throws Exception {
        mockMVC.perform( get("/") )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "name", "SpringApp" ) );
    }

}
