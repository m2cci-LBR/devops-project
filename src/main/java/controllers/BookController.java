package controllers;

import entities.Book;
import exceptions.RessourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.IBookService;

import java.util.List;

@RestController
@Api(value = "/books", description = "Books Api" ,produces = "application/json")
public class BookController {

    @Autowired
    private IBookService bookService;

    public BookController(){
        //init
    }

    @RequestMapping(path = "/books" ,method = RequestMethod.GET)
    @ApiOperation(value = "list_all_books" , response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Books retrieved" , response = Book.class)
    })
    public ResponseEntity<List<Book>> findAllBooks(){
        List<Book> books=this.bookService.getAllBooks();
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    @RequestMapping(path = "/books" , method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        this.bookService.createBook(book);
        return new ResponseEntity<Book>(book,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/books/{id}" ,method = RequestMethod.GET)
    public ResponseEntity<Book> findBookById(@PathVariable("id") long id){
     Book result=this.bookService.getBookById(id)
                                 .orElseThrow(() -> new RessourceNotFoundException("Book not found fot this id : " +id));
     return new ResponseEntity<Book>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/books/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id,@RequestBody Book book){
        this.bookService.getBookById(id)
                        .orElseThrow(() -> new RessourceNotFoundException("Book not found for this id : "+id));
        book.setId(id);
        this.bookService.createBook(book);
        return new ResponseEntity<Book>(book,HttpStatus.OK);
    }
    @RequestMapping(value = "/books/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id){
        try {
            this.bookService.deleteBookById(id);
            return new ResponseEntity<String>("Book is deleted successfully",HttpStatus.OK);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

}
