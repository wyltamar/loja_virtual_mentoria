package mentoria.lojavirtual.repository;
import java.util.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.VendaCompraLojaVirtual;

@Repository
@Transactional
public interface Vd_Cp_Loja_VirtRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {
	
	@Query(value = "select a from VendaCompraLojaVirtual a where a.id = ?1 and a.excluido = false")
	public VendaCompraLojaVirtual findByIdExclusaoBanco(Long idVenda);
	
	@Query(value = "select i.vendaCompraLojaVirtual from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and i.produto.id = ?1")
	public List<VendaCompraLojaVirtual> consultaVendasPorProdutoId(Long idProduto);

	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.produto.nome)) like %?1%")
	public List<VendaCompraLojaVirtual> vendaPorNomeProduto(String nomeProduto);
	 
	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and i.vendaCompraLojaVirtual.pessoa.id = ?1" )
	public List<VendaCompraLojaVirtual> vendaPorCliente(Long idCliente);

	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.pessoa.nome)) like %?1%")
	public List<VendaCompraLojaVirtual> vendaPorNomeCliente(String nomeCliente);
	
	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.pessoa.nome)) like %?1% and i.vendaCompraLojaVirtual.pessoa.cpf = ?2")
	public List<VendaCompraLojaVirtual> vendaPorNomeClienteCpf(String nomeCliente, String cpf);
	
	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.pessoa.cpf)) like %?1%")
	public List<VendaCompraLojaVirtual> vendaPorCpfClienteLike(String cpf);
	
	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and i.vendaCompraLojaVirtual.pessoa.cpf = ?1")
	public List<VendaCompraLojaVirtual> vendaPorCpfClienteIgual(String cpf);

	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.enderecoCobranca.logradouro)) like %?1%")
	public List<VendaCompraLojaVirtual> vendaPorEnderecoCobranca(String enderecoCobranca);

	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i where i.vendaCompraLojaVirtual.excluido = false and upper(trim(i.vendaCompraLojaVirtual.enderecoEntrega.logradouro)) like %?1%")
	public List<VendaCompraLojaVirtual> vendaPorEnderecoEntrega(String enderecoEntrega);

	@Query(value = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i"
			+ " where i.vendaCompraLojaVirtual.excluido = false"
			+ " and i.vendaCompraLojaVirtual.dataVenda >= ?1"
			+ " and i.vendaCompraLojaVirtual.dataVenda <= ?2")
	public List<VendaCompraLojaVirtual> consultaVendaFaixaData(Date data1,Date data2);

	
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "update vd_cp_loja_virt set codigo_etiqueta = ?1 where id = ?2")
	public void updateEtiqueta(String idEtiqueta, Long idVenda);

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "update vd_cp_loja_virt set url_imprime_etiqueta = ?1 where id = ?2")
	public void updateUrlEtiqueta(String urlEtiqueta, Long idVenda);
	

}


