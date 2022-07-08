package mentoria.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.StatusRastreio;

@Repository
@Transactional
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {

}
