package ru.kamil.springproject.DAO;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kamil.springproject.Models.Book;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class PersonDAO {
    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> getBooksByPersonId(int personId) {
        return entityManager.createQuery("select p.bookList from Person p").getResultList();
    }


}
