package com.kodilla.library.mapper;

import com.kodilla.library.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryMapper {

    public Borrower mapToBorrower(final BorrowerDto borrowerDto) {
        return new Borrower(
                borrowerDto.getId(),
                borrowerDto.getFirstName(),
                borrowerDto.getLastName(),
                borrowerDto.getAccountCreationDate(),
                borrowerDto.getBorrowings()
        );
    }

    public BorrowerDto mapToBorrowerDto(final Borrower borrower) {
        return new BorrowerDto(
                borrower.getId(),
                borrower.getFirstName(),
                borrower.getLastName(),
                borrower.getAccountCreationDate(),
                borrower.getBorrowings()
        );
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(
                title.getId(),
                title.getTitle(),
                title.getAuthor(),
                title.getYearOfPublication(),
                title.getCopies()
        );
    }

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getId(),
                titleDto.getTitle(),
                titleDto.getAuthor(),
                titleDto.getYearOfPublication(),
                titleDto.getCopies()
        );
    }

    public BookCopyDto mapToBookCopyDto(final BookCopy bookCopy) {
        return new BookCopyDto(
                bookCopy.getId(),
                bookCopy.getTitleId(),
                bookCopy.getStatus(),
                bookCopy.getBorrowings()
        );
    }

    public BorrowingDto mapToBorrowingDto(final Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getBookCopyId(),
                borrowing.getBorrowerId(),
                borrowing.getBorrowDate(),
                borrowing.getReturnDate()
        );
    }

    public List<BorrowerDto> mapToBorrowerDtoList(final List<Borrower> borrowers) {
        return borrowers.stream()
                .map(b -> new BorrowerDto(b.getId(), b.getFirstName(), b.getLastName(), b.getAccountCreationDate(), b.getBorrowings()))
                .collect(Collectors.toList());
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titles) {
        return titles.stream()
                .map(t -> new TitleDto(t.getId(), t.getTitle(), t.getAuthor(), t.getYearOfPublication(), t.getCopies()))
                .collect(Collectors.toList());
    }

    public List<BookCopyDto> mapToBookCopyDtoList(final List<BookCopy> copies) {
        return copies.stream()
                .map(b -> new BookCopyDto(b.getId(), b.getTitleId(), b.getStatus(), b.getBorrowings()))
                .collect(Collectors.toList());
    }

    public List<BorrowingDto> mapToBorrowingDtoList(final List<Borrowing> borrowings) {
        return borrowings.stream()
                .map(b -> new BorrowingDto(b.getId(), b.getBookCopyId(), b.getBorrowerId(), b.getBorrowDate(), b.getReturnDate()))
                .collect(Collectors.toList());
    }
}
