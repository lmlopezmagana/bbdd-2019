package org.paginationdemo.service;

import org.paginationdemo.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    /**
     * Encuenta una "página" de personas
     *
     * @param pageable
     * @return {@link Page} instance
     */
    Page<Person> findAllPageable(Pageable pageable);
}
