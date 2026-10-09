package backend.small.control.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "permiso")
@SQLDelete(sql = "UPDATE permiso SET eliminado_en = CURRENT_TIMESTAMP(3) WHERE id = ? AND version = ?")
@SQLRestriction("eliminado_en IS NULL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "roles")
public class Permiso {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    /** Formato modulo.accion, ej. egreso.aprobar */
    @Column(name = "codigo", nullable = false, length = 60, unique = true)
    private String codigo;

    @Column(name = "modulo", nullable = false, length = 40)
    private String modulo;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // ---------- Relaciones ----------
    @ManyToMany(mappedBy = "permisos")
    @Builder.Default
    private Set<Rol> roles = new HashSet<>();

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