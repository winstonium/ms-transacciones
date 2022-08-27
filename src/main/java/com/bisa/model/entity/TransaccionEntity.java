package com.bisa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaccion")
public class TransaccionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long numeroCuenta;

	private TipoTransaccion tipo;

	private Moneda moneda;
	
	private BigDecimal monto;

	private String descripcion;

	@CreationTimestamp
	private LocalDateTime creationDate;
	
	@UpdateTimestamp
	private LocalDateTime updateDate;
	
	private Status status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cuenta_id")
	private CuentaEntity cuenta;
	
	@PrePersist
	void setPrePersist() {
		status = Status.CREATED;
	}

}
