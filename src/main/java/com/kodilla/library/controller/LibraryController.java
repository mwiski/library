package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(LibraryController.BASE_API)
public class LibraryController {

    static final String BASE_API = "/v1/library";

    @Autowired
    private DbService service;

    @RequestMapping(method = RequestMethod.POST, value = "borrower/add", consumes = APPLICATION_JSON_VALUE)
    public BorrowerDto addBorrower(@RequestBody BorrowerDto borrowerDto) throws CannotPerformActionException {
        return service.saveBorrower(borrowerDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "title/add", consumes = APPLICATION_JSON_VALUE)
    public TitleDto addTitle(@RequestBody TitleDto titleDto) throws CannotPerformActionException {
        return service.saveTitle(titleDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "book/copy/add", consumes = APPLICATION_JSON_VALUE)
    public BookCopyDto addBookCopy(@RequestParam Long titleId, @RequestParam String status) throws CannotPerformActionException {
        return service.saveBookCopy(titleId, status);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "status/change")
    public BookCopyDto changeBookCopyStatus(@RequestParam Long id, @RequestParam String status) throws CannotPerformActionException {
        return service.updateBookCopyStatus(id, status);
    }

    @RequestMapping(method = RequestMethod.GET, value = "copies/available")
    public int getAvailableBookCopiesNumber(@RequestParam Long titleId) throws CannotPerformActionException {
        return service.getAvailableBookCopiesNumber(titleId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "book/borrow")
    public BorrowingDto borrowBook(@RequestParam Long bookCopyId, @RequestParam Long borrowerId, @RequestParam String borrowDate) throws CannotPerformActionException {
        return service.borrowBook(bookCopyId, borrowerId, borrowDate);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "book/return")
    public BorrowingDto returnBook(@RequestParam Long bookCopyId, @RequestParam String returnDate) throws CannotPerformActionException {
        return service.returnBook(bookCopyId, returnDate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "borrowers")
    public List<BorrowerDto> getBorrowers() {
        return service.getBorrowers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "titles")
    public List<TitleDto> getTitles() {
        return service.getTitles();
    }

    @RequestMapping(method = RequestMethod.GET, value = "books/copies")
    public List<BookCopyDto> getBooksCopies() {
        return service.getBooksCopies();
    }

    @RequestMapping(method = RequestMethod.GET, value = "borrowings")
    public List<BorrowingDto> getBorrowings() {
        return service.getBorrowings();
    }
}
