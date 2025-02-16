package com.assignment.test.repository;

import com.assignment.test.dto.AccountBLDTO;
import com.assignment.test.dto.pkdto.AccountBlKeyDTO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBLRepository extends ReactiveCrudRepository<AccountBLDTO, AccountBlKeyDTO> {
}
