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
    private BookCopy bookCopyId;
    private Borrower borrowerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
