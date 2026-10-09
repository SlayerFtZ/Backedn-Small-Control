package backend.small.control.converter;

import backend.small.control.enums.CargoMesaDirectiva;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CargoMesaDirectivaConverter implements AttributeConverter<CargoMesaDirectiva, String> {

    @Override
    public String convertToDatabaseColumn(CargoMesaDirectiva cargo) {
        return cargo == null ? null : cargo.getValor();
    }

    @Override
    public CargoMesaDirectiva convertToEntityAttribute(String valor) {
        return valor == null ? null : CargoMesaDirectiva.desdeValor(valor);
    }
}