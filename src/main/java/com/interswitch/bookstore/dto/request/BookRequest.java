package com.interswitch.bookstore.dto.request;


import com.interswitch.bookstore.enums.Genre;
import com.interswitch.bookstore.validator.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message = "genre is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "title must contain only numbers and letters")
    private String title;
    @NotBlank(message = "genre must be either FICTION|THRILLER|MYSTERY|POETRY|HORROR|SATIRE")
    @ValueOfEnum(enumClass = Genre.class)
    private String genre;
    @NotBlank(message = "genre is required")
    @Pattern(regexp = "^[0-9-]+$", message = "isbn must contain only numbers and dash(-)")
    private String isbn;
    @NotBlank(message = "author is required")
    private String author;
    @NotBlank(message = "yearOfPublication is required")
    private String yearOfPublication;
}
