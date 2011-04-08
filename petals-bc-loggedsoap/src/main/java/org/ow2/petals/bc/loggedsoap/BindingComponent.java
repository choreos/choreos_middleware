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

package org.ow2.petals.bc.loggedsoap;

import javax.jbi.JBIException;

import org.ow2.petals.component.framework.bc.AbstractBindingComponent;

/**
 * The petals-bc-loggedsoap Binding Component.
 * 
 * <br>
 * <b>NOTE : </b>This class has to be used only if the component developper
 * wants to customize the main component class. In general, using the
 * org.objectweb.petals.component.framework.bc.DefaultBindingComponent class is
 * enough. If so, change the value in the JBI descriptor file.
 * 
 * @author
 * 
 */
public class BindingComponent extends AbstractBindingComponent {

    /*
     * (non-Javadoc)
     * 
     * @see org.ow2.petals.component.framework.AbstractComponent
     */
    @Override
    protected void doInit() throws JBIException {
        // TODO implement the specific initialisation of the component
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ow2.petals.component.framework.AbstractComponent
     */
    @Override
    protected void doStart() throws JBIException {
        // TODO implement the specific start of the component
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ow2.petals.component.framework.AbstractComponent
     */
    @Override
    protected void doStop() throws JBIException {
        // TODO implement the specific stop of the component
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ow2.petals.component.framework.AbstractComponent
     */
    @Override
    protected void doShutdown() throws JBIException {
        // TODO implement the specific shutdown of the component
    }
}
