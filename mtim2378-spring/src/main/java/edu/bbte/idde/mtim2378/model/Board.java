package edu.bbte.idde.mtim2378.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board extends BaseEntity {

    private String title;

    @OneToMany(fetch = FetchType.EAGER,
        mappedBy = "board",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void addTodo(Todo todo) {
        todos.add(todo);
        todo.setBoard(this);
    }

    public void removeTodo(Todo todo) {
        todos.remove(todo);
        todo.setBoard(null);
    }
}
