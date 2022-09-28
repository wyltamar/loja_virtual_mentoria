package mentoria.lojavirtual.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import mentoria.lojavirtual.model.dto.RelatorioCompraCanceladaDTO;
import mentoria.lojavirtual.repository.Vd_Cp_Loja_VirtRepository;

@Service
public class VendaService {
	
	@Autowired
	private Vd_Cp_Loja_VirtRepository vd_Cp_Loja_VirtRepository;
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * Título: Relatorio de vendas. Este relatório traz as vendas a partir 
	 * de uma faixa de data, por meio de status da venda, email do cliente
	 * ou pelo nome do produto.
	 * @param dataInicial
	 * @param dataFinal
	 * @param relatorioCompraCanceladaDTO
	 * @return List<RelatorioCompraCanceladaDTO> RelatorioCompraCanceladaDTO
	 */
	public List<RelatorioCompraCanceladaDTO> relatorioStatusVendaLoja(
			RelatorioCompraCanceladaDTO relatorioCompraCanceladaDTO){
		
		String sql  = " select prod.id as codigoProduto, " + 
				" prod.nome as nomeProduto, " + 
				" pf.email as emailCliente, " + 
				" pf.telefone as foneCliente, " + 
				" prod.valor_venda as valorVendaProduto, " + 
				" pf.id as codigoCliente, " + 
				" pf.nome as nomeCliente, " + 
				" prod.qtd_estoque as quantidadeEstoque, " + 
				" vclv.id as codigoVenda, " + 
				" vclv.status_venda_loja_virtual as status " + 
				" from vd_cp_loja_virt as vclv " + 
				" inner join item_venda_loja as ivl on ivl.venda_compra_loja_virtual_id = vclv.id " + 
				" inner join produto as prod on prod.id = ivl.produto_id " + 
				" inner join pessoa_fisica as pf on pf.id = vclv.pessoa_id " + 
				" where vclv.data_venda >= '"+relatorioCompraCanceladaDTO.getDataInicial()+"' "
			  + " and vclv.data_venda <= '"+relatorioCompraCanceladaDTO.getDataFinal()+"' ";
				
				if(!relatorioCompraCanceladaDTO.getNomeProduto().isEmpty()) {
				  sql += " and upper(prod.nome) like upper('%"+relatorioCompraCanceladaDTO.getNomeProduto()+"%') ";
				}
				
				if(!relatorioCompraCanceladaDTO.getStatus().isEmpty()) {
					sql += "and vclv.status_venda_loja_virtual = '"+relatorioCompraCanceladaDTO.getStatus()+"'";
				}
				
				if(!relatorioCompraCanceladaDTO.getEmailCliente().isEmpty()) {
					sql += "and pf.email = '"+relatorioCompraCanceladaDTO.getEmailCliente()+"'";
				}
				
		
		List<RelatorioCompraCanceladaDTO> retorno = new ArrayList<RelatorioCompraCanceladaDTO>();
		
		retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper(RelatorioCompraCanceladaDTO.class));
		
		return retorno;
	}
	
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
