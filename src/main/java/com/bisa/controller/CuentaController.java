package com.bisa.controller;

import com.bisa.model.dto.*;
import com.bisa.model.entity.EstadoCuenta;
import com.bisa.model.entity.TipoTransaccion;
import com.bisa.service.CuentaService;
import com.bisa.service.TransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "v1/cuenta")
@RequiredArgsConstructor

public class CuentaController {


	@Autowired
	private CuentaService service;

	@Autowired
	private TransaccionService transaccionService;


	@Operation(summary = "Lista de cuentas general", description = "Permite realizar listado de todas las cuentas.")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<List<CuentaResponseDto>> findAll(@RequestParam(required = false) EstadoCuenta estadoCuenta){
		List<CuentaResponseDto> result = service.findAll(estadoCuenta);
		if(result.isEmpty()) {
			return ResponseEntity.noContent().build();	
		}
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "Crear cuenta", description = "Permite la creación de una cuenta para un determinado cliente")
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<CuentaResponseDto> registrar(@Valid @RequestBody CuentaCreateRequestDto cuentaRequest) {
		CuentaResponseDto result = service.create(cuentaRequest);
		return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
	}

	@Operation(summary = "Depositar dinero", description = "Permite realizar una operación de ABONO en cuenta")
	@PatchMapping(value = "/{numeroCuenta}/abonar/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<CuentaResponseDto> abonarMonto(@PathVariable Long numeroCuenta, @Valid @RequestBody TransaccionOperacionRequestDto cuentaRequest) {
		CuentaResponseDto result = service.actualizarSaldo(numeroCuenta, cuentaRequest, TipoTransaccion.CREDITO);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "Retirar dinero", description = "Permite realizar una operación de RETIRO en cuenta")
	@PatchMapping(value = "/{numeroCuenta}/retirar/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<CuentaResponseDto> retirarMonto(@PathVariable Long numeroCuenta, @Valid @RequestBody TransaccionOperacionRequestDto cuentaRequest) {
		CuentaResponseDto result = service.actualizarSaldo(numeroCuenta, cuentaRequest, TipoTransaccion.DEBITO);
		return ResponseEntity.ok(result);
	}


	@Operation(summary = "Consultar Saldo", description = "Permite realizar consulta de saldo en cuenta")
	@GetMapping(value = "/{numeroCuenta}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<CuentaResponseDto> buscarNroCuenta(@PathVariable Long numeroCuenta) throws InterruptedException {
		CuentaResponseDto result = service.buscarPorNumeroCuenta(numeroCuenta);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "Historico de transacciones", description = "Permite realizar consulta del histórico de transacciones de una cuenta")
	@GetMapping(value = "/{numeroCuenta}/transacciones", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<List<TransaccionResponseDto>> transaccionesPorNroCuenta(@PathVariable Long numeroCuenta) throws InterruptedException {
		List<TransaccionResponseDto> result = transaccionService.transaccionesPorNroCuenta(numeroCuenta);
		return ResponseEntity.ok(result);
	}

}
