package br.com.pamcary.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public final class HeaderUtil {

	private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

	private static final String APPLICATION_NAME = "ePamcaryBackendApp";

	private HeaderUtil() {
	}

	public static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-" + APPLICATION_NAME + "-alert", message);
		headers.add("X-" + APPLICATION_NAME + "-params", param);
		return headers;
	}

	public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
		return createAlert("Um novo " + entityName + " foi criado com o id " + param, param);
	}

	public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
		return createAlert("Um novo " + entityName + " foi alterado com o id " + param, param);
	}

	public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
		return createAlert("Um novo " + entityName + " foi deletado com o id " + param, param);
	}

	public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
		log.error("Entidade processada falhou, {}", defaultMessage);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-" + APPLICATION_NAME + "-error", defaultMessage);
		headers.add("X-" + APPLICATION_NAME + "-params", entityName);
		return headers;
	}
}
