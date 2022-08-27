package com.bisa.service;

import com.bisa.model.dto.*;
import com.bisa.model.entity.EstadoCuenta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TransaccionService {
	public TransaccionResponseDto create(TransaccionCreateRequestDto transaccionRequest);
	@Transactional(readOnly = true)
	public List<TransaccionResponseDto> findAll();

	public List<TransaccionResponseDto> transaccionesPorNroCuenta(Long numeroCuenta);
}
