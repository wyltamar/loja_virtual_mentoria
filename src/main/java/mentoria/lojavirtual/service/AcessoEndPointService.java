package mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class AcessoEndPointService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public void qtdAcessoEndPoint(String endPoint) {
	
		String sql = "begin; update acessos_end_point set qtd_acesso_end_point = qtd_acesso_end_point + 1 where nome_end_point = ?; commit;";
		
		jdbcTemplate.update(sql,endPoint);
	}

}
