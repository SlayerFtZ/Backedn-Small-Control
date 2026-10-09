package backend.small.control.enums;

public enum CategoriaRol {
    ADMINISTRACION("administracion"),
    EMPLEADO("empleado"),
    SOCIO("socio"),
    ARRENDATARIO("arrendatario"),
    CONTADOR("contador");

    private final String valor;

    CategoriaRol(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static CategoriaRol desdeValor(String valor) {
        for (CategoriaRol c : values()) {
            if (c.valor.equalsIgnoreCase(valor)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoría de rol desconocida: " + valor);
    }
}