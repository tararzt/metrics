package com.rzt.repository;

import org.springframework.data.repository.CrudRepository;
import com.rzt.schemapojo.Email;

public interface EmailRepo extends CrudRepository<Email, Integer> {

}
