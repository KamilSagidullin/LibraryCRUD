package ru.kamil.springproject.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kamil.springproject.Models.Book;
import ru.kamil.springproject.Models.Person;
import ru.kamil.springproject.Services.BookService;

import java.util.List;

@Component
public class BookDAO {
    private final EntityManager entityManager;
    private final BookService bookService;

    @Autowired
    public BookDAO(EntityManager entityManager, BookService bookService) {
        this.entityManager = entityManager;
        this.bookService = bookService;
    }

    @Transactional
    public Person getPersonById(int bookId){
        Query query = entityManager.createQuery("SELECT p FROM Person p JOIN p.bookList b WHERE b.id = :bookId");
        query.setParameter("bookId", bookId);

        List<Person> persons = query.getResultList();

        if (persons.isEmpty()) {
            return null;
        } else {
            return persons.get(0);
        }
    }
    @Transactional
    public void setBook(int id, Person person){
        Session session = entityManager.unwrap(Session.class);
        Book book = bookService.getBook(id);
        book.setPerson(person);

    }
    @Transactional
    public void free(int id){
        Book book = bookService.getBook(id);
        book.setPerson(null);
    }
}
