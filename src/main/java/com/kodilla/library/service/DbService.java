package com.kodilla.library.service;

import com.kodilla.library.controller.CannotPerformActionException;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Borrower;
import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.Title;
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
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    public Borrower saveBorrower(final Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public BookCopy saveBookCopy(final Long titleId, final String status) throws CannotPerformActionException {
        BookCopy bookCopy = new BookCopy(status);
        bookCopy.setTitleId(titleRepository.findById(titleId).orElseThrow(() -> new CannotPerformActionException("Cannot find this title in database")));
        bookCopy.getTitleId().addBookCopy(bookCopy);
        return bookCopyRepository.save(bookCopy);
    }

    public BookCopy updateBookCopyStatus(final Long id, final String status) throws CannotPerformActionException {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new CannotPerformActionException("Cannot find this book copy in database"));
        bookCopy.setStatus(status);
        return bookCopyRepository.save(bookCopy);
    }

    public int getAvailableBookCopiesNumber(final Long titleId) throws CannotPerformActionException {
        Title title = titleRepository.findById(titleId).orElseThrow(() -> new CannotPerformActionException("Cannot find this title in database"));
        return bookCopyRepository.retrieveBookCopiesWithStatus("available", title).size();
    }

    public Borrowing borrowBook(final Long bookCopyId, final Long borrowerId, final String borrowDate) throws CannotPerformActionException {
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
            return borrowingRepository.save(borrowing);
        } else {
            throw new CannotPerformActionException("This book copy is not available to be borrowed");
        }
    }

    public Borrowing returnBook(final Long bookCopyId, final String returnDate) throws CannotPerformActionException {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId).orElseThrow(() -> new CannotPerformActionException("Cannot find this book copy in database"));
        LocalDate localDate = LocalDate.parse(returnDate);
        bookCopy.setStatus("available");
        Borrowing borrowing = Optional.ofNullable(bookCopy.getBorrowings()
                .get(bookCopy.getBorrowings().size() - 1))
                .orElseThrow(() -> new CannotPerformActionException("Cannot find this book borrowing"));
        borrowing.setReturnDate(localDate);
        return borrowingRepository.save(borrowing);
    }

    public List<Borrower> getBorrowers() {
        return borrowerRepository.findAll();
    }

    public List<Title> getTitles() {
        return titleRepository.findAll();
    }

    public List<BookCopy> getBooksCopies() {
        return bookCopyRepository.findAll();
    }

    public List<Borrowing> getBorrowings() {
        return borrowingRepository.findAll();
    }
}
