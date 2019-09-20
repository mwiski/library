package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Access;
import javax.persistence.AccessType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerDto {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate accountCreationDate;
    private List<BorrowingDto> borrowings = new ArrayList<>();
}
