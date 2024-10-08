package ru.kamil.springproject.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kamil.springproject.Models.Book;
import ru.kamil.springproject.Models.Person;
import ru.kamil.springproject.Repositories.BookRepository;


import java.util.Date;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleService peopleService;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleService peopleService) {
        this.bookRepository = bookRepository;
        this.peopleService = peopleService;
    }


    @Transactional
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book getBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public Book getBook(String name) {
        return bookRepository.findBookByName(name);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book bookToBeUpdated) {
        bookToBeUpdated.setId(id);
        bookRepository.save(bookToBeUpdated);
    }

    @Transactional
    public void delete(int id) {
        Book book = getBook(id);
        bookRepository.delete(book);
    }

    @Transactional
    public List<Book> pagination(int page,int itemsPerPage,boolean sortByYear){
        if (sortByYear) return bookRepository.findAll(PageRequest.of(page,itemsPerPage, Sort.by("yearOfProduction"))).getContent();
        else return bookRepository.findAll(PageRequest.of(page,itemsPerPage)).getContent();
    }
    @Transactional
    public List<Book> findByFirstLetters(String firstLetters){
        return bookRepository.findByNameStartingWith(firstLetters);
    }
    @Transactional
    public List<Book> findBooksByPerson(Person person){
        peopleService.setOverdue(person);
        return bookRepository.findByPerson(person);
    }

}
