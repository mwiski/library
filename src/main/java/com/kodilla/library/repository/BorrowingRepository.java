package com.kodilla.library.repository;

import com.kodilla.library.domain.Borrowing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {
    @Override
    List<Borrowing> findAll();
}
