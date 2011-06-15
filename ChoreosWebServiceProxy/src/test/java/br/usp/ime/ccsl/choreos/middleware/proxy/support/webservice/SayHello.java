
package br.usp.ime.ccsl.choreos.middleware.proxy.support.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sayHello complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sayHello">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="yourName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHello", propOrder = {
    "yourName"
})
public class SayHello {

    protected String yourName;

    /**
     * Gets the value of the yourName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYourName() {
        return yourName;
    }

    /**
     * Sets the value of the yourName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYourName(String value) {
        this.yourName = value;
    }

}
