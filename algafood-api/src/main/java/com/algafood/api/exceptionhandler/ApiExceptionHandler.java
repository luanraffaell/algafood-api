package com.algafood.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
//		Throwable rootCause2 = ex;
//		rootCause = ex.getCause();
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException)rootCause, headers,status,request);
		}else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException)rootCause,headers,status,request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está invalido. Verifique erro de sintaxe.";
		Problem problem	= createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		String details = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
			ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
			String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
			String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido"
					+". Corrija e informe um valor compatível com o tipo '%s'.", path,ex.getValue(),ex.getTargetType().getSimpleName());
			
			Problem problem	= createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEstadoNaoEncontradoException(EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		Problem problem	= createProblemBuilder(status, problemType, ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	// método já tratado pelo ResponseEntityExceptionHandler
//	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//	public ResponseEntity<?> tratarHttpMediTypeSupportedException(){
//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem("O tipo de midia não é suportado!").build();
//		
//		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
//	}
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
}