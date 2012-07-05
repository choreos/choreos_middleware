package org.ow2.choreos.npm.datamodel;

public class Node {

	private String id;
	private Integer cpus;
	private Integer ram;
	private Integer storage;
	private String so;
	private String zone;
	private String ip;
	private String hostname;
	private String user;
	private String privateKeyFile;
	private String image;
	private Integer state;
	private String chefName; // nos comandos do chef é o NODE_NAME

	public Node() {
	}

	public Node(NodeRestRepresentation rest) {
		cpus = rest.cpus;
		hostname = rest.hostname;
		id = rest.id;
		image = rest.image;
		ip = rest.ip;
		ram = rest.ram;
		storage = rest.storage;
		so = rest.so;
		zone = rest.zone;
		state = rest.state;
	}

	public NodeRestRepresentation getRestRepresentation() {
		NodeRestRepresentation rest = new NodeRestRepresentation();

		rest.cpus = cpus;
		rest.hostname = hostname;
		rest.id = id;
		rest.image = image;
		rest.ip = ip;
		rest.ram = ram;
		rest.storage = storage;
		rest.so = so;
		rest.zone = zone;
		rest.state = state;
		
		return rest;
	}

	
	public String getChefName() {
		return chefName;
	}

	public void setChefName(String chefName) {
		this.chefName = chefName;
	}

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCpus() {
		return cpus;
	}

	public void setCpus(Integer cpus) {
		this.cpus = cpus;
	}

	public Integer getRam() {
		return ram;
	}

	public void setRam(Integer ram) {
		this.ram = ram;
	}

	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public String getSo() {
		return so;
	}

	public void setSo(String so) {
		this.so = so;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPrivateKeyFile() {
		return privateKeyFile;
	}

	public void setPrivateKey(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chefName == null) ? 0 : chefName.hashCode());
		result = prime * result + ((cpus == null) ? 0 : cpus.hashCode());
		result = prime * result
				+ ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((privateKeyFile == null) ? 0 : privateKeyFile.hashCode());
		result = prime * result + ((ram == null) ? 0 : ram.hashCode());
		result = prime * result + ((so == null) ? 0 : so.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((storage == null) ? 0 : storage.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
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
		Node other = (Node) obj;
		if (chefName == null) {
			if (other.chefName != null)
				return false;
		} else if (!chefName.equals(other.chefName))
			return false;
		if (cpus == null) {
			if (other.cpus != null)
				return false;
		} else if (!cpus.equals(other.cpus))
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (privateKeyFile == null) {
			if (other.privateKeyFile != null)
				return false;
		} else if (!privateKeyFile.equals(other.privateKeyFile))
			return false;
		if (ram == null) {
			if (other.ram != null)
				return false;
		} else if (!ram.equals(other.ram))
			return false;
		if (so == null) {
			if (other.so != null)
				return false;
		} else if (!so.equals(other.so))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (storage == null) {
			if (other.storage != null)
				return false;
		} else if (!storage.equals(other.storage))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", ip=" + ip + ", hostname=" + hostname
				+ ", chefName=" + chefName + "]";
	}
	
}
