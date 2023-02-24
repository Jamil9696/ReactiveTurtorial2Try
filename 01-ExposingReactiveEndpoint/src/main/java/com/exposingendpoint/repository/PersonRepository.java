package com.exposingendpoint.repository;

import com.exposingendpoint.entities.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/* Usually we have CRUD Repository which is not extending from the CrudRepository but
 directly from the repository itself. It's basically parallel to the CrudRepository and a
 therefore complete different contract
*/

public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
}
