package com.kodilla.library.service;

import com.kodilla.library.controller.CannotPerformActionException;
import com.kodilla.library.domain.*;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.repository.ExemplarRepository;
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
    private ExemplarRepository exemplarRepository;

    public BorrowerDto addBorrower(final BorrowerDto borrowerDto) {
        return libraryMapper.mapToBorrowerDto(borrowerRepository.save(libraryMapper.mapToBorrower(borrowerDto)));
    }

    public TitleDto addTitle(final TitleDto titleDto) throws CannotPerformActionException {
        if (titleRepository.findAll().stream()
                .anyMatch(t -> t.getTitle().equals(titleDto.getTitle()) && t.getAuthor().equals(titleDto.getAuthor()) && t.getYearOfPublication() == titleDto.getYearOfPublication()))
        {
            throw new CannotPerformActionException("This title is already in database");
        }
        return libraryMapper.mapToTitleDto(titleRepository.save(libraryMapper.mapToTitle(titleDto)));
    }

    public ExemplarDto addExemplar(final Long titleId, final Status status) throws CannotPerformActionException {
        Exemplar exemplar = new Exemplar(status);
        exemplar.setTitle(titleRepository.findById(titleId).orElseThrow(() -> new CannotPerformActionException("Cannot find this title in database")));
        exemplar.getTitle().addExemplar(exemplar);
        return libraryMapper.mapToExemplarDto(exemplarRepository.save(exemplar));
    }

    public ExemplarDto updateExemplarStatus(final Long id, final Status status) throws CannotPerformActionException {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new CannotPerformActionException("Cannot find this exemplar in database"));
        exemplar.setStatus(status);
        return libraryMapper.mapToExemplarDto(exemplarRepository.save(exemplar));
    }

    public long getAvailableExemplars(final Long titleId) throws CannotPerformActionException {
        Title title = titleRepository.findById(titleId).orElseThrow(() -> new CannotPerformActionException("Cannot find this title in database"));
        return exemplarRepository.countAllByStatusAndTitle(Status.AVAILABLE, title);
    }

    public BorrowingDto borrowBook(final Long exemplarId, final Long borrowerId, final String borrowDate) throws CannotPerformActionException {
        Exemplar exemplar = exemplarRepository.findById(exemplarId).orElseThrow(() -> new CannotPerformActionException("Cannot find this exemplar in database"));
        Borrower borrower = borrowerRepository.findById(borrowerId).orElseThrow(() -> new CannotPerformActionException("Cannot find this borrower in database"));
        if (exemplar.getStatus().equals(Status.AVAILABLE)) {
            LocalDate localDate = LocalDate.parse(borrowDate);
            Borrowing borrowing = new Borrowing(localDate);
            borrowing.setExemplar(exemplar);
            borrowing.setBorrower(borrower);
            exemplar.addBorrowing(borrowing);
            borrower.addBorrowing(borrowing);
            exemplar.setStatus(Status.BORROWED);
            return libraryMapper.mapToBorrowingDto(borrowingRepository.save(borrowing));
        } else {
            throw new CannotPerformActionException("This exemplar is not available to be borrowed");
        }
    }

    public BorrowingDto returnBook(final Long exemplarId, final String returnDate) throws CannotPerformActionException {
        Exemplar exemplar = exemplarRepository.findById(exemplarId).orElseThrow(() -> new CannotPerformActionException("Cannot find this exemplar in database"));
        LocalDate localDate = LocalDate.parse(returnDate);
        exemplar.setStatus(Status.AVAILABLE);
        Borrowing borrowing = Optional.ofNullable(exemplar.getBorrowings()
                .get(exemplar.getBorrowings().size() - 1))
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

    public List<ExemplarDto> getExemplars() {
        return libraryMapper.mapToExemplarDtoList(exemplarRepository.findAll());
    }

    public List<BorrowingDto> getBorrowings() {
        return libraryMapper.mapToBorrowingDtoList(borrowingRepository.findAll());
    }
}
