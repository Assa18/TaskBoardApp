package edu.bbte.idde.mtim2378.repository.jpa;

import edu.bbte.idde.mtim2378.model.Todo;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("datajpa")
public interface JpaTodoRepository extends JpaRepository<Todo, Long> {
}
