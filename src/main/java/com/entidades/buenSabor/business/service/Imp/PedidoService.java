package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.business.mapper.PedidoMapper;
import com.entidades.buenSabor.domain.dto.PedidoDetalle.PedidoDetalleDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoCreateDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoCreateTransferDto;
import com.entidades.buenSabor.domain.entities.ArticuloManufacturadoDetalle;
import com.entidades.buenSabor.repositories.DetallePedidoRepository;
import com.entidades.buenSabor.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repositoryPedido;

    @Autowired
    private DetallePedidoRepository repositoryDetallePedido;

    final private PedidoMapper mapperPedido;

    public PedidoService(PedidoMapper mapperPedido) {
        this.mapperPedido = mapperPedido;
    }

    public void CreatePedido(PedidoCreateDto dto) throws Exception {
        var transfer = MappingCustom(dto);
        var entity = mapperPedido.toEntityCreateTransfer(transfer);
        var pedido = repositoryPedido.save(entity);
//        int cant =  entity.getDetallePedido().size();
//        for (DetallePedido detalle : entity.getDetallePedido()) {
//            var detalleSave = repositoryDetallePedido.save(detalle);
//        }
//        var detalles = repositoryDetallePedido.saveAll(entity.getDetallePedido());
    }

    public PedidoCreateTransferDto MappingCustom(PedidoCreateDto dto) throws Exception {
        int totalMinutes = 0;
        var subTotal = 0;
        var totalCosto = 0.0D;
        for (PedidoDetalleDto detalle : dto.getDetallePedido()) {
            totalMinutes += detalle.getArticulo().getTiempoEstimadoMinutos();
            detalle.setSubTotal(detalle.getCantidad() * detalle.getArticulo().getPrecioVenta());

            for (ArticuloManufacturadoDetalle detalleManufacturado : detalle.getArticulo().getArticuloManufacturadoDetalles()) {
                // Disminuye el stock
                var stockActual = detalleManufacturado.getArticuloInsumo().getStockActual();

                if (stockActual <= 0){
                    throw new Exception("No hay stock");
                } else {
                    detalleManufacturado.getArticuloInsumo().setStockActual(stockActual - detalleManufacturado.getCantidad());
                }

                var insumo = detalleManufacturado.getArticuloInsumo();
                var medida = detalleManufacturado.getArticuloInsumo().getUnidadMedida().getDenominacion();

                // Calcula el costo
                if (medida.equals("Gramos")){
                    totalCosto += (detalleManufacturado.getCantidad() / 100) * insumo.getPrecioCompra();
                } else {
                    totalCosto += detalleManufacturado.getCantidad() * insumo.getPrecioCompra();
                }
            }

        }
        int hours = totalMinutes / 60;
        int remainingMinutes = totalMinutes % 60;
        dto.setTotalCosto(totalCosto);
        LocalTime horaEstimada = LocalTime.of(hours, remainingMinutes);

        var transfer = new PedidoCreateTransferDto();
        transfer.setTotalCosto(totalCosto);
        transfer.setDetallePedido(dto.getDetallePedido());
        transfer.setEstado(dto.getEstado());
        transfer.setHoraEstimadaFinalizacion(horaEstimada);
        transfer.setFechaPedido(LocalDate.now());
        transfer.setFormaPago(dto.getFormaPago());
        transfer.setTotal(dto.getTotal());
        transfer.setIdSucursal(Long.parseLong(dto.getIdSucursal()));
        transfer.setTotalCosto(dto.getTotalCosto());
        transfer.setTipoEnvio(dto.getTipoEnvio());
        return transfer;
    }
}

