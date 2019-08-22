package com.kodilla.library.domain;

import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BorrowerRepository;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.TitleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowingTest {

    @Autowired
    BorrowingRepository borrowingRepository;
    @Autowired
    BorrowerRepository borrowerRepository;
    @Autowired
    TitleRepository titleRepository;
    @Autowired
    BookCopyRepository bookCopyRepository;

    @Test
    public void testBorrowingSave() {
        //Given
        Borrower johnSmith = new Borrower("John", "Smith", LocalDate.of(2019, 1,29));
        Title title = new Title("Pan Tadeusz", "Adam Mickiewicz", 1834);
        BookCopy bookCopy = new BookCopy("renewed");
        Borrowing borrowing = new Borrowing(LocalDate.of(2019, 8, 10));
        titleRepository.save(title);
        borrowerRepository.save(johnSmith);

        bookCopy.setTitleId(title);
        borrowing.setBookCopyId(bookCopy);
        borrowing.setBorrowerId(johnSmith);

        title.addBookCopy(bookCopy);
        johnSmith.addBorrowing(borrowing);
        bookCopy.addBorrowing(borrowing);

        //When
        bookCopyRepository.save(bookCopy);
        borrowingRepository.save(borrowing);

        //Then
        assertThat(johnSmith.getId()).isGreaterThan(0);
        assertThat(title.getId()).isGreaterThan(0);
        assertThat(bookCopy.getId()).isGreaterThan(0);
        assertThat(borrowing.getId()).isGreaterThan(0);

        //CleanUp
        try {
            borrowingRepository.deleteById(borrowing.getId());
            bookCopyRepository.deleteById(bookCopy.getId());
            titleRepository.deleteById(title.getId());
            borrowerRepository.deleteById(johnSmith.getId());
        } catch (Exception e) {
            //    do nothing
        }
    }

}