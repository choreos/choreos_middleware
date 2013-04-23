package org.ow2.choreos.deployment.services.datamodel;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LegacyServiceSpec extends ServiceSpec {

	List<String> nativeURIs;

	protected LegacyServiceSpec(ServiceType serviceType, PackageType packageType) {
		super(serviceType, packageType);
	}
	
	public LegacyServiceSpec() {
		super();
	}

	
	public List<String> getNativeURIs() {
		return nativeURIs;
	}

	public void setNativeURIs(List<String> nativeURIs) {
		this.nativeURIs = nativeURIs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nativeURIs == null) ? 0 : nativeURIs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!super.equals(obj))
			return false;
		
		LegacyServiceSpec other = (LegacyServiceSpec) obj;
		
		if (nativeURIs == null) {
			if (other.nativeURIs != null)
				return false;
		} else if (!nativeURIs.equals(other.nativeURIs))
			return false;
		return true;
	}

	@Override
	public int getNumberOfInstances() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNumberOfInstances(int number) {
		// TODO Auto-generated method stub
		
	}
}