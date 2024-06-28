package com.entidades.buenSabor.presentation.rest;

import com.entidades.buenSabor.business.facade.Imp.PedidoFacadeImp;
import com.entidades.buenSabor.business.service.Imp.PedidoService;
import com.entidades.buenSabor.business.service.Imp.PedidoServiceImp;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoCreateDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoDto;
import com.entidades.buenSabor.domain.entities.Pedido;
import com.entidades.buenSabor.presentation.rest.Base.BaseControllerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedido")
public class PedidoController extends BaseControllerImp<Pedido, PedidoDto, PedidoCreateDto,PedidoCreateDto,Long, PedidoFacadeImp> {

    @Autowired
    private PedidoService pedidoService;

    public PedidoController(PedidoFacadeImp facade) {
        super(facade);
    }

    @PostMapping("Create")
    public ResponseEntity<?> PedidoControllerCreate(@RequestBody PedidoCreateDto dto) {
        try {
            pedidoService.CreatePedido(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
