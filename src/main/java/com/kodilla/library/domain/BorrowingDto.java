package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Access;
import javax.persistence.AccessType;
import java.time.LocalDate;

@Getter
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingDto {
    private long id;
    private Exemplar exemplar;
    private Borrower borrower;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
