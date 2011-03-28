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

package org.ow2.petals.se.hello_component.listener;

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
     * @see org.ow2.petals.component.framework.listener.AbstractJBIListener#onJBIMessage(org.objectweb.petals.component.framework.util.Exchange)
     */
    public boolean onJBIMessage(final Exchange exchange) {
    	System.out.println("VEIO ALGUMA COISA!!!");
        try {
            if (component.getNativeWsdl() != null
                    && UtilFactory.getWSDLUtil().isDescriptionContaining(
                component.getNativeWsdl().getDescription(), exchange.getEndpoint())) {
                getLogger().info("exchange to process internally received");
            } else {
                getLogger().info("exchange to process with the SU manager received");
            }
            getLogger().info("exchange endpoint name: " + exchange.getEndpointName());
            getLogger().info("exchange operation name: " + exchange.getOperationName());
            if (exchange.getFault() != null) { 
                if (UtilFactory.getExchangeUtil().isPetalsException(exchange.getFault())) { 
                    getLogger().info("exchange technical fault message content: " +
                        UtilFactory.getSourceUtil().createString(exchange.getFault().getContent())); 
                } else {
                    getLogger().info( "exchange business fault message content: " +
                        UtilFactory.getSourceUtil().createString(exchange.getFault().getContent()));
                }
            } else {
                getLogger().info( "exchange in message content: " +
                    UtilFactory.getSourceUtil().createString(exchange.getInMessage().getContent()));
            }
            if(exchange.isInOutPattern() || exchange.isInOptionalOutPattern()) {
                exchange.getOutMessage().setContent(
                        UtilFactory.getSourceUtil().createSource("<helloworld>Hello World!</helloworld>"));
            }
        } catch (Exception e) {
            exchange.setError(new Exception("An error occurs during the treatment of an exchange: " + e.getMessage()));
        }
        return true;
    }
}

