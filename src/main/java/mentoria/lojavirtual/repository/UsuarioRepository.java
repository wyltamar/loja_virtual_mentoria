package mentoria.lojavirtual.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mentoria.lojavirtual.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	@Query(value = "select u from Usuario u where u.login = ?1")
	Usuario findUsuarioByLogin(String login);

	@Query(value="select constraint_name from information_schema.constraint_column_usage \r\n" + 
			" where table_name = 'usuario_acesso' and column_name = 'acesso_id'\r\n" + 
			"and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
	String consultaConstraintAcesso();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuario_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))")
	void insereAcessoUserPj(Long idUser);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuario_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = ?2 limit 1))")
	void insereAcessoUserPj(Long idUser, String acesso);


		
	
}
