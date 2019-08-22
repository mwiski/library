package com.kodilla.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "borrowings")
@DynamicUpdate
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "book_copy_id")
    @JsonBackReference
    private BookCopy bookCopyId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "borrower_id")
    @JsonBackReference
    private Borrower borrowerId;

    @NotNull
    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public Borrowing(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
}
