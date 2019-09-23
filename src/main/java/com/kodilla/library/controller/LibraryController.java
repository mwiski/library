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

    @RequestMapping(method = RequestMethod.POST, value = "borrower", consumes = APPLICATION_JSON_VALUE)
    public BorrowerDto addBorrower(@RequestBody BorrowerDto borrowerDto) throws CannotPerformActionException {
        return service.addBorrower(borrowerDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "book/title", consumes = APPLICATION_JSON_VALUE)
    public TitleDto addTitle(@RequestBody TitleDto titleDto) throws CannotPerformActionException {
        return service.addTitle(titleDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "book/exemplar", consumes = APPLICATION_JSON_VALUE)
    public ExemplarDto addExemplar(@RequestParam Long titleId, @RequestParam Status status) throws CannotPerformActionException {
        return service.addExemplar(titleId, status);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "book/exemplar/status")
    public ExemplarDto updateExemplarStatus(@RequestParam Long id, @RequestParam Status status) throws CannotPerformActionException {
        return service.updateExemplarStatus(id, status);
    }

    @RequestMapping(method = RequestMethod.GET, value = "book/exemplars")
    public long getAvailableExemplars(@RequestParam Long titleId) throws CannotPerformActionException {
        return service.getAvailableExemplars(titleId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "book/borrow")
    public BorrowingDto borrowBook(@RequestParam Long exemplarId, @RequestParam Long borrowerId, @RequestParam String borrowDate) throws CannotPerformActionException {
        return service.borrowBook(exemplarId, borrowerId, borrowDate);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "book/return")
    public BorrowingDto returnBook(@RequestParam Long exemplarId, @RequestParam String returnDate) throws CannotPerformActionException {
        return service.returnBook(exemplarId, returnDate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "borrowers")
    public List<BorrowerDto> getBorrowers() {
        return service.getBorrowers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "book/titles")
    public List<TitleDto> getTitles() {
        return service.getTitles();
    }

    @RequestMapping(method = RequestMethod.GET, value = "books/exemplars")
    public List<ExemplarDto> getExemplars() {
        return service.getExemplars();
    }

    @RequestMapping(method = RequestMethod.GET, value = "book/borrowings")
    public List<BorrowingDto> getBorrowings() {
        return service.getBorrowings();
    }
}
