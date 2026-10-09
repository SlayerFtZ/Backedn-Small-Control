package backend.small.control.enums;

public enum EstadoOrganizacion {
    ACTIVA("activa"),
    SUSPENDIDA("suspendida"),
    CANCELADA("cancelada");

    private final String valor;

    EstadoOrganizacion(String valor) { this.valor = valor; }

    public String getValor() { return valor; }

    public static EstadoOrganizacion desdeValor(String valor) {
        for (EstadoOrganizacion e : values()) {
            if (e.valor.equalsIgnoreCase(valor)) return e;
        }
        throw new IllegalArgumentException("Estado de organización desconocido: " + valor);
    }
}