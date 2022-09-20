package mentoria.lojavirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import mentoria.lojavirtual.model.dto.RelatorioProdAlertaEstoqueDTO;
import mentoria.lojavirtual.model.dto.RelatorioProdCompradoNotaFiscalDTO;

@Service
public class NotaFiscalCompraService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Relatório de compra de produtos para a loja virtual.
	 * 
	 * Este relatório mostra todos os produtos comprados pela Loja virtual para serem vendidos,
	 * tais produtos tem relação com a nota fiscal de compra/venda, os parâmetros obirgatórios
	 * são imprescindíveis para a geração do relatório.
	 * @param relatorioProdCompradoNotaFiscalDTO
	 * @param dataInicial parâmetro obirgatório
	 * @param dataFinal parâmetro obirgatório
	 * @return List<RelatorioProdCompradoNotaFiscalDTO>
	 * 
	 * @author wyltamar
	 */
	public List<RelatorioProdCompradoNotaFiscalDTO> gerarRelatorioProdCompraNota(
			RelatorioProdCompradoNotaFiscalDTO relatorioProdCompradoNotaFiscalDTO) {
		
		String sql = " select nip.produto_id as codigo_produto,prod.nome as nome_produto, " + 
				" nfco.valor_total as valor_venda_produto,nip.quantidade as quantidade_comprada,pjf.id as cod_fornecedor, " + 
				" pjf.nome as nome_fornecedor,nfco.data_compra " +  
				" from nota_fiscal_compra as nfco " + 
				" inner join pessoa_juridica as pjf on nfco.pessoa_id = pjf.id " + 
				" inner join nota_item_produto as nip on nfco.id = nip.nota_fiscal_copra_id " + 
				" inner join produto as prod on nip.produto_id = prod.id where ";
		
		sql +=  " nfco.data_compra >= '"+ relatorioProdCompradoNotaFiscalDTO.getDataInicial()+"' "; 
		sql += 	" and nfco.data_compra <= '" +relatorioProdCompradoNotaFiscalDTO.getDataFinal()+"' ";	
		
		if(!relatorioProdCompradoNotaFiscalDTO.getCodigoNotaFiscalCompra().isEmpty()) {
			sql += " and nfco.id = " +relatorioProdCompradoNotaFiscalDTO.getCodigoNotaFiscalCompra()+ " ";
		}
		
		if(!relatorioProdCompradoNotaFiscalDTO.getCodigoProduto().isEmpty()) {
			sql += " and nip.produto_id = " + relatorioProdCompradoNotaFiscalDTO.getCodigoProduto()+ " ";
		}
		
		if(!relatorioProdCompradoNotaFiscalDTO.getNomeProduto().isEmpty()) {
			sql += " and upper(prod.nome) like upper('%"+relatorioProdCompradoNotaFiscalDTO.getNomeProduto() +"%') ";
		}
		
		if(!relatorioProdCompradoNotaFiscalDTO.getNomeFornecedor().isEmpty()) {
			sql += " and upper(pjf.nome) lique upper('%"+ relatorioProdCompradoNotaFiscalDTO.getNomeFornecedor()+ "%') ";
		}
		
		List<RelatorioProdCompradoNotaFiscalDTO> retorno = jdbcTemplate.query(sql, 
				new BeanPropertyRowMapper(RelatorioProdCompradoNotaFiscalDTO.class));
		
		return retorno;
	}
	
	public List<RelatorioProdAlertaEstoqueDTO> gerarRelatorioAlertaEstoque(
			RelatorioProdAlertaEstoqueDTO relatorio) {
		
		String sql = " select nip.produto_id as codigo_produto,prod.nome as nome_produto, " + 
				" nfco.valor_total as valor_venda_produto,nip.quantidade as quantidade_comprada,pjf.id as cod_fornecedor, " + 
				" pjf.nome as nome_fornecedor,nfco.data_compra, " +
				" prod.qtd_estoque as qtdEstoque, prod.qtd_alerta_estoque as qtdAlertaEstoque" +
				" from nota_fiscal_compra as nfco " + 
				" inner join pessoa_juridica as pjf on nfco.pessoa_id = pjf.id " + 
				" inner join nota_item_produto as nip on nfco.id = nip.nota_fiscal_copra_id " + 
				" inner join produto as prod on nip.produto_id = prod.id where ";
		
		sql += 	" nfco.data_compra >= '"+ relatorio.getDataInicial()+"' "; 
		sql += 	" and nfco.data_compra <= '" +relatorio.getDataFinal()+"' ";
		sql += 	" and prod.alerta_qtd_estoque = true ";
		sql +=  " and prod.qtd_estoque <= prod.qtd_alerta_estoque ";
		
		if(!relatorio.getCodigoNotaFiscalCompra().isEmpty()) {
			sql += " and nfco.id = " +relatorio.getCodigoNotaFiscalCompra()+ " ";
		}
		
		if(!relatorio.getCodigoProduto().isEmpty()) {
			sql += " and nip.produto_id = " + relatorio.getCodigoProduto()+ " ";
		}
		
		if(!relatorio.getNomeProduto().isEmpty()) {
			sql += " and upper(prod.nome) like upper('%"+relatorio.getNomeProduto() +"%') ";
		}
		
		if(!relatorio.getNomeFornecedor().isEmpty()) {
			sql += " and upper(pjf.nome) lique upper('%"+ relatorio.getNomeFornecedor()+ "%') ";
		}
		
		List<RelatorioProdAlertaEstoqueDTO> retorno = jdbcTemplate.query(sql, 
				new BeanPropertyRowMapper(RelatorioProdAlertaEstoqueDTO.class));
		
		return retorno;
	}
	

	
}
