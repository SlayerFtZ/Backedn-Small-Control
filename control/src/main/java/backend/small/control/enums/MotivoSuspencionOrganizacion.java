package backend.small.control.enums;

public enum MotivoSuspencionOrganizacion {
    FALTA_PAGO("falta_pago"),
    ADMINISTRATIVA("administrativa");

    private final String valor;

    MotivoSuspencionOrganizacion(String valor) { this.valor = valor; }

    public String getValor() { return valor; }

    public static MotivoSuspencionOrganizacion desdeValor(String valor) {
        for (MotivoSuspencionOrganizacion m : values()) {
            if (m.valor.equalsIgnoreCase(valor)) return m;
        }
        throw new IllegalArgumentException("Motivo de suspensión desconocido: " + valor);
    }
}