
package multiplication.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the multiplication.webservice package. 
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

    private final static QName _OperationMultiplyResponse_QNAME = new QName("http://webservice.multiplication/", "operationMultiplyResponse");
    private final static QName _OperationMultiply_QNAME = new QName("http://webservice.multiplication/", "operationMultiply");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: multiplication.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OperationMultiply }
     * 
     */
    public OperationMultiply createOperationMultiply() {
        return new OperationMultiply();
    }

    /**
     * Create an instance of {@link OperationMultiplyResponse }
     * 
     */
    public OperationMultiplyResponse createOperationMultiplyResponse() {
        return new OperationMultiplyResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationMultiplyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.multiplication/", name = "operationMultiplyResponse")
    public JAXBElement<OperationMultiplyResponse> createOperationMultiplyResponse(OperationMultiplyResponse value) {
        return new JAXBElement<OperationMultiplyResponse>(_OperationMultiplyResponse_QNAME, OperationMultiplyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationMultiply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.multiplication/", name = "operationMultiply")
    public JAXBElement<OperationMultiply> createOperationMultiply(OperationMultiply value) {
        return new JAXBElement<OperationMultiply>(_OperationMultiply_QNAME, OperationMultiply.class, null, value);
    }

}
