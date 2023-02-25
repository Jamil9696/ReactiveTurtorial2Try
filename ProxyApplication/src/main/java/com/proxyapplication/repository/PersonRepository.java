package com.proxyapplication.repository;


import com.proxyapplication.entities.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/* Usually we have CRUD Repository which is not extending from the CrudRepository but
 directly from the repository itself. It's basically parallel to the CrudRepository and a
 therefore complete different contract
*/

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
}
