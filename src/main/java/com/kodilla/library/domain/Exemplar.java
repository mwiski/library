package com.kodilla.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "exemplars")
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "title_id")
    private Title title;

    @Column(name = "status")
    private Status status;

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "exemplar",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Borrowing> borrowings = new ArrayList<>();

    public Exemplar(Status status) {
        this.status = status;
    }

    public void addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
    }
}
