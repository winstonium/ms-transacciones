package com.bisa.repository;

import com.bisa.model.entity.CuentaEntity;
import com.bisa.model.entity.EstadoCuenta;
import com.bisa.model.entity.Status;
import com.bisa.model.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuentaRepository extends CrudRepository<CuentaEntity, Long> {

	@Query("from CuentaEntity where status = com.bisa.model.entity.Status.CREATED and ((:estadoCuenta is null) or (estadoCuenta = :estadoCuenta))")
	List<CuentaEntity> findAll(@Param("estadoCuenta") EstadoCuenta estadoCuenta);

	@Query("from CuentaEntity where status = com.bisa.model.entity.Status.CREATED and numeroCuenta=:numeroCuenta")
	CuentaEntity findByNumeroCuenta(@Param("numeroCuenta") Long numeroCuenta);

}
