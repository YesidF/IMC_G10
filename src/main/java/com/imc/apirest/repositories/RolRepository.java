package com.imc.apirest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.imc.apirest.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
