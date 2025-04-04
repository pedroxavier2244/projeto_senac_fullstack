package br.com.aplrm.aplrm.controllers;

import br.com.aplrm.aplrm.dto.PedidoDTO;
import br.com.aplrm.aplrm.dto.PedidoRequest;
import br.com.aplrm.aplrm.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/{id}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable Integer id) {
        PedidoDTO dto = pedidoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> insert(@Valid @RequestBody PedidoRequest pedidoRequest) {
        PedidoDTO dto = pedidoService.insert(pedidoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {
       List<PedidoDTO> dto = pedidoService.findAll();
        return ResponseEntity.ok(dto);
    }
}
