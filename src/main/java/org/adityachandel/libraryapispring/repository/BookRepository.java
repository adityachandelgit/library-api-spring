package org.adityachandel.libraryapispring.repository;

import org.adityachandel.libraryapispring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
