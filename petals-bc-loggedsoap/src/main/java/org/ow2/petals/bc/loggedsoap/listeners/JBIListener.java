/**
 * PETALS - PETALS Services Platform.
 * Copyright (c) 2007 EBM Websourcing, http://www.ebmwebsourcing.com/
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.ow2.petals.bc.loggedsoap.listeners;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.jbi.messaging.MessagingException;

import javax.jbi.messaging.MessagingException;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.petals.component.framework.api.exception.PEtALSCDKException;
import org.ow2.petals.component.framework.api.message.Exchange;
import org.ow2.petals.component.framework.listener.AbstractJBIListener;
import org.ow2.petals.component.framework.util.UtilFactory;

/**
 * @author
 * 
 */
public class JBIListener extends AbstractJBIListener {

    private static final Logger log = Logger.getLogger(JBIListener.class
	    .getName());

    @Override
    public Logger getLogger() {
	return log;
    }

    /**
     * Creates a new instance of {@link JBIListener}
     * 
     */
    public JBIListener() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ow2.petals.component.framework.listener.AbstractJBIListener
     */
    public boolean onJBIMessage(final Exchange exchange) {
	try {
	    if (component.getNativeWsdl() != null
		    && UtilFactory.getWSDLUtil().isDescriptionContaining(
			    component.getNativeWsdl().getDescription(),
			    exchange.getEndpoint())) {
		getLogger().info("exchange to process internally received");
	    } else {
		getLogger().info(
			"exchange to process with the SU manager received");
	    }
	    getLogger().info("exchange endpoint name: " + exchange.getEndpointName());
	    getLogger().info("exchange operation name: " + exchange.getOperationName());
	    System.out.println("teste-muito-antes");
	    if (exchange.getFault() != null) {
		System.out.println("teste-out");
		if (UtilFactory.getExchangeUtil().isPetalsException(exchange.getFault())) {
		    System.out.println("teste-in");
		    getLogger().info(
			    "exchange technical fault message content: "
				    + UtilFactory.getSourceUtil().createString(exchange.getFault().getContent()));
		} else {
		    System.out.println("teste-antes");
		    getLogger().info(
			    "exchange business fault message content: "
				    + UtilFactory.getSourceUtil().createString(exchange.getFault().getContent()));
		    System.out.println("teste-depois");
		}
	    } else {
		getLogger().info(
			"exchange in message content: "
				+ UtilFactory.getSourceUtil().createString(exchange.getInMessage().getContent()));
	    }
	    if (exchange.isInOutPattern() || exchange.isInOptionalOutPattern()) {
		exchange.getOutMessage().setContent(
			UtilFactory.getSourceUtil().createSource("<helloworld>Hello World!</helloworld>"));
	    }
	    
	    getLogger().info("Log generated successfully");
	} catch (Exception e) {
	    exchange.setError(new Exception(
		    "An error occurs during the treatment of an exchange: "
			    + e.getMessage()));
	}
	return true;
    }
}
