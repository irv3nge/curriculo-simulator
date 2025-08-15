package curriculo.demo.repo;

import curriculo.demo.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidaturaRepository extends JpaRepository<Candidatura , Long> {
}
