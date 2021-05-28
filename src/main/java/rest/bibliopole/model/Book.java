package rest.bibliopole.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rest.bibliopole.model.base.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "author", "publishing", "year"}, name = "books_idx")})
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Book(String name, String author, String publishing, Integer year, Integer pages, Double cost) {
        this (null, name, author, publishing, year, pages, cost);
    }

    public Book(Long id, String name, String author, String publishing, Integer year, Integer pages, Double cost) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishing = publishing;
        this.year = year;
        this.pages = pages;
        this.cost = cost;
    }
}
