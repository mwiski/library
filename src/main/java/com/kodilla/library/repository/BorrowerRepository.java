package com.kodilla.library.repository;

import com.kodilla.library.domain.Borrower;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BorrowerRepository extends CrudRepository<Borrower, Long> {
    @Override
    List<Borrower> findAll();
}
