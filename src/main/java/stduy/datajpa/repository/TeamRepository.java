package stduy.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stduy.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
