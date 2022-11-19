package mentoria.lojavirtual.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.enums.StatusContaReceber;
import mentoria.lojavirtual.model.ContaReceber;
import mentoria.lojavirtual.model.Endereco;
import mentoria.lojavirtual.model.ItemVendaLoja;
import mentoria.lojavirtual.model.PessoaFisica;
import mentoria.lojavirtual.model.StatusRastreio;
import mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import mentoria.lojavirtual.model.dto.ConsultaFreteDTO;
import mentoria.lojavirtual.model.dto.EmpresaTransporteDTO;
import mentoria.lojavirtual.model.dto.EnvioEtiquetaDTO;
import mentoria.lojavirtual.model.dto.ItemVendaDTO;
import mentoria.lojavirtual.model.dto.ProductEnvioEtiquetaDTO;
import mentoria.lojavirtual.model.dto.RelatorioCompraCanceladaDTO;
import mentoria.lojavirtual.model.dto.TagEnvioEtiquetaDTO;
import mentoria.lojavirtual.model.dto.VendaCompraLojaVirtualDTO;
import mentoria.lojavirtual.model.dto.VolumeEnvioEtiquetaDTO;
import mentoria.lojavirtual.repository.ContaReceberRepository;
import mentoria.lojavirtual.repository.EnderecoRepository;
import mentoria.lojavirtual.repository.NotaFiscalVendaRepository;
import mentoria.lojavirtual.repository.StatusRastreioRepository;
import mentoria.lojavirtual.repository.Vd_Cp_Loja_VirtRepository;
import mentoria.lojavirtual.service.ConsultaFreteService;
import mentoria.lojavirtual.service.ServiceSendEmail;
import mentoria.lojavirtual.service.VendaService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class Vd_Cp_Loja_VirtController {

	@Autowired
	private Vd_Cp_Loja_VirtRepository vd_Cp_Loja_VirtRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PessoaController pessoaController;

	@Autowired
	private NotaFiscalVendaRepository notaFiscalVendaRepository;
	
	@Autowired
	private StatusRastreioRepository statusRastreioRepository;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private ContaReceberRepository contaReceberRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Autowired
	private ConsultaFreteService consultaFreteService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@ResponseBody
	@PostMapping(value = "**/relatorioStatusVendaLoja")
	public ResponseEntity<List<RelatorioCompraCanceladaDTO>> relatorioStatusVendaLoja(
		@Valid @RequestBody	RelatorioCompraCanceladaDTO relatorioCompraCanceladaDTO)throws ExceptionMentoriaJava{
		
		List<RelatorioCompraCanceladaDTO> retorno = new ArrayList<RelatorioCompraCanceladaDTO>();
		
		retorno = vendaService.relatorioStatusVendaLoja(relatorioCompraCanceladaDTO);
		
		if(retorno.isEmpty()) {
			throw new ExceptionMentoriaJava("Registro não encontrado em nossa base de dados");
		}
		
		return new ResponseEntity<List<RelatorioCompraCanceladaDTO>>(retorno, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/salvarVendaLoja")
	public ResponseEntity<VendaCompraLojaVirtualDTO> salvarVendaLoja(
			@RequestBody @Valid VendaCompraLojaVirtual vendaCompraLojaVirtual) throws ExceptionMentoriaJava, UnsupportedEncodingException, MessagingException {

		vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
		PessoaFisica pessoaFisica = pessoaController.salvarPf(vendaCompraLojaVirtual.getPessoa()).getBody();
		vendaCompraLojaVirtual.setPessoa(pessoaFisica);

		vendaCompraLojaVirtual.getEnderecoCobranca().setPessoa(pessoaFisica);
		vendaCompraLojaVirtual.getEnderecoCobranca().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
		Endereco enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoCobranca());
		vendaCompraLojaVirtual.setEnderecoCobranca(enderecoCobranca);

		vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
		vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
		Endereco enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
		vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);

		/* Associa a nota fiscal a empresa */
		vendaCompraLojaVirtual.getNotaFiscalVenda().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

		/* Associa o produto(que está na classe ItemVenda) com a venda */
		for (int i = 0; i < vendaCompraLojaVirtual.getItemVendas().size(); i++) {
			vendaCompraLojaVirtual.getItemVendas().get(i).setEmpresa(vendaCompraLojaVirtual.getEmpresa());
			vendaCompraLojaVirtual.getItemVendas().get(i).setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
			
		}

		/* Salva primeiro a venda e todos os dados */
		 vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.saveAndFlush(vendaCompraLojaVirtual);
		
		StatusRastreio statusRastreio = new StatusRastreio();
		statusRastreio.setCentroDistribuicao("Local");
		statusRastreio.setCidade("Jerico");
		statusRastreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
		statusRastreio.setEstado("PB");
		statusRastreio.setStatus("Pedido realizado");
		statusRastreio.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
		
		statusRastreioRepository.save(statusRastreio);

		/* Associa a venda gravada no banco com a nota fiscal */
		vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

		/* Persiste novamente a nota fiscal para ficar amarrada na venda */
		notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());

		VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
		compraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
		compraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa());
		compraLojaVirtualDTO.setCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());
		compraLojaVirtualDTO.setEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
		compraLojaVirtualDTO.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
		compraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());
		compraLojaVirtualDTO.setId(vendaCompraLojaVirtual.getId());

		for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendas()) {

			ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
			itemVendaDTO.setProduto(item.getProduto());
			itemVendaDTO.setQuantidade(item.getQuantidade());
			
			compraLojaVirtualDTO.getItemVendas().add(itemVendaDTO);
		}
		
		ContaReceber contaReceber = new ContaReceber();
		contaReceber.setDescricao("Venda da Loja Virtual nº: "+vendaCompraLojaVirtual.getId());
		contaReceber.setDtPagamento(Calendar.getInstance().getTime());
		contaReceber.setDtVencimento(Calendar.getInstance().getTime());
		contaReceber.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
		contaReceber.setPessoa(vendaCompraLojaVirtual.getPessoa());
		contaReceber.setStatus(StatusContaReceber.QUITADA);
		contaReceber.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
		contaReceber.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
		
		contaReceberRepository.saveAndFlush(contaReceber);
		
		//Envio de email de confirmação da venda para o cliente 
		StringBuilder corpoEmail = new StringBuilder();
		corpoEmail.append("<h2>").append("Seu pedido de número "+vendaCompraLojaVirtual.getId());
		corpoEmail.append("</h2")
		.append(" realizado na loja "+vendaCompraLojaVirtual.getEmpresa().getNomeFantasia()+ " foi confirmado");
		corpoEmail.append("<br>");
		corpoEmail.append("<p>Obrigado pela preferência</p>");
		
		if(vendaCompraLojaVirtual.getPessoa().getEmail() != null) {
			serviceSendEmail.enviarEmail("Confiramção de compra na LojaVirtual", corpoEmail.toString(), vendaCompraLojaVirtual.getPessoa().getEmail());
		}
		
		//Envio de email para empresa que vendeu o produto
		corpoEmail = new StringBuilder();
		corpoEmail.append("<h2>Venda de número " +vendaCompraLojaVirtual.getId()+ "</h2>");
		corpoEmail.append("<br>");
		corpoEmail.append("<p>O Cliente "+vendaCompraLojaVirtual.getPessoa().getNome()+"acabou de realizar uma compra em sua loja");
		corpoEmail.append("</p>");
		
		if(vendaCompraLojaVirtual.getEmpresa().getEmail() != null) {
			serviceSendEmail.enviarEmail("Venda da Loja Virtual", corpoEmail.toString(), vendaCompraLojaVirtual.getEmpresa().getEmail());
		}
		
		
		return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/consultaVendaId/{idVenda}")
	public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaId(@PathVariable("idVenda") Long idVenda) {

		VendaCompraLojaVirtual vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.findByIdExclusaoBanco(idVenda);
		
		if(vendaCompraLojaVirtual == null) {
			vendaCompraLojaVirtual = new VendaCompraLojaVirtual();
		}
		
		VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
		
		compraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
		compraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa());
		compraLojaVirtualDTO.setCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());
		compraLojaVirtualDTO.setEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
		compraLojaVirtualDTO.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
		compraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());
		compraLojaVirtualDTO.setId(vendaCompraLojaVirtual.getId());

		for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendas()) {

			ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
			itemVendaDTO.setProduto(item.getProduto());
			itemVendaDTO.setQuantidade(item.getQuantidade());
			
			compraLojaVirtualDTO.getItemVendas().add(itemVendaDTO);
		}

		return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/exclusaoTotalVenda/{idVenda}")
	public ResponseEntity<String> exclusaoTotalVenda(@PathVariable("idVenda") Long idVenda){
		
		vendaService.exclusaoTotalVenda(idVenda);
		
		return new ResponseEntity<String>("Venda removida com sucesso.",HttpStatus.OK);
	}
	
	@ResponseBody
	@PutMapping(value = "**/exclusaoLogicaVenda/{idVenda}")
	public ResponseEntity<String> exclusaoLogicaVenda(@PathVariable("idVenda") Long idVenda){
		
		vendaService.exclusaoLogicaVenda(idVenda);
		
		return new ResponseEntity<String>("Venda excluída com sucesso.", HttpStatus.OK);
	}
	
	@ResponseBody
	@PutMapping(value = "**/ativaVenda/{idVenda}")
	public ResponseEntity<String> ativaVenda(@PathVariable("idVenda")Long idVenda){
		
		vendaService.ativaVenda(idVenda);
		
		return new ResponseEntity<String>("Venda ativada com sucesso.", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaVendasPorProdutoId/{idProduto}")
	public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendasPorProdutoId(@PathVariable("idProduto") Long idProduto) {

		List<VendaCompraLojaVirtual> vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.consultaVendasPorProdutoId(idProduto);
		
		if(vendaCompraLojaVirtual == null) {
			vendaCompraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
		}
		
		List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();
		
		for (VendaCompraLojaVirtual vcl : vendaCompraLojaVirtual) {
			
			VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
			
			compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
			compraLojaVirtualDTO.setPessoa(vcl.getPessoa());
			compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());
			compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
			compraLojaVirtualDTO.setValorDesconto(vcl.getValorDesconto());
			compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
			compraLojaVirtualDTO.setId(vcl.getId());
			
			for (ItemVendaLoja item : vcl.getItemVendas()) {
				
				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setProduto(item.getProduto());
				itemVendaDTO.setQuantidade(item.getQuantidade());
				
				compraLojaVirtualDTO.getItemVendas().add(itemVendaDTO);
			}
			
			compraLojaVirtualDTOList.add(compraLojaVirtualDTO);
		}
		

		return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaVendaDinamicaFaixaData/{data1}/{data2}")
	public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendaDinamicaFaixaData(
					@PathVariable("data1") String data1, @PathVariable("data2") String data2) throws ParseException{
		
		List<VendaCompraLojaVirtual> vendaCompraLojaVirtual = null;
		
		vendaCompraLojaVirtual = vendaService.consultaVendaFaixaData(data1,data2);
		
		if(vendaCompraLojaVirtual == null) {
			
			vendaCompraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
		}
		
		List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();
		
		for (VendaCompraLojaVirtual vcl : vendaCompraLojaVirtual) {
			
			VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
			
			compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
			compraLojaVirtualDTO.setPessoa(vcl.getPessoa());
			compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());
			compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
			compraLojaVirtualDTO.setValorDesconto(vcl.getValorDesconto());
			compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
			compraLojaVirtualDTO.setId(vcl.getId());
			
			for (ItemVendaLoja item : vcl.getItemVendas()) {
				
				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setProduto(item.getProduto());
				itemVendaDTO.setQuantidade(item.getQuantidade());
				
				compraLojaVirtualDTO.getItemVendas().add(itemVendaDTO);
			}
			
			compraLojaVirtualDTOList.add(compraLojaVirtualDTO);
		}
		

		return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaVendaDinamica/{valor}/{tipoConsulta}")
	public ResponseEntity<List<VendaCompraLojaVirtualDTO>> 
			consultaVendaDinamica(@PathVariable("valor") String valor, @PathVariable("tipoConsulta") String tipoConsulta) {

		List<VendaCompraLojaVirtual> vendaCompraLojaVirtual = null; 
		
		if(tipoConsulta.equalsIgnoreCase("POR_ID_PROD")) {
			vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.consultaVendasPorProdutoId(Long.parseLong(valor));
		}
		else if(tipoConsulta.equalsIgnoreCase("POR_NOME_PROD")) {
			vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.vendaPorNomeProduto(valor.toUpperCase().trim());
		}
		else if(tipoConsulta.equalsIgnoreCase("POR_CPF_CLIENTE_LIKE")) {
			vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.vendaPorCpfClienteLike(valor);
		}
		else if(tipoConsulta.equalsIgnoreCase("POR_CPF_CLIENTE_IGUAL")) {
			vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.vendaPorCpfClienteIgual(valor);
		}
		else if(tipoConsulta.equalsIgnoreCase("POR_NOME_CLIENTE")) {
			vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.vendaPorNomeCliente(valor.toUpperCase().trim());
		}
		else if(tipoConsulta.equalsIgnoreCase("POR_ENDERECO_COBRANCA")) {
			vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.vendaPorEnderecoCobranca(valor.toUpperCase().trim());
		}
		else if(tipoConsulta.equalsIgnoreCase("POR_ENDERECO_ENTREGA")) {
			vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.vendaPorEnderecoEntrega(valor.toUpperCase().trim());
		}
		
		if(vendaCompraLojaVirtual == null) {
			vendaCompraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
		}
		
		List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();
		
		for (VendaCompraLojaVirtual vcl : vendaCompraLojaVirtual) {
			
			VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
			
			compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
			compraLojaVirtualDTO.setPessoa(vcl.getPessoa());
			compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());
			compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
			compraLojaVirtualDTO.setValorDesconto(vcl.getValorDesconto());
			compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
			compraLojaVirtualDTO.setId(vcl.getId());
			
			for (ItemVendaLoja item : vcl.getItemVendas()) {
				
				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setProduto(item.getProduto());
				itemVendaDTO.setQuantidade(item.getQuantidade());
				
				compraLojaVirtualDTO.getItemVendas().add(itemVendaDTO);
			}
			
			compraLojaVirtualDTOList.add(compraLojaVirtualDTO);
		}
		

		return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/vendasPorCliente/{idCliente}")
	public ResponseEntity<List<VendaCompraLojaVirtualDTO>> vendasPorCliente(@PathVariable("idCliente") Long idCliente) {

		List<VendaCompraLojaVirtual> vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.vendaPorCliente(idCliente);
		
		if(vendaCompraLojaVirtual == null) {
			vendaCompraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
		}
		
		List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();
		
		for (VendaCompraLojaVirtual vcl : vendaCompraLojaVirtual) {
			
			VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
			
			compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
			compraLojaVirtualDTO.setPessoa(vcl.getPessoa());
			compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());
			compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
			compraLojaVirtualDTO.setValorDesconto(vcl.getValorDesconto());
			compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
			compraLojaVirtualDTO.setId(vcl.getId());
			
			for (ItemVendaLoja item : vcl.getItemVendas()) {
				
				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setProduto(item.getProduto());
				itemVendaDTO.setQuantidade(item.getQuantidade());
				
				compraLojaVirtualDTO.getItemVendas().add(itemVendaDTO);
			}
			
			compraLojaVirtualDTOList.add(compraLojaVirtualDTO);
		}
		

		return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/calcular-frete")
	public ResponseEntity<List<EmpresaTransporteDTO>> calcularFrete(@RequestBody ConsultaFreteDTO consultaFreteDTO) throws IOException{
		
		List<EmpresaTransporteDTO> empresaTransporteDTOList = consultaFreteService.calcularFrete(consultaFreteDTO);
		
		return new ResponseEntity<List<EmpresaTransporteDTO>>(empresaTransporteDTOList, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/impreme-compra-etiqueta/{idVenda}")
	public ResponseEntity<String> imprimeCompraEtiquetaFrete(@PathVariable Long idVenda) throws ExceptionMentoriaJava, IOException{
		
		VendaCompraLojaVirtual vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.findById(idVenda).orElse(null);
	
		if(vendaCompraLojaVirtual == null) {
			 return new ResponseEntity<String>("Venda não encontrada", HttpStatus.OK);
		}
		
		List<Endereco> enderecos = enderecoRepository.enderecoPj(vendaCompraLojaVirtual.getEmpresa().getId());
		vendaCompraLojaVirtual.getEmpresa().setEnderecos(enderecos);
		
		EnvioEtiquetaDTO envioEtiquetaDTO = new EnvioEtiquetaDTO();
		envioEtiquetaDTO.setService(vendaCompraLojaVirtual.getServicoTransportadora());
		envioEtiquetaDTO.setAgency("49");
		envioEtiquetaDTO.getFrom().setName(vendaCompraLojaVirtual.getEmpresa().getNome());
		envioEtiquetaDTO.getFrom().setPhone(vendaCompraLojaVirtual.getEmpresa().getTelefone());
		envioEtiquetaDTO.getFrom().setEmail(vendaCompraLojaVirtual.getEmpresa().getEmail());
		envioEtiquetaDTO.getFrom().setCompany_document(vendaCompraLojaVirtual.getEmpresa().getCnpj());
		envioEtiquetaDTO.getFrom().setState_register(vendaCompraLojaVirtual.getEmpresa().getInscEstadual());
		envioEtiquetaDTO.getFrom().setAddress(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getLogradouro());
		envioEtiquetaDTO.getFrom().setComplement(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getComplemento());
		envioEtiquetaDTO.getFrom().setNumber(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getNumero());
		envioEtiquetaDTO.getFrom().setDistrict(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getBairro());
		envioEtiquetaDTO.getFrom().setCity(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getCidade());
		envioEtiquetaDTO.getFrom().setCountry_id("BR");
		envioEtiquetaDTO.getFrom().setPostal_code(vendaCompraLojaVirtual.getEmpresa().getEnderecos().get(0).getCep());
		envioEtiquetaDTO.getFrom().setNote("Qualquer coisa");
		
		
		envioEtiquetaDTO.getTo().setName(vendaCompraLojaVirtual.getPessoa().getNome());
		envioEtiquetaDTO.getTo().setPhone(vendaCompraLojaVirtual.getPessoa().getTelefone());
		envioEtiquetaDTO.getTo().setEmail(vendaCompraLojaVirtual.getPessoa().getEmail());
		envioEtiquetaDTO.getTo().setDocument(vendaCompraLojaVirtual.getPessoa().getCpf());
		envioEtiquetaDTO.getTo().setAddress("Endereço do destinatário");
		envioEtiquetaDTO.getTo().setComplement("Complemento");
		envioEtiquetaDTO.getTo().setNumber("2");
		envioEtiquetaDTO.getTo().setDistrict("Bairro");
		envioEtiquetaDTO.getTo().setCity("Porto Alegre");
		envioEtiquetaDTO.getTo().setState_abbr("RS");
		envioEtiquetaDTO.getTo().setCountry_id("BR");
		envioEtiquetaDTO.getTo().setPostal_code("90570020");
		envioEtiquetaDTO.getTo().setNote("Observação");
		
		List<ProductEnvioEtiquetaDTO> products = new ArrayList<ProductEnvioEtiquetaDTO>();
		
		for(ItemVendaLoja itemVendaLoja : vendaCompraLojaVirtual.getItemVendas()) {
			
			ProductEnvioEtiquetaDTO dto = new ProductEnvioEtiquetaDTO();
			dto.setName(itemVendaLoja.getProduto().getNome());
			dto.setQuantity(itemVendaLoja.getQuantidade().toString());
			dto.setUnitary_value("" + itemVendaLoja.getProduto().getValorVenda().doubleValue());
			
			products.add(dto);
		}
		
		envioEtiquetaDTO.setProducts(products);
		
		List<VolumeEnvioEtiquetaDTO> volumes = new ArrayList<VolumeEnvioEtiquetaDTO>();
		
		for(ItemVendaLoja itemVendaLoja : vendaCompraLojaVirtual.getItemVendas()) {
			
			VolumeEnvioEtiquetaDTO dto = new VolumeEnvioEtiquetaDTO();
			dto.setHeight(itemVendaLoja.getProduto().getAltura().toString());
			dto.setWidth(itemVendaLoja.getProduto().getLargura().toString());
			dto.setLength(itemVendaLoja.getProduto().getProfundidade().toString());
			dto.setWeight(itemVendaLoja.getProduto().getPeso().toString());
			
			volumes.add(dto);
		}
		
		envioEtiquetaDTO.setVolumes(volumes);
		
		envioEtiquetaDTO.getOptions().setInsurance_value("" + vendaCompraLojaVirtual.getValorTotal().doubleValue());
		envioEtiquetaDTO.getOptions().setReceipt(false);
		envioEtiquetaDTO.getOptions().setOwn_hand(false);
		envioEtiquetaDTO.getOptions().setReverse(false);
		envioEtiquetaDTO.getOptions().setNon_commercial(false);
		envioEtiquetaDTO.getOptions().getInvoice().setKey("31190307586261000184550010000092481404848162");
		envioEtiquetaDTO.getOptions().setPlataform(vendaCompraLojaVirtual.getEmpresa().getNomeFantasia());
		
		TagEnvioEtiquetaDTO tagEnvioEtiquetaDTO = new TagEnvioEtiquetaDTO();
		tagEnvioEtiquetaDTO.setTag("Identificação do pedido na plataforma, exemplo: "+ vendaCompraLojaVirtual.getId());
		tagEnvioEtiquetaDTO.setUrl(null);
		envioEtiquetaDTO.getOptions().getTags().add(tagEnvioEtiquetaDTO);
		
				ObjectMapper mapper = new ObjectMapper();
				
				String jsonEnvio = mapper.writeValueAsString(envioEtiquetaDTO);
		
				/*Insere etiqueta no carrinho*/
				okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().build();
				okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
				okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEnvio);
				okhttp3.Request request = new okhttp3.Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/cart")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				
				okhttp3.Response response = client.newCall(request).execute();
				
				String respostaJson = response.body().string();
				
				if(respostaJson.contains("error")) {
					throw new ExceptionMentoriaJava(respostaJson);
				}
				
				JsonNode jsonNode = new ObjectMapper().readTree(respostaJson);
				
				Iterator<JsonNode> iterator = jsonNode.iterator();
				
				String idEtiqueta = "";
				
				while(iterator.hasNext()) {
					JsonNode node = iterator.next();
					if(node.get("id") != null) {
						idEtiqueta = node.get("id").asText();	
					}else {
						idEtiqueta = node.asText();
					}
					
					break;
				}
				
				/*Salvando o código da etiqueta*/
				jdbcTemplate.execute("begin;update vd_cp_loja_virt set codigo_etiqueta = '"+idEtiqueta+"' where id = "+vendaCompraLojaVirtual.getId()+" ;commit;");
				//vd_Cp_Loja_VirtRepository.updateEtiqueta(idEtiqueta, vendaCompraLojaVirtual.getId());
				
				/*Compra da etiqueta*/
				okhttp3.OkHttpClient clientCompra = new okhttp3.OkHttpClient().newBuilder().build();
				okhttp3.MediaType mediaTypeC = okhttp3.MediaType.parse("application/json");
				okhttp3.RequestBody bodyC = okhttp3.RequestBody.create(mediaTypeC, "{\n    \"orders\": [\n        \""+idEtiqueta+"\n    ]\n}");
						okhttp3.Request requestC = new okhttp3.Request.Builder()
						  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX +"api/v2/me/shipment/checkout")
						  .method("POST", bodyC)
						  .addHeader("Accept", "application/json")
						  .addHeader("Content-Type", "application/json")
						  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
						  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
						  .build();
						
				okhttp3.Response responseC = clientCompra.newCall(requestC).execute();
			
				if(!responseC.isSuccessful()) {
					return new ResponseEntity<String>("Não foi possível realizar a compra da etiqueta", HttpStatus.OK);
				}
				
				/*Gera a etiqueta*/
				okhttp3.OkHttpClient clientGe = new okhttp3.OkHttpClient().newBuilder().build();
				okhttp3.MediaType mediaTypeGe = okhttp3.MediaType.parse("application/json");
				okhttp3.RequestBody bodyGe = okhttp3.RequestBody.create(mediaTypeGe, "{\n    \"orders\":[\n        \""+idEtiqueta+"\"\n    ]\n}");
				okhttp3.Request requestGe = new okhttp3.Request.Builder()
						  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/generate")
						  .method("POST", bodyGe)
						  .addHeader("Accept", "application/json")
						  .addHeader("Content-Type", "application/json")
						  .addHeader("Authorization", "Bearer "+ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
						  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
						  .build();
				okhttp3.Response responseGe = clientGe.newCall(requestGe).execute();
				
				if(!responseGe.isSuccessful()) {
					return new ResponseEntity<String>("Não foi possível gerar etiqueta", HttpStatus.OK);
				}
				
				/*Imprime a etiqueta*/
				okhttp3.OkHttpClient clientIm = new okhttp3.OkHttpClient().newBuilder().build();
				okhttp3.MediaType mediaTypeIm = okhttp3.MediaType.parse("application/json");
				okhttp3.RequestBody bodyIm = okhttp3.RequestBody.create(mediaTypeIm, "{\n    \"mode\": \"private\",\n    \"orders\": [\n        \""+idEtiqueta+"\"\n    ]\n}");
				okhttp3.Request requestIm = new okhttp3.Request.Builder()
						  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/print")
						  .method("POST", bodyIm)
						  .addHeader("Accept", "application/json")
						  .addHeader("Content-Type", "application/json")
						  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
						  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
						  .build();
				okhttp3.Response responseIm = clientIm.newCall(requestIm).execute();
		
				if(!responseIm.isSuccessful()) {
					return new ResponseEntity<String>("Não foi possível imprimir a etiqueta", HttpStatus.OK);
				}
				
				String urlEtiqueta = responseIm.body().string();
				
				/*Atualizando url da etiqueta direto no banco */
				jdbcTemplate.execute("begin;update vd_cp_loja_virt set url_imprime_etiqueta = '"+urlEtiqueta+"' where id = "+vendaCompraLojaVirtual.getId()+" ;commit;");
				//vd_Cp_Loja_VirtRepository.updateUrlEtiqueta(urlEtiqueta, vendaCompraLojaVirtual.getId());
				
		return new ResponseEntity<String>("Tudo pronto com a sua etiqueta", HttpStatus.OK);
	}
	
	

}
