package org.adityachandel.libraryapispring.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.adityachandel.libraryapispring.model.Book;
import org.adityachandel.libraryapispring.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/library")
@Api(tags = "Book Endpoints", description = "  ")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/all")
    @ApiOperation(value = "Fetches all books present in the library")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/book/{bookId}")
    @ApiOperation(value = "Fetches a book by bookId from the library")
    public Optional<Book> getAllBooks(@PathVariable long bookId) {
        return bookService.findBookById(bookId);
    }

    @PostMapping("/book")
    @ApiOperation(value = "Creates a new book in the library")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/book")
    @ApiOperation(value = "Updates a book that's already present in the library")
    public Book updateBook(@Valid @RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/book/{bookId}")
    @ApiOperation(value = "Deletes a book from the library")
    public void deleteBook(@PathVariable long bookId) {
        bookService.deleteBook(bookId);
    }

}
