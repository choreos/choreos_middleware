package br.usp.ime.ccsl.choreos.middleware.exceptions;

import br.usp.ime.choreos.vv.exceptions.FrameworkException;

public class WSDLException extends FrameworkException {

	private static final long serialVersionUID = 2726146634585392449L;

	public WSDLException(Exception originalException) {
		super(originalException);
	}

}
