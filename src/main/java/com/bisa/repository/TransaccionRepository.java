package com.bisa.repository;

import com.bisa.model.entity.CuentaEntity;
import com.bisa.model.entity.EstadoCuenta;
import com.bisa.model.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends CrudRepository<TransaccionEntity, Long> {

	@Query("from TransaccionEntity where status = com.bisa.model.entity.Status.CREATED")
	List<TransaccionEntity> findAll();
	@Query("from TransaccionEntity where status = com.bisa.model.entity.Status.CREATED and numeroCuenta = :numeroCuenta")
	List<TransaccionEntity> transaccionesPorNroCuenta(Long numeroCuenta);

}
