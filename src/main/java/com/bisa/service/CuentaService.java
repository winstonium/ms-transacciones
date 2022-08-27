package com.bisa.service;

import com.bisa.model.dto.CuentaCreateRequestDto;
import com.bisa.model.dto.CuentaResponseDto;
import com.bisa.model.dto.CuentaUpdateRequestDto;
import com.bisa.model.dto.TransaccionOperacionRequestDto;
import com.bisa.model.entity.EstadoCuenta;
import com.bisa.model.entity.TipoTransaccion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CuentaService {
	@Transactional(readOnly = true)
	public List<CuentaResponseDto> findAll(EstadoCuenta estadoCuenta);

	//@Transactional(readOnly = true)
	public CuentaResponseDto findById(Long id);

	//@Transactional(readOnly = true)
	//public List<CuentaResponseDto> findByIdCategory(Long id, int port);

	public CuentaResponseDto create(CuentaCreateRequestDto cuentaRequest);

	//@Transactional
	public CuentaResponseDto update(Long id, CuentaUpdateRequestDto cuentaRequest);

	//@Transactional
	public CuentaResponseDto actualizarSaldo(Long nroCuenta, TransaccionOperacionRequestDto cuentaRequest, TipoTransaccion tipoTransaccion);

	public CuentaResponseDto buscarPorNumeroCuenta(Long numeroCuenta);
	//@Transactional
	public void delete(Long id);







}
