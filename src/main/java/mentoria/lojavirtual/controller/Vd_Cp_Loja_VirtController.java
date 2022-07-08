package mentoria.lojavirtual.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.Endereco;
import mentoria.lojavirtual.model.ItemVendaLoja;
import mentoria.lojavirtual.model.PessoaFisica;
import mentoria.lojavirtual.model.StatusRastreio;
import mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import mentoria.lojavirtual.model.dto.ItemVendaDTO;
import mentoria.lojavirtual.model.dto.VendaCompraLojaVirtualDTO;
import mentoria.lojavirtual.repository.EnderecoRepository;
import mentoria.lojavirtual.repository.NotaFiscalVendaRepository;
import mentoria.lojavirtual.repository.StatusRastreioRepository;
import mentoria.lojavirtual.repository.Vd_Cp_Loja_VirtRepository;
import mentoria.lojavirtual.service.VendaService;

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

	@ResponseBody
	@PostMapping(value = "**/salvarVendaLoja")
	public ResponseEntity<VendaCompraLojaVirtualDTO> salvarVendaLoja(
			@RequestBody @Valid VendaCompraLojaVirtual vendaCompraLojaVirtual) throws ExceptionMentoriaJava {

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

		return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/consultaVendaId/{id}")
	public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaId(@PathVariable("id") Long idVenda) {

		VendaCompraLojaVirtual vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.findById(idVenda)
				.orElse(new VendaCompraLojaVirtual());
		
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

}
