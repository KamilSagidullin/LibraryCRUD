package ru.kamil.springproject.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kamil.springproject.Models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
     Person findByName(String name);
}
