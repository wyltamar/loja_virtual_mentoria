package mentoria.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mentoria.lojavirtual.model.PessoaFisica;
import mentoria.lojavirtual.model.PessoaJuridica;
import mentoria.lojavirtual.model.Usuario;
import mentoria.lojavirtual.model.dto.CepDTO;
import mentoria.lojavirtual.model.dto.ConsultaCnpjDTO;
import mentoria.lojavirtual.repository.PessoaFisicaRepository;
import mentoria.lojavirtual.repository.PessoaRepository;
import mentoria.lojavirtual.repository.UsuarioRepository;

@Service
@Component
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
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
			usuarioRepository.insereAcessoUser(usuarioPj.getId(), "ROLE_ADMIN");
			
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

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {
		
		for(int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
			
			pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);
			pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
		}
		
		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
		
		Usuario usuarioPf = pessoaRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());
		
		if(usuarioPf == null) {
			
			String constraint = usuarioRepository.consultaConstraintAcesso();
			
			if(constraint != null) {
				
				jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint + "; commit;");
			}
			
			usuarioPf = new Usuario();
			
			usuarioPf.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPf.setEmpresa(pessoaFisica.getEmpresa());
			usuarioPf.setPessoa(pessoaFisica);
			usuarioPf.setLogin(pessoaFisica.getEmail());
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPf.setSenha(senhaCript);
			
			usuarioPf = usuarioRepository.save(usuarioPf);
			
		
			usuarioRepository.insereAcessoUser(usuarioPf.getId(), "ROLE_USER");
			
			StringBuilder mensagemHtml = new StringBuilder();
			
			mensagemHtml.append("<b>Segue abaixo os seus dados de acesso para a loja virtual</b><br>");
			mensagemHtml.append("<b>Login:</b> ").append(pessoaFisica.getEmail()).append("<br>");
			mensagemHtml.append("<b>Senha:</b> ").append(senha).append("<br>");
			mensagemHtml.append("<b>Obrigado!!</b>");
			
			try {
				serviceSendEmail.enviarEmail("Acesso gerado para a loja virtual", mensagemHtml.toString(), pessoaFisica.getEmail());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return pessoaFisica;
	}
	
	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+ cep +"/json/", CepDTO.class).getBody();
	}
	
	public ConsultaCnpjDTO conslutaCnpjReceitaWS(String cnpj) {
		return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/"+cnpj, ConsultaCnpjDTO.class).getBody();
	}
	
}
