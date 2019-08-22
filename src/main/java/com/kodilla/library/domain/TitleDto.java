package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Access;
import javax.persistence.AccessType;
import java.util.ArrayList;
import java.util.List;

@Getter
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {
    private long id;
    private String title;
    private String author;
    private int yearOfPublication;
    private List<BookCopy> copies = new ArrayList<>();
}
