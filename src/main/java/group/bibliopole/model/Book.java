package group.bibliopole.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String author;

    private String publishing;

    private int year;

    private int pages;

    private float cost;
}
