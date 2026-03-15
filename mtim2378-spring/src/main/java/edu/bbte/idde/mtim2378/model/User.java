package edu.bbte.idde.mtim2378.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String theme;

    private String language;

    @OneToMany(fetch = FetchType.EAGER,
        mappedBy = "user",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true)
    private List<Board> boards;
}
