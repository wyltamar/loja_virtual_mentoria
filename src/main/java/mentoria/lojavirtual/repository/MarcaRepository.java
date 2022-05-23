package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.MarcaProduto;

@Repository
@Transactional
public interface MarcaRepository extends JpaRepository<MarcaProduto, Long> {

	@Query("select m from MarcaProduto m where upper(trim(m.nomeDesc)) like %?1%")
	public List<MarcaProduto> buscarMarcaDesc(String desc);
		
}
