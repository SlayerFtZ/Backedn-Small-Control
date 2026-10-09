package backend.small.control.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "codigo", nullable = false, length = 30)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(
        name = "precio_mensual",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal precioMensual;

    @Column(
        name = "precio_anual",
        precision = 12,
        scale = 2
    )
    private BigDecimal precioAnual;

    @Column(
        name = "moneda",
        nullable = false,
        length = 3
    )
    private String moneda;

    @Column(
        name = "dias_prueba",
        nullable = false
    )
    private Integer diasPrueba;

    @Column(name = "max_sucursales")
    private Integer maxSucursales;

    @Column(name = "max_usuarios")
    private Integer maxUsuarios;

    @Column(name = "max_locales")
    private Integer maxLocales;

    @Column(name = "almacenamiento_mb")
    private Integer almacenamientoMb;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "caracteristicas", columnDefinition = "json")
    private Object caracteristicas;

    @Column(
        name = "visible",
        nullable = false
    )
    private Boolean visible;

    @Column(
        name = "creado_en",
        nullable = false,
        updatable = false
    )
    private LocalDateTime creadoEn;

    @Column(
        name = "creado_por",
        columnDefinition = "BINARY(16)"
    )
    private UUID creadoPor;

    @Column(
        name = "actualizado_en",
        nullable = false
    )
    private LocalDateTime actualizadoEn;

    @Column(
        name = "actualizado_por",
        columnDefinition = "BINARY(16)"
    )
    private UUID actualizadoPor;

    @Column(
        name = "eliminado_en"
    )
    private LocalDateTime eliminadoEn;

    @Column(
        name = "eliminado_por",
        columnDefinition = "BINARY(16)"
    )
    private UUID eliminadoPor;

    @Version
    @Column(
        name = "version",
        nullable = false
    )
    private Integer version;
}