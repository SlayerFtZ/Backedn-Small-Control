package backend.small.control.enums;

public enum EstadoUsuarioAcceso {
    ACTIVO("activo"),
    SUSPENDIDO("suspendido");

    private final String valor;

    EstadoUsuarioAcceso(String valor) { this.valor = valor; }

    public String getValor() { return valor; }

    public static EstadoUsuarioAcceso desdeValor(String valor) {
        for (EstadoUsuarioAcceso e : values()) {
            if (e.valor.equalsIgnoreCase(valor)) return e;
        }
        throw new IllegalArgumentException("Estado de acceso desconocido: " + valor);
    }
}