package mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VendaService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void exclusaoLogicaVenda(Long id) {
		String sql = "begin; update vd_cp_loja_virt set excluido = true where id = "+id+";commit;";
		jdbcTemplate.execute(sql);
	}
	
	public void exclusaoTotalVenda(Long idVenda) {
		
		String sql = 
				  "begin;"
				+ "UPDATE nota_fiscal_venda set venda_compra_loja_virtual_id = null where venda_compra_loja_virtual_id = "+idVenda+";"
				+ "delete from nota_fiscal_venda where venda_compra_loja_virtual_id = "+idVenda+";"
				+ "delete from status_rastreio where venda_compra_loja_virtual_id = "+idVenda+";"
				+ "delete from item_venda_loja where venda_compra_loja_virtual_id = "+idVenda+";"
				+ "delete from vd_cp_loja_virt where id = "+idVenda+";"
				+ "commit;";
		jdbcTemplate.execute(sql);
	}
	
	public void ativaVenda(Long idVenda) {
		
		String sql = "begin; update vd_cp_loja_virt set excluido = false where id = "+idVenda+"; commit; ";
		jdbcTemplate.execute(sql);
	}
	

}
