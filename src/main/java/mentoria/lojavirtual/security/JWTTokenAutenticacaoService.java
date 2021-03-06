package mentoria.lojavirtual.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import mentoria.lojavirtual.ApplicationContextLoad;
import mentoria.lojavirtual.model.Usuario;
import mentoria.lojavirtual.repository.UsuarioRepository;

/*Cria e retorna a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*token com validade de 3 dias */
	private static final long EXPIRATION_TIME = 950400000;
	
	/*Chave de senha para juntar com o JWT*/
	private static final String SECRET = "33CbuS67";
	
	/*Tipo de autenticação*/
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	/*Gera o token e dar a resposta para o cliente com JWT*/
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		/*Montagem do token*/
		String JWT = Jwts.builder(). /*Chama o gerador de token*/
					 setSubject(username). /*Adiciona o user*/
					 setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))./*Tempo de expiração*/
					 signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		/*Exemplo: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
		 * 				  eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
		 * 				  SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c */
		String token = TOKEN_PREFIX + " " + JWT;
		
		/*Dar a resposta para a tela e para outro cliente, ex. outra API, navegador, aplicativo, javascript, 
		 * outra chamada java*/
		response.addHeader(HEADER_STRING, token);
		liberacaoCors(response);
		
		/*Usados para ver no Postman para teste*/
		response.getWriter().write("{\"Autorization\": \"" + token + "\"}");
	}
	
	/*Método que retorna o usuáiro validado com token ou caso não seja válido retorna null*/
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_STRING);
		
		try {
			
			if(token != null) {
				
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
				
				/*Faz a validação do token do usuário na requisição e obtem o User*/
				
				String user = Jwts.parser().
							  setSigningKey(SECRET).
							  parseClaimsJws(tokenLimpo).
							  getBody().getSubject();
				
				if(user != null) {
					
					Usuario usuario = ApplicationContextLoad.
									  getApplicationContext().
									  getBean(UsuarioRepository.class).findUsuarioByLogin(user);
					
					if(usuario != null) {
						
						return new UsernamePasswordAuthenticationToken(
								usuario.getLogin(),
								usuario.getSenha(),
								usuario.getAuthorities());
					}
									  
				}
				
			}
			
		}catch (SignatureException e) {
			
			response.getWriter().write("Token inválido!");
			
		}catch(ExpiredJwtException e) {
			response.getWriter().write("Token expirado, faça login novamente!");
		}finally {
			
			liberacaoCors(response);
		}
		
		
	
		return null;
	}
	
	/*Fazendo liberação contra erro de Cors no navegador*/
	private void liberacaoCors(HttpServletResponse response) {
		
		if(response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if(response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		if(response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		if(response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}
}
