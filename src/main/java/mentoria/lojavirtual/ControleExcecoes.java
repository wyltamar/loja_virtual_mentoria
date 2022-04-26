package mentoria.lojavirtual;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mentoria.lojavirtual.model.dto.ObjetoErroDTO;
import mentoria.lojavirtual.service.ServiceSendEmail;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {
	
	@Autowired
	private ServiceSendEmail serviceSendEmail; 
	
	@ExceptionHandler({ExceptionMentoriaJava.class})
	public ResponseEntity<Object> handleExceptionCustom(ExceptionMentoriaJava ex){
		
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		objetoErroDTO.setError(ex.getMessage());
		objetoErroDTO.setCode(HttpStatus.OK.toString());
		
		return new ResponseEntity<Object>(objetoErroDTO,HttpStatus.OK );
		
	}

	/* Captura as exceções do projeto */
	@ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

		String msg = "";

		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else if(ex instanceof HttpMessageNotReadableException){
				msg = "Não foi enviado dados no BODY - corpo da requisição";
		}else {
			msg += ex.getMessage();
		}

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(status.value() + "==>" + status.getReasonPhrase());

		ex.printStackTrace();
		try {
			serviceSendEmail.enviarEmail("Alerta de erro na Loja Virtual", ExceptionUtils.getStackTrace(ex), "wyltamar.dougladesousaoliveir@gmail.com");
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/* Captura erro na parte do banco */
	@ExceptionHandler({ DataIntegrityViolationException.class, 
			ConstraintViolationException.class, SQLException.class })
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

		String msg = "";

		if (ex instanceof SQLException) {

			msg = "Erro de SQL:" + ((SQLException) ex).getCause().getCause().getMessage();
		} else {
			ex.getMessage();
		}

		if (ex instanceof DataIntegrityViolationException) {

			msg = "Erro de integridade no banco:" + ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
		} else {
			ex.getMessage();
		}
		
		if (ex instanceof ConstraintViolationException) {

			msg = "Erro de Violação de chave estrangeira:" + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
		} else {
			ex.getMessage();
		}

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

		ex.printStackTrace();
		try {
			serviceSendEmail.enviarEmail("Alerta de erro na Loja Virtual", ExceptionUtils.getStackTrace(ex), "wyltamarjavadev@gmail.com");
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
