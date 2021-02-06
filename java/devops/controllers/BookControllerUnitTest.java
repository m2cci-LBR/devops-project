package devops.controllers;

import devops.entities.Book;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import devops.services.IBookService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)

public class BookControllerUnitTest {
    /*
     *  MockMvc for testing MVC controllers without starting a full HTTP server
     */

    @Autowired
    private MockMvc mvc;

    // this is a mock for book service
    @MockBean
    private IBookService bookService;

    @Before
    public  void setUp(){

        Book b1=new Book("serviceTest1","g1","description....","Author....","0","",new Date(),0);
        Book b2=new Book("serviceTest2","g1","description....","Author....","0","",new Date(),0);
        b1.setId((long)1);
        b1.setId((long)2);

        List<Book> books = new ArrayList<Book>(Arrays.asList(b1,b2));

        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        Mockito.when(bookService.getBookById(1)).thenReturn(Optional.of(b1));
    }

     /*
      In this testCase we will use MockMvc object to perform a get request to our application and to verify that it responds as expected.
     */
    @Test
    public void getAllBooksTest() throws Exception{
       mvc.perform(MockMvcRequestBuilders.get("/books")
                                          .contentType(MediaType.APPLICATION_JSON))
                                          .andExpect(status().isOk())
                                          .andExpect(jsonPath("$", Matchers.hasSize(2)))
                                          .andExpect(jsonPath("$[0].title",Matchers.is("serviceTest1")))
                                          .andExpect(jsonPath("$[1].title",Matchers.is("serviceTest2")));
    }

    @Test
    public void getBookByIdTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/book/1")
                                          .contentType(MediaType.APPLICATION_JSON))
                                          .andExpect(status().isOk())
                                          .andExpect(jsonPath("$.*",Matchers.hasSize(9)))
                                          .andExpect(jsonPath("$.id",Matchers.is(1)))
                                          .andExpect(jsonPath("$.title",Matchers.is("serviceTest1")));
    }

    @Test
    public void createBookTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",Matchers.hasSize(9)))
                .andExpect(jsonPath("$.id",Matchers.is(1)))
                .andExpect(jsonPath("$.title",Matchers.is("serviceTest1")));
    }

}
