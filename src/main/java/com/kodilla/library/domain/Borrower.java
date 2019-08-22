package com.kodilla.library.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity()
@Table(name = "borrowers")
@DynamicUpdate
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    @Column(name = "account_creation_date")
    private LocalDate accountCreationDate;

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "borrowerId",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Borrowing> borrowings = new ArrayList<>();

    public Borrower(String firstName, String lastName, LocalDate accountCreationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountCreationDate = accountCreationDate;
    }

    public void addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
    }
}
