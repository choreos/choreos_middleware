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

package org.ow2.petals.se.hello_component;

import javax.jbi.JBIException;

import org.ow2.petals.component.framework.se.AbstractServiceEngine;

/**
 * The petals-se-hello_component Service Engine.
 * 
 * <br>
 * <b>NOTE : </b>This class has to be used only if the component developer wants
 * to customize the main component class. In general, using the
 * org.objectweb.petals.component.framework.se.DefaultServiceEngine class is
 * enough. If so, change the value in the JBI descriptor file.
 * 
 * @author
 * 
 */
public class ServiceEngine extends AbstractServiceEngine {

    @Override
    protected void doInit() throws JBIException {
    	System.out.println("INICIOU A BAGA‚A");
    }

    @Override
    protected void doStart() throws JBIException {
    	System.out.println("DEU START NA BAGA‚A");
    }

    @Override
    protected void doStop() throws JBIException {
    	System.out.println("PAROU A BAGA‚A");
    }

    @Override
    protected void doShutdown() throws JBIException {
    	System.out.println("MATOU O ELEMENTO!");
    }
}
