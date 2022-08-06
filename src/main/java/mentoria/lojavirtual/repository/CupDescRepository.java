package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.CupDesc;

@Repository
@Transactional
public interface CupDescRepository extends JpaRepository<CupDesc, Long> {
	
	@Query(value = "select cd from CupDesc cd")
	public List<CupDesc> carregarCuponsDesconto();

}
