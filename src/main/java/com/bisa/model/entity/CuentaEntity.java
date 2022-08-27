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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cuenta")
public class CuentaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long codCliente;
	@Column(unique=true)
	private Long numeroCuenta;
	private Moneda moneda;
	private BigDecimal saldo;
	private EstadoCuenta estadoCuenta;
	@Column(columnDefinition = "integer default 0")
	private Integer excedido;

	@CreationTimestamp
	private LocalDateTime creationDate;

	@UpdateTimestamp
	private LocalDateTime updateDate;

	private Status status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cuenta_id", referencedColumnName = "id")
	private List<TransaccionEntity> transacciones;


	@PrePersist
	void setPrePersist() {
		estadoCuenta = EstadoCuenta.ACTIVE;
		status = Status.CREATED;
	}


}
