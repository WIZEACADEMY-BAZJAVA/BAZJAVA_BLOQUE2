package com.wizeline.maven.LearningJava.repository;

import com.wizeline.maven.LearningJava.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}