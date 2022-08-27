package com.bisa.controller;

import com.bisa.model.dto.TransaccionResponseDto;
import com.bisa.service.TransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/transaccion")
@RequiredArgsConstructor
public class TransaccionController {
	@Autowired
	private TransaccionService service;

	@Operation(summary = "Historico de transacciones general", description = "Permite realizar consulta del histórico de transacciones en general")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<List<TransaccionResponseDto>> findAll(){
		List<TransaccionResponseDto> result = service.findAll();
		if(result.isEmpty()) {
			return ResponseEntity.noContent().build();	
		}
		return ResponseEntity.ok(result);
	}
	
}
