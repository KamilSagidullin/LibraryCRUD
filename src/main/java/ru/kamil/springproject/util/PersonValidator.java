package ru.kamil.springproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kamil.springproject.Models.Person;
import ru.kamil.springproject.Services.PeopleService;


@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.getPerson(person.getName()) != null){
            errors.rejectValue("name","","Name is already taken");
        }
        if (person.getYearOfBirthday() <= 1900){
            errors.rejectValue("yearOfBirthday","","Year MUST be greater than 1900");
        }

    }
}
