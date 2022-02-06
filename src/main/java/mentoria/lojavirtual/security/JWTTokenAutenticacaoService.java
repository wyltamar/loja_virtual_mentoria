package mentoria.lojavirtual.security;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*Cria e retorna a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*token com validade de 3 dias*/
	private static final long EXPIRATION_TIME = 950400000;
	
}
