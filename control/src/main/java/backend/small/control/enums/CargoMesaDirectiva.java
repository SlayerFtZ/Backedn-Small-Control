package backend.small.control.enums;

public enum CargoMesaDirectiva {
    PRESIDENTE("presidente"),
    VICEPRESIDENTE("vicepresidente"),
    SECRETARIO("secretario"),
    TESORERO("tesorero"),
    VOCAL("vocal");

    private final String valor;

    CargoMesaDirectiva(String valor) { this.valor = valor; }

    public String getValor() { return valor; }

    public static CargoMesaDirectiva desdeValor(String valor) {
        for (CargoMesaDirectiva c : values()) {
            if (c.valor.equalsIgnoreCase(valor)) return c;
        }
        throw new IllegalArgumentException("Cargo de mesa directiva desconocido: " + valor);
    }
}