package br.com.pamcary.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.pamcary.service.PessoaService;

@ControllerAdvice
public class ExceptionHandlerSpring {
	
	private final Logger log = LoggerFactory.getLogger(PessoaService.class);
    
    @ExceptionHandler({PessoaNotFoundException.class})
    private ResponseEntity<List<Error>>handleNotFoundException(PessoaNotFoundException e) {
        return error(NOT_FOUND, e,null);
    }
    
    @ExceptionHandler({CpfEmUsoException.class})
    private ResponseEntity<List<Error>>handleNotFoundException(CpfEmUsoException e) {
        return error(BAD_REQUEST, e,null);
    }
    
    @ExceptionHandler({BadRequestAlertException.class})
    private ResponseEntity<List<Error>>handleNotFoundException(BadRequestAlertException e) {
        return error(BAD_REQUEST, e,null);
    }
    
    @ExceptionHandler({HttpMessageNotReadableException.class})
    private ResponseEntity<List<Error>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return error(BAD_REQUEST, e, "Payload inválido, consulte documentação.");
    }
    
    @ExceptionHandler({MethodArgumentNotValidException.class})
    private ResponseEntity<List<Error>> handleHttpMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return error(BAD_REQUEST, e, "Argumento inválido.");
    }
    
    @ExceptionHandler({SQLException.class})
    private ResponseEntity<List<Error>> handleSQLException(SQLException e) {
        return error(BAD_REQUEST, e, "Erro ao executar SQL.");
    }
    
    @ExceptionHandler({DataIntegrityViolationException.class})
    private ResponseEntity<List<Error>> handleSQLException(DataIntegrityViolationException e) {
        return error(BAD_REQUEST, e, "Erro violação de integridade SQL.");
    }
    
    @ExceptionHandler({RuntimeException.class})
    private ResponseEntity<List<Error>> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e,null);
    }
    
    @ExceptionHandler({Exception.class})
    private ResponseEntity<List<Error>> handleException(Exception e) {
        return error(INTERNAL_SERVER_ERROR, e,null);
    }
    
    private ResponseEntity<List<Error>> error(HttpStatus status, Exception e, String msg) {
        log.error("Exception : ", e);
		List<Error> listErros = createListError(e,msg);
        return ResponseEntity.status(status).body(listErros);
    }
    
	private List<Error> createListError(Exception ex, String msg) {
		List<Error> listErros = new ArrayList<>();
		String msgUser = msg != null ? msg : ex.getMessage();
		String msgDeveloper = ex.toString();
		listErros.add(new Error(msgUser, msgDeveloper));

		return listErros;
	}
    
	public static class Error {

		public Error(final String msgUser, final String msgDeveloper) {
			this.msgUser = msgUser;
			this.msgDeveloper = msgDeveloper;
		}

		private String msgUser;
		private String msgDeveloper;

		public String getMsgUser() {
			return msgUser;
		}

		public void setMsgUser(String msgUser) {
			this.msgUser = msgUser;
		}

		public String getMsgDeveloper() {
			return msgDeveloper;
		}

		public void setMsgDeveloper(String msgDeveloper) {
			this.msgDeveloper = msgDeveloper;
		}

	}
}