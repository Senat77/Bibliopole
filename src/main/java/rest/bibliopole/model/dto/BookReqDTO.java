package rest.bibliopole.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReqDTO extends BaseEntityDTO {

    @NotBlank(groups = {New.class})
    @Size(min = 3, max = 32)
    private String name;

    @NotBlank(groups = {New.class})
    @Size(min = 3, max = 32)
    private String author;

    @NotBlank(groups = {New.class})
    @Size(min = 3, max = 32)
    private String publishing;

    @NotBlank
    @NonNull
    private Integer year;

    @NotBlank
    @NonNull
    private Integer pages;

    @NotBlank
    @NonNull
    private Double cost;
}
