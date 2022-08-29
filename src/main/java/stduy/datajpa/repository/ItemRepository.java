package stduy.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stduy.datajpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
