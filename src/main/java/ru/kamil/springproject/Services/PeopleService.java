package ru.kamil.springproject.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kamil.springproject.Models.Book;
import ru.kamil.springproject.Models.Person;
import ru.kamil.springproject.Repositories.BookRepository;
import ru.kamil.springproject.Repositories.PeopleRepository;

import java.util.Date;
import java.util.List;


@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BookRepository bookRepository) {
        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Transactional
    public Person getPerson(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public Person getPerson(String name) {
        return peopleRepository.findByName(name);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.delete(getPerson(id));
    }

    @Transactional
    public void setOverdue(Person person) {
        List<Book> books = bookRepository.findByPerson(person); // Получение всех книг из базы данных

        Date currentDate = new Date(); // Получение текущей даты

        for (Book book : books) {
            Date borrowDate = book.getManagedAt(); // Получение даты, когда была взята книга

            // Проверка, была ли книга взята более 10 дней назад
            long diffInMillies = currentDate.getTime() - borrowDate.getTime();
            long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

            if (diffInDays > 10) {
                book.setOverdue(true); // Устанавливаем флаг просрочки книги
            }
        }
    }

}
