package com.bisa.model.mapper;

import com.bisa.model.dto.CuentaCreateRequestDto;
import com.bisa.model.dto.CuentaResponseDto;
import com.bisa.model.dto.TransaccionResponseDto;
import com.bisa.model.entity.CuentaEntity;
import com.bisa.model.entity.TransaccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CuentaMapper {

	@Mapping(source = "entity.id", target = "id")
	@Mapping(source = "entity.codCliente", target = "codCliente")
	@Mapping(source = "entity.numeroCuenta", target = "numeroCuenta")
	@Mapping(source = "entity.moneda", target = "moneda")
	@Mapping(source = "entity.saldo", target = "saldo")
	//@Mapping(source = "entity.status", target = "status")
	@Mapping(source = "entity.estadoCuenta", target = "estadoCuenta")
	@Mapping(source = "entity.creationDate", target = "creationDate")
	//@Mapping(source = "entity.updateDate", target = "updateDate")
	@Mapping(source = "entity.transacciones", target = "transacciones", qualifiedByName = "entityToResponseForTransacciones", ignore = true)
	CuentaResponseDto entityToResponse(CuentaEntity entity);

	@Mapping(source = "entity.id", target = "id")
	@Mapping(source = "entity.tipo", target = "tipo")
	@Mapping(source = "entity.numeroCuenta", target = "numeroCuenta")
	@Mapping(source = "entity.moneda", target = "moneda")
	@Mapping(source = "entity.monto", target = "monto")
	//@Mapping(source = "entity.cuentaId", target = "cuentaId")
	TransaccionResponseDto entityToResponse(TransaccionEntity entity);


	@Named("entityToResponseForTransacciones")
	default List<TransaccionResponseDto> entityToResponseForTransacciones(List<TransaccionEntity> items) {
		if(items==null) return null;
		return items.stream().map(p -> entityToResponse(p)).collect(Collectors.toList());
	}

	@Mapping(source = "request.codCliente", target = "codCliente")
	@Mapping(source = "request.moneda", target = "moneda")
	CuentaEntity requestToEntity(CuentaCreateRequestDto request);



}