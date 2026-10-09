package backend.small.control.converter;

import backend.small.control.enums.EstadoOrganizacion;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EstadoOrganizacionConverter implements AttributeConverter<EstadoOrganizacion, String> {

    @Override
    public String convertToDatabaseColumn(EstadoOrganizacion estado) {
        return estado == null ? null : estado.getValor();
    }

    @Override
    public EstadoOrganizacion convertToEntityAttribute(String valor) {
        return valor == null ? null : EstadoOrganizacion.desdeValor(valor);
    }
}