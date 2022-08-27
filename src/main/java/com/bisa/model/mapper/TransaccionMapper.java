package com.bisa.model.mapper;

import com.bisa.model.dto.TransaccionCreateRequestDto;
import com.bisa.model.dto.TransaccionResponseDto;
import com.bisa.model.entity.TransaccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransaccionMapper {

	@Mapping(source = "entity.id", target = "id")
	@Mapping(source = "entity.numeroCuenta", target = "numeroCuenta")
	@Mapping(source = "entity.tipo", target = "tipo")
	@Mapping(source = "entity.moneda", target = "moneda")
	@Mapping(source = "entity.monto", target = "monto")
	@Mapping(source = "entity.descripcion", target = "descripcion")
	@Mapping(source = "entity.status", target = "status")
	@Mapping(source = "entity.creationDate", target = "creationDate")
	@Mapping(source = "entity.updateDate", target = "updateDate")
	//@Mapping(source = "entity.cuenta.id", target = "cuenta.id")
	TransaccionResponseDto entityToResponse(TransaccionEntity entity);


	TransaccionEntity requestToEntity(TransaccionCreateRequestDto request);



}