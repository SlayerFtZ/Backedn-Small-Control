package backend.small.control.converter;

import backend.small.control.enums.EstadoUsuarioAcceso;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EstadoUsuarioAccesoConverter implements AttributeConverter<EstadoUsuarioAcceso, String> {

    @Override
    public String convertToDatabaseColumn(EstadoUsuarioAcceso estado) {
        return estado == null ? null : estado.getValor();
    }

    @Override
    public EstadoUsuarioAcceso convertToEntityAttribute(String valor) {
        return valor == null ? null : EstadoUsuarioAcceso.desdeValor(valor);
    }
}