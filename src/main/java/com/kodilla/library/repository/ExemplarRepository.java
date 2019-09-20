package com.kodilla.library.repository;

import com.kodilla.library.domain.Exemplar;
import com.kodilla.library.domain.Status;
import com.kodilla.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ExemplarRepository extends CrudRepository<Exemplar, Long> {

    @Override
    List<Exemplar> findAll();

    long countAllByStatusAndTitle(Status status, Title title);
}
