package rest.bibliopole.model;

import lombok.Getter;
import lombok.Setter;
import rest.bibliopole.model.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "author", "publishing", "year"}, name = "books_idx")})

public class Book extends BaseEntity {

    @NotBlank
    @Column (name = "name", nullable = false, length = 150)
    private String name;

    @NotBlank
    @Column (name = "author", nullable = false, length = 100)
    private String author;

    @NotBlank
    @Column (name = "publishing", nullable = false, length = 100)
    private String publishing;

    @NotBlank
    @Column (name = "year", nullable = false)
    private Integer year;

    @NotBlank
    @Column (name = "pages", nullable = false)
    private Integer pages;

    @NotBlank
    @Column (name = "cost", precision = 12, scale = 2, nullable = false)
    private Double cost;

    public Book() {
    }

    public Book(String name, String author, String publishing, Integer year, Integer pages, Double cost) {
        this (null, name, author, publishing, year, pages, cost);
    }

    public Book(Integer id, String name, String author, String publishing, Integer year, Integer pages, Double cost) {
        super(id);
        this.name = name;
        this.author = author;
        this.publishing = publishing;
        this.year = year;
        this.pages = pages;
        this.cost = cost;
    }
}
