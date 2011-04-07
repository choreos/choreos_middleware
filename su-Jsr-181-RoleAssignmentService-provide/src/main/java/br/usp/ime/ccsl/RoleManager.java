package br.usp.ime.ccsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoleManager {

	private static RoleManager instance;
	
	Map<String, List<String>> uriRole;
	
	private RoleManager() {
		uriRole = new HashMap<String, List<String>>();
	}
	
	public static RoleManager getInstance() {
		if (instance == null) {
			instance = new RoleManager();
		}
		return instance;
	}
	
	public void assignRole(String uri, String roleName) {
		List<String> uriList = uriRole.get(roleName);
		if (uriList == null) {
			uriList = new ArrayList<String>();
			uriRole.put(roleName, uriList);
		}
		uriList.add(uri);
	}
	
	public List<String> getUriList(String roleName) {
		List<String> uriList = uriRole.get(roleName);
		if (uriList == null) uriList = new ArrayList<String>();
		return uriList;
	}
}

