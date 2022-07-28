package mentoria.lojavirtual.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import mentoria.lojavirtual.repository.Vd_Cp_Loja_VirtRepository;

@Service
public class VendaService {
	
	@Autowired
	private Vd_Cp_Loja_VirtRepository vd_Cp_Loja_VirtRepository;
		
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
	
	/*HQL (Hibernate) ou JPQL (JPA ou Spring Data)*/
	public List<VendaCompraLojaVirtual> consultaVendaFaixaData(String data1,String data2)throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt1 = dateFormat.parse(data1);
		Date dt2 = dateFormat.parse(data2);
		
		return vd_Cp_Loja_VirtRepository.consultaVendaFaixaData(dt1, dt2);
	}
	

}
