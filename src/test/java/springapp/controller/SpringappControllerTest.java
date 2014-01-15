package springapp.controller;

import com.springapp.controller.SpringappController;
import com.springapp.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Main SpringApp controller unit test
 * User: Gerardo Fernández
 * Date: 15/01/2013
 */
@RunWith( MockitoJUnitRunner.class )
public class SpringappControllerTest {

    private MockMvc mockMVC;

    @Mock
    private Person personMock;

    @InjectMocks
    private SpringappController springappController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks( this );
        mockMVC = MockMvcBuilders.standaloneSetup( springappController ).build();
    }

    @Test
    public void testHome() throws Exception {
        when( personMock.getName() ).thenReturn( "Administrador" );

        mockMVC.perform( get( "/" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "home" ) )
                .andExpect( model().attribute( "person", hasProperty( "name", equalTo( "Administrador" ) ) ) );
    }

    @Test
    public void testIdentify() throws Exception {
        when( personMock.getName() ).thenReturn( "Administrador" );

        mockMVC.perform( post( "/identify" ).param( "name", "Foo" ) )
                .andExpect( status().isMovedTemporarily() )
                .andExpect( redirectedUrl( "/home" ) )
                .andExpect( model().attribute( "person", hasProperty( "name", equalTo( "Foo" ) ) ) );
    }

}
