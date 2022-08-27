package com.bisa.model.dto;

import com.bisa.model.entity.CuentaEntity;
import com.bisa.model.entity.Moneda;
import com.bisa.model.entity.Status;
import com.bisa.model.entity.TipoTransaccion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransaccionResponseDto {

	private Long id;
	private Long numeroCuenta;
	private TipoTransaccion tipo;
	private Moneda moneda;
	private BigDecimal monto;
	private String descripcion;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;
	
	private Status status;

	private Long cuenta_id;

	//private CuentaResponseDto cuenta;

}
