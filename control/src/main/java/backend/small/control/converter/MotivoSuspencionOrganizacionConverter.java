package backend.small.control.converter;

import backend.small.control.enums.MotivoSuspencionOrganizacion;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MotivoSuspencionOrganizacionConverter
        implements AttributeConverter<MotivoSuspencionOrganizacion, String> {

    @Override
    public String convertToDatabaseColumn(MotivoSuspencionOrganizacion motivo) {
        return motivo == null ? null : motivo.getValor();
    }

    @Override
    public MotivoSuspencionOrganizacion convertToEntityAttribute(String valor) {
        return valor == null ? null : MotivoSuspencionOrganizacion.desdeValor(valor);
    }
}