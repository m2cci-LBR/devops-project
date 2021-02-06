import com.fasterxml.jackson.databind.ObjectMapper;
import devops.DemoApplication;
import devops.entities.Book;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)

/*
 * Because we have a full application context, including web controllers, Spring Data repositories, and data sources,
 * @SpringBootTest is very convenient for integration tests that go through all layers of the application.
 * It will start up an application context to be used in a test.
 * classes: tell Spring Boot which application class to use to create an application context
 */

@SpringBootTest(classes = DemoApplication.class)


//The following annotation will add a MockMvc instance to the application context that will be injected to our "mvc" field
@AutoConfigureMockMvc
public class DemoApplicationIT {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @Test
    public  void createBookThroughAllLayers() throws  Exception {

        Book b1= new Book("createBookThroughAllLayers","g1","description ...","author ....","0","",null,0);

        mvc.perform(MockMvcRequestBuilders.post("/book")
                                           .contentType("application/json")
                                           .content(objectMapper.writeValueAsString(b1)))
                                           .andExpect(status().isCreated())
                                           // to test that the result is an object containing 9 members
                                            .andExpect(jsonPath("$.*", Matchers.hasSize(9)))
                                            .andExpect(jsonPath("$.title",Matchers.is("createBookThroughAllLayers")));
    }

    @Test
    public void findAllBooksThroughAllLayers() throws  Exception{
        mvc.perform(MockMvcRequestBuilders.get("/books")
                                           .contentType(MediaType.APPLICATION_JSON))
                                           .andExpect(status().isOk());
    }

}
