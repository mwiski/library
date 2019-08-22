package com.kodilla.library.domain;

import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.TitleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookCopyTest {

    @Autowired
    BookCopyRepository bookCopyRepository;
    @Autowired
    TitleRepository titleRepository;

    @Test
    public void testUpdateBookCopyStatus() {
        //Given
        Title title = new Title("Pan Tadeusz", "Adam Mickiewicz", 1834);
        titleRepository.save(title);
        BookCopy bookCopy = new BookCopy("renewed");
        bookCopy.setTitleId(title);
        title.addBookCopy(bookCopy);
        bookCopyRepository.save(bookCopy);

        //When
        bookCopy.setStatus("test");
        bookCopyRepository.save(bookCopy);

        //Then
        assertThat(bookCopy.getStatus()).isEqualTo("test");

        //CleanUp
        try {
            bookCopyRepository.deleteById(bookCopy.getId());
        } catch (Exception e) {
            //    do nothing
        }
    }

    @Test
    public void testRetrieveAvailableBookCopies() {
        //Given
        Title title = new Title("Pan Tadeusz", "Adam Mickiewicz", 1834);
        titleRepository.save(title);
        BookCopy bookCopy = new BookCopy("available");
        bookCopy.setTitleId(title);
        title.addBookCopy(bookCopy);
        bookCopyRepository.save(bookCopy);

        //When
        List<BookCopy> availableCopies = bookCopyRepository.retrieveBookCopiesWithStatus("available", title);

        //Then
        assertThat(availableCopies.size()).isEqualTo(1);

        //CleanUp
        try {
            bookCopyRepository.deleteById(bookCopy.getId());
        } catch (Exception e) {
            //    do nothing
        }
    }
}