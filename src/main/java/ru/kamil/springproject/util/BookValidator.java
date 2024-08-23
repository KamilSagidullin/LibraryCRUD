package ru.kamil.springproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kamil.springproject.Models.Book;
import ru.kamil.springproject.Services.BookService;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookService.getBook(book.getName()) != null){
            errors.rejectValue("name","","Book is already exist");
        }
        if (book.getYearOfProduction() < 1900){
            errors.rejectValue("yearOfProduction","","Year of production must be greater than 1900");
        }
    }
}
