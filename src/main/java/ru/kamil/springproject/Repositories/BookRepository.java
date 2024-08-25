package ru.kamil.springproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kamil.springproject.Models.Book;
import ru.kamil.springproject.Models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findBookByName(String name);
    List<Book> findByNameStartingWith(String firstLetters);
    List<Book> findByPerson(Person person);
}
