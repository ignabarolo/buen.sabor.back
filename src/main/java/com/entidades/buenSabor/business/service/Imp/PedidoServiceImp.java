package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.business.mapper.PedidoMapper;
import com.entidades.buenSabor.business.service.Base.BaseServiceImp;
import com.entidades.buenSabor.business.service.PedidoService;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoCreateDto;
import com.entidades.buenSabor.domain.entities.Pedido;
import com.entidades.buenSabor.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImp extends BaseServiceImp<Pedido,Long> implements PedidoService {
    @Autowired
    private PedidoRepository repositoryPedido;

    final private PedidoMapper mapperPedido;

    public PedidoServiceImp(PedidoMapper mapperPedido) {
        this.mapperPedido = mapperPedido;
    }

    public void CreatePedido(PedidoCreateDto dto){
        var entity = mapperPedido.toEntityCreate(dto);
        System.out.println(entity);
    }
}

