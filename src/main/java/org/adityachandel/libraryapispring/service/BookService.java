package org.adityachandel.libraryapispring.service;

import org.adityachandel.libraryapispring.model.Book;
import org.adityachandel.libraryapispring.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(long bookId) {
        bookRepository.delete(findBookById(bookId).get());
    }
}
