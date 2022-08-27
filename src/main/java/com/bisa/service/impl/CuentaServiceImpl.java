package com.bisa.service.impl;

import com.bisa.configuration.error.ResourceBadRequestException;
import com.bisa.configuration.error.ResourceNotFoundException;
import com.bisa.model.dto.*;
import com.bisa.model.entity.*;
import com.bisa.model.mapper.CuentaMapper;
import com.bisa.repository.CuentaRepository;
import com.bisa.service.CuentaService;
import com.bisa.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {
	
	private final CuentaRepository repository;
	@Autowired
	private TransaccionService transaccionService;
	
	private final CuentaMapper mapper;
	
	@Transactional(readOnly = true)
	public List<CuentaResponseDto> findAll(EstadoCuenta estadoCuenta) {
		log.info("findAll");
		List<CuentaEntity> list = (List<CuentaEntity>)repository.findAll(estadoCuenta);
		log.info("finded");
		return list.stream().map(p -> mapper.entityToResponse(p)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public CuentaResponseDto findById(Long id) {
		log.info("findById");
		return repository.findById(id).
				filter(p -> p.getStatus() == Status.CREATED)
				.map(p -> mapper.entityToResponse(p)).orElseThrow(() -> new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND));
	}
	

	
	public CuentaResponseDto create(CuentaCreateRequestDto cuentaRequest) {
		log.info("create");
		CuentaEntity cuentaEntity = mapper.requestToEntity(cuentaRequest);
		cuentaEntity.setNumeroCuenta(cuentaEntity.getCodCliente()*1000+(long)(Math.random()*1000));
		repository.save(cuentaEntity);
		TransaccionCreateRequestDto trans= new TransaccionCreateRequestDto();
		cuentaEntity.setTransacciones(new ArrayList<TransaccionEntity>());
		cuentaEntity.getTransacciones().add(TransaccionEntity.builder()
				.numeroCuenta(cuentaEntity.getNumeroCuenta())
				.moneda(cuentaEntity.getMoneda())
				.monto(BigDecimal.ZERO)
				.descripcion("Balance inicial")
				.tipo(TipoTransaccion.BAL_INICIAL)
				.build());

		log.info("saved");
		return mapper.entityToResponse(cuentaEntity);
	}
	
	@Transactional
	public CuentaResponseDto update(Long id, CuentaUpdateRequestDto productRequest) {
		log.info("update");
		CuentaEntity cuentaEntity = getCuentaById(id);
		BeanUtils.copyProperties(productRequest, cuentaEntity);
		//cuentaEntity.setTransacciones(TransaccionEntity.builder().id(productRequest.getTransaccionId()).build());
		repository.save(cuentaEntity);
		log.info("updated");
		return mapper.entityToResponse(cuentaEntity);
	}
	
	@Transactional
	public CuentaResponseDto actualizarSaldo(Long nroCuenta, TransaccionOperacionRequestDto cuentaRequest, TipoTransaccion tipoTransaccion){
		log.info("updateStock");
		CuentaEntity cuentaEntity = getCuentaByNumeroCuenta(nroCuenta);
		BigDecimal monto = cuentaRequest.getMonto();
		if(tipoTransaccion.equals(TipoTransaccion.DEBITO)){
			monto=monto.multiply(BigDecimal.ONE.negate());
		}

		if(!cuentaRequest.getMoneda().equals(cuentaEntity.getMoneda())){
			throw new ResourceBadRequestException("Solo se permiten retiros y/o abonos en la misma moneda de la cuenta.", HttpStatus.BAD_REQUEST);
		}

		cuentaEntity.setSaldo(cuentaEntity.getSaldo().add(monto));
		if(tipoTransaccion.equals(TipoTransaccion.DEBITO)){
			if(cuentaEntity.getSaldo().signum()==-1 && cuentaEntity.getExcedido()<=0){
				cuentaEntity.setExcedido(cuentaEntity.getExcedido()+1);
				cuentaEntity.setEstadoCuenta(EstadoCuenta.HOLD);
			}else if(cuentaEntity.getEstadoCuenta().equals(EstadoCuenta.HOLD)) {
				throw new ResourceBadRequestException("Cuenta en estado HOLD, no se puede retirar monto.", HttpStatus.BAD_REQUEST);
			}else if(cuentaEntity.getSaldo().signum()==-1 && cuentaEntity.getEstadoCuenta().equals(EstadoCuenta.ACTIVE) && cuentaEntity.getExcedido()>0){
				throw new ResourceBadRequestException("No se permite retiro de este monto, por saldo insuficiente.", HttpStatus.BAD_REQUEST);
			}
			cuentaEntity.getTransacciones().add(TransaccionEntity.builder()
					.numeroCuenta(cuentaEntity.getNumeroCuenta())
					.moneda(cuentaEntity.getMoneda())
					.monto(cuentaRequest.getMonto())
					.descripcion(cuentaRequest.getDescripcion())
					.tipo(TipoTransaccion.DEBITO)
					.build());

		}else{
			if((cuentaEntity.getSaldo().signum()==1 || cuentaEntity.getSaldo().signum()==0) && cuentaEntity.getEstadoCuenta().equals(EstadoCuenta.HOLD)){
				cuentaEntity.setEstadoCuenta(EstadoCuenta.ACTIVE);
			}
			cuentaEntity.getTransacciones().add(TransaccionEntity.builder()
					.numeroCuenta(cuentaEntity.getNumeroCuenta())
					.moneda(cuentaEntity.getMoneda())
					.monto(cuentaRequest.getMonto())
					.descripcion(cuentaRequest.getDescripcion())
					.tipo(TipoTransaccion.CREDITO)
					.build());
		}

		repository.save(cuentaEntity);
		log.info("updated");
		return mapper.entityToResponse(cuentaEntity);
	}

	
	@Transactional
	public void delete(Long id) {
		log.info("delete");
		CuentaEntity productEntity = getCuentaById(id);
		productEntity.setStatus(Status.DELETED);
		repository.save(productEntity);
		log.info("deleted");
	}

	@Override
	public CuentaResponseDto buscarPorNumeroCuenta(Long numeroCuenta) {
		CuentaEntity productEntityOp = getCuentaByNumeroCuenta(numeroCuenta);
		return mapper.entityToResponse(productEntityOp);
	}

	private CuentaEntity getCuentaById(Long id) {
		Optional<CuentaEntity> productEntityOp = repository.findById(id).
				filter(p -> p.getStatus() == Status.CREATED);
		if(!productEntityOp.isPresent()) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}
		return productEntityOp.get();
	}

	private CuentaEntity getCuentaByNumeroCuenta(Long numeroCuenta) {
		CuentaEntity productEntityOp = repository.findByNumeroCuenta(numeroCuenta);
		if(productEntityOp==null) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}
		return productEntityOp;
	}


}
