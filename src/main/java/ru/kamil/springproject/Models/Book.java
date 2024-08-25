package ru.kamil.springproject.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name="book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;
    @NotEmpty
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters")
    @Column(name = "author")
    private String author;
    @Min(value = 0, message = "Year of production should be greater than 0")
    @Column(name = "year_of_production")
    private int yearOfProduction;
    @ManyToOne()
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    @Column(name = "managed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date managedAt;

    @Transient
    private boolean overdue;

    public Book() {

    }

    public Book(int id, String name, String author, int yearOfProduction) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return name + ", " + author + ", " + yearOfProduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getManagedAt() {
        return managedAt;
    }

    public void setManagedAt(Date managedAt) {
        this.managedAt = managedAt;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
