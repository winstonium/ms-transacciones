package com.bisa.service.impl;

import com.bisa.configuration.error.ResourceNotFoundException;
import com.bisa.model.dto.*;
import com.bisa.model.entity.CuentaEntity;
import com.bisa.model.entity.EstadoCuenta;
import com.bisa.model.entity.Status;
import com.bisa.model.entity.TransaccionEntity;
import com.bisa.model.mapper.CuentaMapper;
import com.bisa.model.mapper.TransaccionMapper;
import com.bisa.repository.CuentaRepository;
import com.bisa.repository.TransaccionRepository;
import com.bisa.service.CuentaService;
import com.bisa.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {
	
	private final TransaccionRepository repository;
	
	private final TransaccionMapper mapper;
	
	public TransaccionResponseDto create(TransaccionCreateRequestDto transaccionRequest) {
		log.info("create");
		TransaccionEntity transaccionEntity = mapper.requestToEntity(transaccionRequest);
		repository.save(transaccionEntity);
		log.info("saved");
		return mapper.entityToResponse(transaccionEntity);
	}

	@Transactional(readOnly = true)
	public List<TransaccionResponseDto> findAll() {
		log.info("findAll");
		List<TransaccionEntity> list = (List<TransaccionEntity>)repository.findAll();
		log.info("finded");
		return list.stream().map(p -> mapper.entityToResponse(p)).collect(Collectors.toList());
	}

	private TransaccionEntity getTransaccionById(Long id) {
		Optional<TransaccionEntity> transaccionEntityOp = repository.findById(id).
				filter(p -> p.getStatus() == Status.CREATED);
		if(!transaccionEntityOp.isPresent()) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}
		return transaccionEntityOp.get();
	}

	@Override
	public List<TransaccionResponseDto> transaccionesPorNroCuenta(Long numeroCuenta) {
		List<TransaccionEntity> transaccionEntityOp = repository.transaccionesPorNroCuenta(numeroCuenta);
		if(transaccionEntityOp==null || transaccionEntityOp.isEmpty()) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}
		return transaccionEntityOp.stream().map(p -> mapper.entityToResponse(p)).collect(Collectors.toList());
	}
}
