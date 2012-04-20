 /*
  * GLIMPSE: A generic and flexible monitoring infrastructure.
  * For further information: http://labsewiki.isti.cnr.it/labse/tools/glimpse/public/main
  * 
  * Copyright (C) 2011  Software Engineering Laboratory - ISTI CNR - Pisa - Italy
  * 
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * 
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * 
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  * 
*/

package it.cnr.isti.labse.glimpse.event;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;

/**
 * 
 * This class is a possible implementation (extension) of the {@link GlimpseBaseEvent} class.<br /><br /> 
 * This implementation refer to a CONNECT project scenario.
 * 
 * @author Antonello Calabr&ograve;
 * @version 3.2
 */
public class GlimpseBaseEventImpl<T> extends GlimpseBaseEvent<T>
{
	private static final long serialVersionUID = 1L;
	public Long timestamp = 0L;
	private T data;
	private boolean consumed;
	private String connectorID;
	private String connectorInstanceID;
	private String connectorInstanceExecutionID;
	private int eventID;
	private int eventInResponseToID;
	private String sourceState;
	private String networkedSystemSource;
	private String eventName;
	private boolean isException;
	
	/**
	 * @param eventName the name of the event that will be fired
	 * @param connectorID the CONNECTor ID from which the event is passing through
	 * @param connectorInstanceID the identificator of a specific instance of the CONNECTor
	 * @param connectorInstanceExecutionID the execution identifier of an instance
	 * @param eventID an identificator of the event
	 * @param eventInResponseToID cause/effect, this value provide the id of the event from which this event is effect/cause
	 * @param ts the timestamp if the event 
	 * @param networkedSystemSource the networked system that generate this event before pass through the CONNECTor
	 * @param isException is true if the event is an Exception, the payload of this kind of event is a {@link ComplexEventException} object.
	 */
	public GlimpseBaseEventImpl(String eventName, String connectorID, String connectorInstanceID, String connectorInstanceExecutionID, int eventID, int eventInResponseToID, Long ts, String networkedSystemSource, boolean isException)
	{
		super(eventName, connectorID, connectorInstanceID, connectorInstanceExecutionID, eventID, eventInResponseToID, ts, networkedSystemSource, isException);
		this.connectorID = connectorID;
		this.connectorInstanceID = connectorInstanceID;
		this.connectorInstanceExecutionID = connectorInstanceExecutionID;
		this.eventID = eventID;
		this.eventInResponseToID = eventInResponseToID;
		this.timestamp = ts;
		this.networkedSystemSource = networkedSystemSource;
		this.eventName = eventName;
		this.isException = isException;
	}

	public T getData() {
		return this.data;
	}
	
	public void setData(T t) {
		this.data = t;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public boolean getConsumed() {
		return this.consumed;
	}

	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}
	
	public String getSourceState() {
		return this.sourceState;
	}
	
	public void setSourceState(String sourceState) {
		this.sourceState = sourceState;
	}
	
	public String getConnectorID() {
		return this.connectorID;
	}
	
	public void setConnectorID(String connectorID) {
		this.connectorID = connectorID;		
	}

	public String getConnectorInstanceID() {
		return this.connectorInstanceID;
	}

	public void setConnectorInstanceID(String connectorInstanceID) {
		this.connectorInstanceID = connectorInstanceID;	
	}
	
	public String getConnectorInstanceExecutionID(){
		return this.connectorInstanceExecutionID;
	}
	
	public void setConnectorInstanceExecutionID(String connectorInstanceExecutionID){
		this.connectorInstanceExecutionID = connectorInstanceExecutionID;
	}

	public String getNetworkedSystemSource() {
		return networkedSystemSource;
	}

	@Override
	public void setNetworkedSystemSource(String networkedSystemSource) {
		this.networkedSystemSource = networkedSystemSource;
	}

	@Override
	public int getEventID() {
		return this.eventID;
	}

	@Override
	public void setEventID(int eventID) {
		this.eventID = eventID;		
	}

	@Override
	public int getEventInResponseToID() {
		return this.eventInResponseToID;
	}

	@Override
	public void setEventInResponseToID(int eventInResponseToID) {
		this.eventInResponseToID = eventInResponseToID;		
	}

	@Override
	public String getName() {
		return this.eventName;
	}

	@Override
	public void setName(String eventName) {
		this.eventName = eventName;		
	}

	@Override
	public boolean getIsException() {
		return this.isException;
	}

	@Override
	public void setIsException(boolean isException) {
		this.isException = isException;
		
	}
}
