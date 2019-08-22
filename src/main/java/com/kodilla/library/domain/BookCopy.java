package com.kodilla.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(
        name = "BookCopy.retrieveBookCopiesWithStatus",
        query = "FROM BookCopy WHERE status LIKE :available AND title_id = :title"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "books_copies")
@DynamicUpdate
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "title_id")
    @JsonBackReference
    private Title titleId;

    @Column(name = "status")
    private String status;

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "bookCopyId",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Borrowing> borrowings = new ArrayList<>();

    public BookCopy(String status) {
        this.status = status;
    }

    public void addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
    }
}
