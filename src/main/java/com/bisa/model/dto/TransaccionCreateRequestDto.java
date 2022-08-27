package com.bisa.model.dto;

import com.bisa.model.entity.Moneda;
import com.bisa.model.entity.TipoTransaccion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransaccionCreateRequestDto {

	@NotNull
	private TipoTransaccion tipo;

	@NotNull
	private Moneda moneda;

	@NotNull
	private Long numeroCuenta;

	@NotNull
	@Min(1)
	private BigDecimal monto;

	@NotBlank
	private String descripcion;

	@NotNull
	@JsonProperty("cuenta_id")
	private Long cuentaId;

}
