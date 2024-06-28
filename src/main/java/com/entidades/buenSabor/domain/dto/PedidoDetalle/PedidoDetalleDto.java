package com.entidades.buenSabor.domain.dto.PedidoDetalle;

import com.entidades.buenSabor.domain.entities.ArticuloManufacturado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleDto {
    private Integer cantidad;
    private Double subTotal;

    private ArticuloManufacturado articulo;
}
