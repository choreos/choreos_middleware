package org.ow2.choreos.services.datamodel;

public class Recipe {

	private String name;
	private String cookbookName;
	private String cookbookFolder;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCookbookName() {
		return cookbookName;
	}
	public void setCookbookName(String cookbookName) {
		this.cookbookName = cookbookName;
	}
	public String getCookbookFolder() {
		return cookbookFolder;
	}
	public void setCookbookFolder(String cookbookFolder) {
		this.cookbookFolder = cookbookFolder;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cookbookName == null) ? 0 : cookbookName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Recipe other = (Recipe) obj;
		if (cookbookName == null) {
			if (other.cookbookName != null)
				return false;
		} else if (!cookbookName.equals(other.cookbookName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Recipe [name=" + name + ", cookbookName=" + cookbookName
				+ ", cookbookFolder=" + cookbookFolder + "]";
	}

}
