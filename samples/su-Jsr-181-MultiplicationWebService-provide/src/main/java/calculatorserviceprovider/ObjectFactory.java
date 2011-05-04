
package calculatorserviceprovider;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the calculatorserviceprovider package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OperationAddResponse_QNAME = new QName("http://CalculatorServiceProvider/", "operationAddResponse");
    private final static QName _OperationAdd_QNAME = new QName("http://CalculatorServiceProvider/", "operationAdd");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: calculatorserviceprovider
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OperationAddResponse }
     * 
     */
    public OperationAddResponse createOperationAddResponse() {
        return new OperationAddResponse();
    }

    /**
     * Create an instance of {@link OperationAdd }
     * 
     */
    public OperationAdd createOperationAdd() {
        return new OperationAdd();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationAddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculatorServiceProvider/", name = "operationAddResponse")
    public JAXBElement<OperationAddResponse> createOperationAddResponse(OperationAddResponse value) {
        return new JAXBElement<OperationAddResponse>(_OperationAddResponse_QNAME, OperationAddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationAdd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculatorServiceProvider/", name = "operationAdd")
    public JAXBElement<OperationAdd> createOperationAdd(OperationAdd value) {
        return new JAXBElement<OperationAdd>(_OperationAdd_QNAME, OperationAdd.class, null, value);
    }

}
