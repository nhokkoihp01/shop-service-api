package nlu.shop.api.repository;

import nlu.shop.api.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPokemonRepository extends JpaRepository<Pokemon,Integer> {
    Optional<Pokemon> findByType(String type);
}
