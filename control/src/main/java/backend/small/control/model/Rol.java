package backend.small.control.model;
import backend.small.control.converter.CategoriaRolConverter;
import backend.small.control.enums.CategoriaRol;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "rol")
@SQLDelete(sql = "UPDATE rol SET eliminado_en = CURRENT_TIMESTAMP(3) WHERE id = ? AND version = ?")
@SQLRestriction("eliminado_en IS NULL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"organizacion", "permisos", "usuariosAcceso"})
public class Rol {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    /** NULL = rol de sistema (plantilla global) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @Column(name = "codigo", nullable = false, length = 40)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Convert(converter = CategoriaRolConverter.class)
    @Column(name = "categoria", nullable = false)
    private CategoriaRol categoria;

    /** No editable desde la app de la organización */
    @Column(name = "es_sistema", nullable = false)
    @Builder.Default
    private Boolean esSistema = false;

    // ---------- Relaciones ----------
    @ManyToMany
    @JoinTable(
        name = "rol_permiso",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    @Builder.Default
    private Set<Permiso> permisos = new HashSet<>();

    @OneToMany(mappedBy = "rol")
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