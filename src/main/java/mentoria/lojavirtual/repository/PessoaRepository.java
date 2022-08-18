package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mentoria.lojavirtual.model.PessoaFisica;
import mentoria.lojavirtual.model.PessoaJuridica;
import mentoria.lojavirtual.model.Usuario;

@Repository
public interface PessoaRepository extends CrudRepository<PessoaJuridica, Long> {
	
	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public PessoaJuridica existeCnpjCadastrado(String cnpj);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public List<PessoaJuridica> existeCnpjCadastradoList(String cnpj);
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public PessoaFisica existeCpfCadastrado(String cpf);
	
	
	@Query(value = "select u from PessoaJuridica u where u.inscEstadual = ?1")
	public PessoaJuridica existeInscEstadualCadastrado(String inscEstadual);

	@Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
	public Usuario findUserByPessoa(Long id, String email);

	@Query(value = "selec pj from PessoaJuridica pj where pj.nome like %?1%")
	public List<PessoaJuridica> pesquisaPorNomePJ(String nome);
	
}
