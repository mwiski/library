package com.kodilla.library.service;

import com.kodilla.library.controller.CannotPerformActionException;
import com.kodilla.library.domain.*;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BorrowerRepository;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DbService {

    @Autowired
    private LibraryMapper libraryMapper;
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    public BorrowerDto saveBorrower(final BorrowerDto borrowerDto) {
        return libraryMapper.mapToBorrowerDto(borrowerRepository.save(libraryMapper.mapToBorrower(borrowerDto)));
    }

    public TitleDto saveTitle(final TitleDto titleDto) throws CannotPerformActionException {
        Title title = libraryMapper.mapToTitle(titleDto);
        if (titleRepository.findAll().stream().anyMatch(t -> t.equals(title))) {
            throw new CannotPerformActionException("This title is already in database");
        }
        return libraryMapper.mapToTitleDto(titleRepository.save(libraryMapper.mapToTitle(titleDto)));
    }

    public BookCopyDto saveBookCopy(final Long titleId, final String status) throws CannotPerformActionException {
        BookCopy bookCopy = new BookCopy(status);
        bookCopy.setTitleId(titleRepository.findById(titleId).orElseThrow(() -> new CannotPerformActionException("Cannot find this title in database")));
        bookCopy.getTitleId().addBookCopy(bookCopy);
        return libraryMapper.mapToBookCopyDto(bookCopyRepository.save(bookCopy));
    }

    public BookCopyDto updateBookCopyStatus(final Long id, final String status) throws CannotPerformActionException {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new CannotPerformActionException("Cannot find this book copy in database"));
        bookCopy.setStatus(status);
        return libraryMapper.mapToBookCopyDto(bookCopyRepository.save(bookCopy));
    }

    public int getAvailableBookCopiesNumber(final Long titleId) throws CannotPerformActionException {
        Title title = titleRepository.findById(titleId).orElseThrow(() -> new CannotPerformActionException("Cannot find this title in database"));
        return bookCopyRepository.retrieveBookCopiesWithStatus("available", title).size();
    }

    public BorrowingDto borrowBook(final Long bookCopyId, final Long borrowerId, final String borrowDate) throws CannotPerformActionException {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId).orElseThrow(() -> new CannotPerformActionException("Cannot find this book copy in database"));
        Borrower borrower = borrowerRepository.findById(borrowerId).orElseThrow(() -> new CannotPerformActionException("Cannot find this borrower in database"));
        if (bookCopy.getStatus().equals("available")) {
            LocalDate localDate = LocalDate.parse(borrowDate);
            Borrowing borrowing = new Borrowing(localDate);
            borrowing.setBookCopyId(bookCopy);
            borrowing.setBorrowerId(borrower);
            bookCopy.addBorrowing(borrowing);
            borrower.addBorrowing(borrowing);
            bookCopy.setStatus("borrowed");
            return libraryMapper.mapToBorrowingDto(borrowingRepository.save(borrowing));
        } else {
            throw new CannotPerformActionException("This book copy is not available to be borrowed");
        }
    }

    public BorrowingDto returnBook(final Long bookCopyId, final String returnDate) throws CannotPerformActionException {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId).orElseThrow(() -> new CannotPerformActionException("Cannot find this book copy in database"));
        LocalDate localDate = LocalDate.parse(returnDate);
        bookCopy.setStatus("available");
        Borrowing borrowing = Optional.ofNullable(bookCopy.getBorrowings()
                .get(bookCopy.getBorrowings().size() - 1))
                .orElseThrow(() -> new CannotPerformActionException("Cannot find this book borrowing"));
        borrowing.setReturnDate(localDate);
        return libraryMapper.mapToBorrowingDto(borrowingRepository.save(borrowing));
    }

    public List<BorrowerDto> getBorrowers() {
        return libraryMapper.mapToBorrowerDtoList(borrowerRepository.findAll());
    }

    public List<TitleDto> getTitles() {
        return libraryMapper.mapToTitleDtoList(titleRepository.findAll());
    }

    public List<BookCopyDto> getBooksCopies() {
        return libraryMapper.mapToBookCopyDtoList(bookCopyRepository.findAll());
    }

    public List<BorrowingDto> getBorrowings() {
        return libraryMapper.mapToBorrowingDtoList(borrowingRepository.findAll());
    }
}
