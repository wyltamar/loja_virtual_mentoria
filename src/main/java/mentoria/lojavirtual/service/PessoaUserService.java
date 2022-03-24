package mentoria.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mentoria.lojavirtual.model.PessoaJuridica;
import mentoria.lojavirtual.model.Usuario;
import mentoria.lojavirtual.repository.PessoaRepository;
import mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) {
		
		for(int i = 0; i < juridica.getEnderecos().size(); i++) {
			
			juridica.getEnderecos().get(i).setEmpresa(juridica);
			juridica.getEnderecos().get(i).setPessoa(juridica);
		}
		
		juridica = pessoaRepository.save(juridica);
		
		Usuario usuarioPj = pessoaRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());
		
		if(usuarioPj == null) {
			
			String constraint = usuarioRepository.consultaConstraintAcesso();
			
			if(constraint != null) {
				
				jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint + "; commit;");
			}
			
			usuarioPj = new Usuario();
			
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(juridica);
			usuarioPj.setPessoa(juridica);
			usuarioPj.setLogin(juridica.getEmail());
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPj.setSenha(senhaCript);
			
			usuarioPj = usuarioRepository.save(usuarioPj);
			
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId());
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");
			
			StringBuilder mensagemHtml = new StringBuilder();
			
			mensagemHtml.append("<b>Segue abaixo os seus dados de acesso para a loja virtual</b><br>");
			mensagemHtml.append("<b>Login:</b> ").append(juridica.getEmail()).append("<br>");
			mensagemHtml.append("<b>Senha:</b> ").append(senha).append("<br>");
			mensagemHtml.append("<b>Obrigado!!</b>");
			
			try {
				serviceSendEmail.enviarEmail("Acesso gerado para a loja virtual", mensagemHtml.toString(), juridica.getEmail());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return juridica;
		
	}
	
}
