package com.imc.apirest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imc.apirest.models.Imc;

@Repository
public interface ImcRepository extends JpaRepository<Imc, Long>{
	
}
