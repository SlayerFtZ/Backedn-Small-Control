package backend.small.control.converter;

import backend.small.control.enums.CategoriaRol;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CategoriaRolConverter implements AttributeConverter<CategoriaRol, String> {

    @Override
    public String convertToDatabaseColumn(CategoriaRol categoria) {
        return categoria == null ? null : categoria.getValor();
    }

    @Override
    public CategoriaRol convertToEntityAttribute(String valor) {
        return valor == null ? null : CategoriaRol.desdeValor(valor);
    }
}