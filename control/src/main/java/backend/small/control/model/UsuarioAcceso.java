package backend.small.control.model;

import backend.small.control.converter.CargoMesaDirectivaConverter;
import backend.small.control.converter.EstadoUsuarioAccesoConverter;
import backend.small.control.enums.CargoMesaDirectiva;
import backend.small.control.enums.EstadoUsuarioAcceso;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario_acceso")
@SQLDelete(sql = "UPDATE usuario_acceso SET eliminado_en = CURRENT_TIMESTAMP(3) WHERE id = ? AND version = ?")
@SQLRestriction("eliminado_en IS NULL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"organizacion", "sucursal", "rol"})
public class UsuarioAcceso {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    /**
     * N:1 -> usuario. Temporal como UUID hasta que exista la entidad Usuario.
     * Luego: @ManyToOne(fetch = LAZY, optional = false) @JoinColumn(name = "usuario_id") private Usuario usuario;
     */
    @Column(name = "usuario_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID usuarioId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    /** NULL = todas las sucursales */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    /** Solo para accesos con rol MESA_DIRECTIVA (lo valida un trigger en BD) */
    @Convert(converter = CargoMesaDirectivaConverter.class)
    @Column(name = "cargo_mesa_directiva")
    private CargoMesaDirectiva cargoMesaDirectiva;

    @Convert(converter = EstadoUsuarioAccesoConverter.class)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private EstadoUsuarioAcceso estado = EstadoUsuarioAcceso.ACTIVO;

    @Column(name = "vigente_desde")
    private LocalDate vigenteDesde;

    @Column(name = "vigente_hasta")
    private LocalDate vigenteHasta;

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