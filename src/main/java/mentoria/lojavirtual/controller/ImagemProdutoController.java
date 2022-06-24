package mentoria.lojavirtual.controller;

import java.util.ArrayList;
import java.util.List;

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
import mentoria.lojavirtual.model.ImagemProduto;
import mentoria.lojavirtual.model.dto.ImagemProdutoDTO;
import mentoria.lojavirtual.repository.ImagemProdutoRepository;

@RestController
public class ImagemProdutoController {
	
	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarImagemProduto")
	public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto){
		
		ImagemProduto imagemProdutoSalva = imagemProdutoRepository.saveAndFlush(imagemProduto);
		
		ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
		imagemProdutoDTO.setId(imagemProdutoSalva.getId());
		imagemProdutoDTO.setImagemMiniatura(imagemProdutoSalva.getImagemMiniatura());
		imagemProdutoDTO.setImagemOriginal(imagemProdutoSalva.getImagemOriginal());
		imagemProdutoDTO.setEmpresa(imagemProdutoSalva.getEmpresa().getId());
		imagemProdutoDTO.setProduto(imagemProdutoSalva.getProduto().getId());
		
		return new ResponseEntity<ImagemProdutoDTO>(imagemProdutoDTO, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteTodasImagesDoProduto/{idProduto}")
	public ResponseEntity<?> deleteTodasImagesDoProduto(@PathVariable("idProduto") Long idProduto){
		
		imagemProdutoRepository.deleteImagem(idProduto);
		
		return new ResponseEntity<String>("Imagems do produto removidas com sucesso",HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteImagem")
	public ResponseEntity<?> deleteImagem(@RequestBody ImagemProduto imagemProduto){
		
		if(!imagemProdutoRepository.existsById(imagemProduto.getId())) {
			return new ResponseEntity<String> ("Imagem com o id "+ imagemProduto.getId() +"já foi removida ou não existe.",HttpStatus.OK);
		}
		
		imagemProdutoRepository.delete(imagemProduto);
		
		return new ResponseEntity<>("Imgem removida com sucesso",HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteImagemProdutoPorId/{id}")
	public ResponseEntity<?> deleteImagemProdutoPorId(@PathVariable("id") Long id){
		
		if(!imagemProdutoRepository.existsById(id)){
			return new ResponseEntity<String> ("Imagem com o id "+ id +" já foi removida ou não existe.",HttpStatus.OK);
		}
		
		imagemProdutoRepository.deleteById(id);
		
		return new ResponseEntity<String>("Imagem removida com sucesso",HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterImagemProduto/{idProduto}")
	public ResponseEntity<List<ImagemProdutoDTO>> obterImagemProduto(@PathVariable("idProduto") Long idProduto)throws ExceptionMentoriaJava{
		
		List<ImagemProdutoDTO> dtos = new ArrayList<ImagemProdutoDTO>();
		
		List<ImagemProduto> imagemProdutos = imagemProdutoRepository.buscaImagemProduto(idProduto);
		
		if(imagemProdutos.isEmpty()) {
			throw new ExceptionMentoriaJava("Id do produto não encontrado em nossa base de dados");
		}
		
		for (ImagemProduto imagemProdutoSalva: imagemProdutos ) {
			
			ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
			imagemProdutoDTO.setId(imagemProdutoSalva.getId());
			imagemProdutoDTO.setImagemMiniatura(imagemProdutoSalva.getImagemMiniatura());
			imagemProdutoDTO.setImagemOriginal(imagemProdutoSalva.getImagemOriginal());
			imagemProdutoDTO.setEmpresa(imagemProdutoSalva.getEmpresa().getId());
			imagemProdutoDTO.setProduto(imagemProdutoSalva.getProduto().getId());
			
			dtos.add(imagemProdutoDTO);
		}
		
		return new ResponseEntity<List<ImagemProdutoDTO>>(dtos,HttpStatus.OK);
	}

}
