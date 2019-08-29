package br.com.pamcary.exception;

import java.util.Date;

public class ExceptionResponse extends RuntimeException {

	private static final long serialVersionUID = -9039412616899180781L;

	public ExceptionResponse(Date date, String msg, String details) {
		super();
		this.date = date;
		this.msg = msg;
		this.details = details;
	}

	private Date date;
	private String msg;
	private String details;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}