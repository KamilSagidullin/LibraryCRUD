package ru.kamil.springproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kamil.springproject.DAO.PersonDAO;
import ru.kamil.springproject.Models.Person;
import ru.kamil.springproject.Services.BookService;
import ru.kamil.springproject.Services.PeopleService;
import ru.kamil.springproject.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PeopleService peopleService;
    private final BookService bookService;
    private final PersonDAO personDAO;

    private final PersonValidator personValidator;


    @Autowired
    public PersonController(PeopleService peopleService, BookService bookService, PersonDAO personDAO, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }
    

    @GetMapping()
    public String helloPage(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "/PersonViews/personIndex";
    }

    @GetMapping("/create")
    public String createPerson(@ModelAttribute("person") Person person) {
        return "/PersonViews/createPerson";
    }

    @PostMapping()
    public String addingPersonToDataBase(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "PersonViews/createPerson";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.getPerson(id));
        model.addAttribute("books", bookService.findBooksByPerson(peopleService.getPerson(id)));
        return "/PersonViews/currentPerson";
    }

    @GetMapping("/{id}/edit")
    public String updatePerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.getPerson(id));
        return "/PersonViews/editingPerson";
    }

    @PatchMapping("/{id}")
    public String updatingPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "PersonViews/editingPerson";
        }
        peopleService.update(person.getId(),person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
