
package calculatorserviceprovider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operationAdd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="operationAdd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="term1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="term2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operationAdd", propOrder = {
    "term1",
    "term2"
})
public class OperationAdd {

    protected int term1;
    protected int term2;

    /**
     * Gets the value of the term1 property.
     * 
     */
    public int getTerm1() {
        return term1;
    }

    /**
     * Sets the value of the term1 property.
     * 
     */
    public void setTerm1(int value) {
        this.term1 = value;
    }

    /**
     * Gets the value of the term2 property.
     * 
     */
    public int getTerm2() {
        return term2;
    }

    /**
     * Sets the value of the term2 property.
     * 
     */
    public void setTerm2(int value) {
        this.term2 = value;
    }

}
