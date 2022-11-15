package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.Endereco;

@Repository
@Transactional
public interface EnderecoRepository extends JpaRepository<Endereco, Long>  {

	@Query(value = "select e from Endereco e where e.pessoa.id = ?1")
	public List<Endereco> buscarEnderecosPessoa(Long idPessoa);

}
