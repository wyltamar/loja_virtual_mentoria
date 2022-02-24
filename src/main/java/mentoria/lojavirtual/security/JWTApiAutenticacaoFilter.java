package mentoria.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/*Filtro onde todas as requisições serão capturadas para autenticar*/
public class JWTApiAutenticacaoFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {

		/*Estabelecer a autrnticação do usuario*/
		
		Authentication authentication = new JWTTokenAutenticacaoService().
				getAuthentication((HttpServletRequest)request,(HttpServletResponse) response);
		
		/*Colocar o processo de autenticação para o Spring Security*/
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
		
		}catch(Exception e) {
			e.printStackTrace();
			response.getWriter().write("Ocorreu um erro no sistema, entre em contato com o administrador: \n"
										+e.getMessage());
		}
	}

}
