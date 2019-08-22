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
public class BookCopyDto {
    private long id;
    private Title titleId;
    private String status;
    private List<Borrowing> borrowings = new ArrayList<>();
}
