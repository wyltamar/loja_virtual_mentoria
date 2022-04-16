package mentoria.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.Endereco;

@Repository
@Transactional
public interface EnderecoRepository extends JpaRepository<Endereco, Long>  {

}
