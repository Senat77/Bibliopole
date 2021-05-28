package rest.bibliopole.model.dto;

import lombok.Data;

@Data
public class BookRespDTO {

    private Integer id;
    private String name;
    private String author;
    private String publishing;
    private Integer year;
    private Integer pages;
    private Double cost;
}
