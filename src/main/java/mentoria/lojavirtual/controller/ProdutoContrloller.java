package mentoria.lojavirtual.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.Produto;
import mentoria.lojavirtual.repository.ProdutoRepository;
import mentoria.lojavirtual.service.ServiceSendEmail;

@Controller
@RestController
public class ProdutoContrloller {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@ResponseBody /*Para ser possível dar um retorno da API*/
	@PostMapping(value = "**/salvarProduto") /*Mapeando a url para receber JSON*/
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava, MessagingException, IOException { /*Recebe o JSON e converte para Objeto Java*/
		
		if(produto.getTipoUnidade() == null || produto.getTipoUnidade().trim().isEmpty()) {
			throw new ExceptionMentoriaJava("O Tipo da unidade deve ser informado. ");
		}
		
		if(produto.getNome().length() < 10) {
			throw new ExceptionMentoriaJava("O nome do produto deve conter no mínimo 10 letras.");
		}
		
		if(produto.getEmpresa().getId() == null || produto.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A empresa deve ser informada ");
		}
		
		if(produto.getId() == null ||produtoRepository.existeProduto(produto.getNome())) {
			
			List <Produto> produtos = produtoRepository.buscarProdutoNome(produto.getNome().toUpperCase(), 
									  produto.getEmpresa().getId());
			
			if(!produtos.isEmpty()) {
				
				throw new ExceptionMentoriaJava("Já existe produto com esse nome: "+produto.getNome());
			}
		}
		
		if(produto.getCategoriaProduto().getId() == null || produto.getCategoriaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("A Categoria do produto é um campo obrigatório ");
		}
		
		if(produto.getMarcaProduto().getId() == null || produto.getMarcaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("A Marca do produto é um campo obrigatório ");
		}
		
		if(produto.getQtdEstoque() < 1){
			throw new ExceptionMentoriaJava("O produto deve ter no mínimo 1 no estoque");
		}
		
		if(produto.getImagens() == null || produto.getImagens().isEmpty() || produto.getImagens().size() == 0) {
			throw new ExceptionMentoriaJava("É obirigatório informar imagens para o produto.");
		}
		
		if(produto.getImagens().size() < 3) {
			throw new ExceptionMentoriaJava("Deve ser informado pelo menos 3 imagens para o produto");
		}
		
		if(produto.getImagens().size() > 6) {
			throw new ExceptionMentoriaJava("Deve ser informado no máximo 6 imagens para o produto");
		}
		
		if(produto.getId() == null) {
			for(int x = 0; x < produto.getImagens().size(); x++) {
				produto.getImagens().get(x).setProduto(produto);
				produto.getImagens().get(x).setEmpresa(produto.getEmpresa());
				
				String base64Image = "";
				
				if(produto.getImagens().get(x).getImagemOriginal().contains("data:image")) {
					
					base64Image = produto.getImagens().get(x).getImagemOriginal().split(",")[1];
					
				}else {
					base64Image = produto.getImagens().get(x).getImagemOriginal();
				}
				
				byte[] imagesBytes = DatatypeConverter.parseBase64Binary(base64Image);
				
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagesBytes));
				
				if(bufferedImage != null) {
					
					int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
					int largura = Integer.parseInt("800");
					int altura = Integer.parseInt("600");
					
					BufferedImage resizedImage = new BufferedImage(largura, altura, type);
					Graphics2D g = resizedImage.createGraphics();
					g.drawImage(bufferedImage, 0, 0,  largura, altura, null);
					g.dispose();
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(resizedImage, "png", baos);
					
					String miniImgBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
					
					produto.getImagens().get(x).setImagemMiniatura(miniImgBase64);
					
					bufferedImage.flush();
					resizedImage.flush();
					baos.flush();
					baos.close();
				}
			}
		}
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		if(produto.getAlertaQtdEstoque() && produto.getQtdEstoque() <= 1) {
			
			StringBuilder html = new StringBuilder();
			html.append("<h2>")
			.append("Produto: "+ produto.getNome())
			.append(" com estoque baixo: "+ produto.getQtdAlertaEstoque())
			.append("</h2>");
			html.append("<p>").append("Id do produto:" + produto.getId()).append("<>/p");
			
			if(produto.getEmpresa().getEmail() != null) {
				
				serviceSendEmail.enviarEmail("Alerta de Estoque Baixo", html.toString(), produto.getEmpresa().getEmail());
				
			}
			
		}
		
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*Para ser possível dar um retorno da API*/
	@PostMapping(value = "**/deleteProduto") /*Mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteProduto(@RequestBody Produto produto) { /*Recebe o JSON e converte para Objeto Java*/
		
		produtoRepository.delete(produto);
		
		return new ResponseEntity<>("Produto excluido com suceso", HttpStatus.OK);
	}
	
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteProdutoPorId/{id}") 
	public ResponseEntity<?> deleteProdutoPorId(@PathVariable ("id") Long id) {
		
		produtoRepository.deleteById(id);
		
		return new ResponseEntity<>("Produto removido", HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/obterProduto/{id}")
	public ResponseEntity<Produto> obterProduto(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		Produto produto = produtoRepository.findById(id).orElse(null);
		
		if(produto == null) {
			throw new ExceptionMentoriaJava("Produto com código: "+id+" não existe em nossa base de dados");
		}
		
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/buscarProdNome/{desc}")
	public ResponseEntity<List<Produto>> buscarProdutoPorDesc(@PathVariable("desc") String desc) { 
		
		List <Produto> produtos = produtoRepository.buscarProdutoNome(desc.toUpperCase());
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	
}
