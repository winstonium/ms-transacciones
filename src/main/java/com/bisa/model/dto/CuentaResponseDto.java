package com.bisa.model.dto;

import com.bisa.model.entity.EstadoCuenta;
import com.bisa.model.entity.Moneda;
import com.bisa.model.entity.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CuentaResponseDto {

	private Long id;
	
	private Long codCliente;

	private Long numeroCuenta;
	private Moneda moneda;
	private BigDecimal saldo;
	private EstadoCuenta estadoCuenta;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;

	private List<TransaccionResponseDto> transacciones;
	
}
