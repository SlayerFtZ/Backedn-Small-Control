package backend.small.control.enums;

public enum EstadoSucursal {
    ACTIVA("activa"),
    INACTIVA("inactiva");

    private final String valor;

    EstadoSucursal(String valor) { this.valor = valor; }

    public String getValor() { return valor; }

    public static EstadoSucursal desdeValor(String valor) {
        for (EstadoSucursal e : values()) {
            if (e.valor.equalsIgnoreCase(valor)) return e;
        }
        throw new IllegalArgumentException("Estado de sucursal desconocido: " + valor);
    }
}