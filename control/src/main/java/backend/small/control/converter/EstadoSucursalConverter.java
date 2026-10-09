package backend.small.control.converter;

import backend.small.control.enums.EstadoSucursal;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EstadoSucursalConverter implements AttributeConverter<EstadoSucursal, String> {

    @Override
    public String convertToDatabaseColumn(EstadoSucursal estado) {
        return estado == null ? null : estado.getValor();
    }

    @Override
    public EstadoSucursal convertToEntityAttribute(String valor) {
        return valor == null ? null : EstadoSucursal.desdeValor(valor);
    }
}