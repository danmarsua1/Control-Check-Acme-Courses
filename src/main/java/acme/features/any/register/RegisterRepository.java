/*
 * RegisterRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.register;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Register;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface RegisterRepository extends AbstractRepository {

	@Query("select r from Register r where r.id = :id")
	Register findOneRegisterById(int id);
	
	@Query("select r from Register r")
	Collection<Register> findAllRegisters();
	
	@Query("select r from Register r where r.theoryTutorial.id = :id")
	Register findOneRegisterByTheoryTutorial(int id);
	
	@Query("select r from Register r where r.labTutorial.id = :id")
	Register findOneRegisterByLabTutorial(int id);
	
}
