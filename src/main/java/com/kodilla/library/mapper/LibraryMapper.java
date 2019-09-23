package com.kodilla.library.mapper;

import com.kodilla.library.domain.*;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LibraryMapper {

    public Borrower mapToBorrower(final BorrowerDto borrowerDto) {
        return new Borrower(
                borrowerDto.getId(),
                borrowerDto.getFirstName(),
                borrowerDto.getLastName(),
                borrowerDto.getAccountCreationDate(),
                mapToBorrowingList(borrowerDto.getBorrowings())
        );
    }

    public BorrowerDto mapToBorrowerDto(final Borrower borrower) {
        return new BorrowerDto(
                borrower.getId(),
                borrower.getFirstName(),
                borrower.getLastName(),
                borrower.getAccountCreationDate(),
                mapToBorrowingDtoList(borrower.getBorrowings())
        );
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(
                title.getId(),
                title.getTitle(),
                title.getAuthor(),
                title.getYearOfPublication(),
                mapToExemplarDtoList(title.getExemplars())
        );
    }

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getId(),
                titleDto.getTitle(),
                titleDto.getAuthor(),
                titleDto.getYearOfPublication(),
                mapToExemplarList(titleDto.getExemplars())
        );
    }

    public ExemplarDto mapToExemplarDto(final Exemplar exemplar) {
        return new ExemplarDto(
                exemplar.getId(),
                exemplar.getTitle(),
                exemplar.getStatus(),
                mapToBorrowingDtoList(exemplar.getBorrowings())
        );
    }

    public BorrowingDto mapToBorrowingDto(final Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getExemplar(),
                borrowing.getBorrower(),
                borrowing.getBorrowDate(),
                borrowing.getReturnDate()
        );
    }

    private static <E, D> List<D> getConvertedList(Collection<E> entityList, Function<E, D> convertFunction) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(convertFunction)
                .collect(Collectors.toList());
    }

    public List<BorrowerDto> mapToBorrowerDtoList(final List<Borrower> borrowers) {
        return getConvertedList(borrowers, b -> new BorrowerDto(b.getId(), b.getFirstName(), b.getLastName(), b.getAccountCreationDate(), mapToBorrowingDtoList(b.getBorrowings())));
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titles) {
        return getConvertedList(titles, t -> new TitleDto(t.getId(), t.getTitle(), t.getAuthor(), t.getYearOfPublication(), mapToExemplarDtoList(t.getExemplars())));
    }

    public List<ExemplarDto> mapToExemplarDtoList(final List<Exemplar> exemplars) {
        return getConvertedList(exemplars, e -> new ExemplarDto(e.getId(), e.getTitle(), e.getStatus(), mapToBorrowingDtoList(e.getBorrowings())));
    }

    public List<Exemplar> mapToExemplarList(final List<ExemplarDto> exemplars) {
        return getConvertedList(exemplars, e -> new Exemplar(e.getId(), e.getTitle(), e.getStatus(), mapToBorrowingList(e.getBorrowings())));
    }

    public List<BorrowingDto> mapToBorrowingDtoList(final List<Borrowing> borrowings) {
        return getConvertedList(borrowings, b -> new BorrowingDto(b.getId(), b.getExemplar(), b.getBorrower(), b.getBorrowDate(), b.getReturnDate()));
    }

    public List<Borrowing> mapToBorrowingList(final List<BorrowingDto> borrowings) {
        return getConvertedList(borrowings, b -> new Borrowing(b.getId(), b.getExemplar(), b.getBorrower(), b.getBorrowDate(), b.getReturnDate()));
    }
}
