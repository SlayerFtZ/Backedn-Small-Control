package backend.small.control.model;

import backend.small.control.converter.EstadoOrganizacionConverter;
import backend.small.control.converter.MotivoSuspencionOrganizacionConverter;
import backend.small.control.enums.EstadoOrganizacion;
import backend.small.control.enums.MotivoSuspencionOrganizacion;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "organizacion")
@SQLDelete(sql = "UPDATE organizacion SET eliminado_en = CURRENT_TIMESTAMP(3) WHERE id = ? AND version = ?")
@SQLRestriction("eliminado_en IS NULL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"sucursales"})
public class Organizacion {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    /** Identificador URL/subdominio, único entre activas */
    @Column(name = "slug", nullable = false, length = 63)
    private String slug;

    @Column(name = "nombre_comercial", nullable = false, length = 150)
    private String nombreComercial;

    @Column(name = "razon_social", length = 200)
    private String razonSocial;

    @Column(name = "rfc", length = 13)
    private String rfc;

    /** N:1 -> archivo. Se deja como UUID hasta que exista la entidad Archivo. */
    @Column(name = "logo_archivo_id", columnDefinition = "BINARY(16)")
    private UUID logoArchivoId;

    @Convert(converter = EstadoOrganizacionConverter.class)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private EstadoOrganizacion estado = EstadoOrganizacion.ACTIVA;

    /** Solo falta_pago se reactiva sola al pagar */
    @Convert(converter = MotivoSuspencionOrganizacionConverter.class)
    @Column(name = "motivo_suspension")
    private MotivoSuspencionOrganizacion motivoSuspension;

    @Column(name = "zona_horaria", nullable = false, length = 50)
    @Builder.Default
    private String zonaHoraria = "America/Mexico_City";

    @Column(name = "moneda", nullable = false, columnDefinition = "CHAR(3)")
    @Builder.Default
    private String moneda = "MXN";

    /** Ej: {"segregacion_egresos": true, "segregacion_ajustes": true} */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracion", columnDefinition = "JSON")
    private Map<String, Object> configuracion;

    // ---------- Relaciones ----------
    @OneToMany(mappedBy = "organizacion")
    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();

    // ---------- Auditoría (sin FK) ----------
    @CreationTimestamp
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "creado_por", columnDefinition = "BINARY(16)", updatable = false)
    private UUID creadoPor;

    @UpdateTimestamp
    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @Column(name = "actualizado_por", columnDefinition = "BINARY(16)")
    private UUID actualizadoPor;

    @Column(name = "eliminado_en")
    private LocalDateTime eliminadoEn;

    @Column(name = "eliminado_por", columnDefinition = "BINARY(16)")
    private UUID eliminadoPor;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;
}