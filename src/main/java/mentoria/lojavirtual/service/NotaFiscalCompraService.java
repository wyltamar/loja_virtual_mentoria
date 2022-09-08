package mentoria.lojavirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import mentoria.lojavirtual.model.dto.RelatorioProdCompradoNotaFiscalDTO;

@Service
public class NotaFiscalCompraService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<RelatorioProdCompradoNotaFiscalDTO> gerarRelatoriProdCompraNota(
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

	
}
