package com.wizeline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.wizeline.model.BankAccountDTO;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long>{

}
