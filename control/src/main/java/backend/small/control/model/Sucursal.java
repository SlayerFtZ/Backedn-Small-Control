package backend.small.control.model;

import backend.small.control.converter.EstadoSucursalConverter;
import backend.small.control.enums.EstadoSucursal;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sucursal")
@SQLDelete(sql = "UPDATE sucursal SET eliminado_en = CURRENT_TIMESTAMP(3) WHERE id = ? AND version = ?")
@SQLRestriction("eliminado_en IS NULL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"organizacion", "usuariosAcceso"})
public class Sucursal {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    /** N:1 -> organizacion (tenant) */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Column(name = "entidad_federativa", length = 100)
    private String entidadFederativa;

    @Column(name = "codigo_postal", columnDefinition = "CHAR(5)")
    private String codigoPostal;

    @Column(name = "telefono", length = 20)
    private String telefono;

    /** NULL = hereda de la organización */
    @Column(name = "zona_horaria", length = 50)
    private String zonaHoraria;

    @Convert(converter = EstadoSucursalConverter.class)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private EstadoSucursal estado = EstadoSucursal.ACTIVA;

    // ---------- Relaciones ----------
    /** 1:N -> usuario_acceso (sucursal_id NULL = todas, esos no aparecen aquí) */
    @OneToMany(mappedBy = "sucursal")
    @Builder.Default
    private Set<UsuarioAcceso> usuariosAcceso = new HashSet<>();

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