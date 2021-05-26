package group.bibliopole.model;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "books")
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
