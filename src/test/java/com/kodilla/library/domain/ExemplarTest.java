package com.kodilla.library.domain;

import com.kodilla.library.repository.ExemplarRepository;
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
public class ExemplarTest {

    @Autowired
    ExemplarRepository exemplarRepository;
    @Autowired
    TitleRepository titleRepository;

    @Test
    public void testUpdateExemplarStatus() {
        //Given
        Title title = new Title("Pan Tadeusz", "Adam Mickiewicz", 1834);
        titleRepository.save(title);
        Exemplar exemplar = new Exemplar(Status.DAMAGED);
        exemplar.setTitle(title);
        title.addExemplar(exemplar);
        exemplarRepository.save(exemplar);

        //When
        exemplar.setStatus(Status.BORROWED);
        exemplarRepository.save(exemplar);

        //Then
        assertThat(exemplar.getStatus()).isEqualTo(Status.BORROWED);

        //CleanUp
        try {
            exemplarRepository.deleteById(exemplar.getId());
        } catch (Exception e) {
            //    do nothing
        }
    }

    @Test
    public void testRetrieveAvailableExemplars() {
        //Given
        Title title = new Title("Pan Tadeusz", "Adam Mickiewicz", 1834);
        titleRepository.save(title);
        Exemplar exemplar = new Exemplar(Status.AVAILABLE);
        exemplar.setTitle(title);
        title.addExemplar(exemplar);
        exemplarRepository.save(exemplar);

        //When
        Long availableExemplarsNumber = exemplarRepository.countAllByStatusAndTitle(Status.AVAILABLE, title);

        //Then
        assertThat(availableExemplarsNumber).isEqualTo(1);

        //CleanUp
        try {
            exemplarRepository.deleteById(exemplar.getId());
        } catch (Exception e) {
            //    do nothing
        }
    }
}