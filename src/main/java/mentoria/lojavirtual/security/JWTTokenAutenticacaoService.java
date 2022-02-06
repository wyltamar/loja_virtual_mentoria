package mentoria.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*Cria e retorna a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*token com validade de 3 dias*/
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
	}
	
}
