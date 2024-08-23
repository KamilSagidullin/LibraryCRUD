package ru.kamil.springproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kamil.springproject.Models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    public Book findBookByName(String name);
}
