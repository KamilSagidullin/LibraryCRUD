package ru.kamil.springproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kamil.springproject.DAO.BookDAO;
import ru.kamil.springproject.Models.Book;
import ru.kamil.springproject.Models.Person;
import ru.kamil.springproject.Services.BookService;
import ru.kamil.springproject.Services.PeopleService;
import ru.kamil.springproject.util.BookValidator;

@Controller
@RequestMapping("/books")

public class BookController {
    private final PeopleService peopleService;
    private final BookService bookService;
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService, BookDAO bookDAO, BookValidator bookValidator) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }


    @GetMapping()
    public String helloPage(@RequestParam(required = false, value = "page") Integer page,
                            @RequestParam(required = false, value = "books_per_page") Integer booksPerPage,
                            @RequestParam(required = false, value = "sort_by_year") Boolean sortByYear, Model model) {
        if (page == null) page = 0;
        if (booksPerPage == null) booksPerPage = bookService.getBooks().size();
        if (sortByYear == null) sortByYear = false;

        model.addAttribute("books", bookService.pagination(page, booksPerPage,sortByYear));
        return "/BookViews/bookIndex";
    }

    @GetMapping("/create")
    public String createBook(@ModelAttribute("book") Book book) {
        return "/BookViews/createBook";
    }

    @PostMapping()
    public String addingBookToDataBase(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/BookViews/createBook";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("voidPerson") Person person) {
        model.addAttribute("book", bookService.getBook(id));
        model.addAttribute("person", bookDAO.getPersonById(person.getId()));
        model.addAttribute("people", peopleService.findAll());
        return "/BookViews/currentBook";
    }

    @GetMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "/BookViews/editingBook";
    }

    @PatchMapping("/{id}")
    public String updatingBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/BookViews/editingBook";
        }
        bookService.update(book.getId(), book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/set")
    public String setBook(@ModelAttribute("book") Book book, @ModelAttribute("person") Person person, @PathVariable("id") int id) {
        bookDAO.setBook(id, person);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        bookDAO.free(id);
        return "redirect:/books/{id}";
    }

}
