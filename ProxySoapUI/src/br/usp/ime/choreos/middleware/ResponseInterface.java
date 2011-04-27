package br.usp.ime.choreos.middleware;

import java.util.HashMap;
import java.util.List;

public interface ResponseInterface {
        
        public String getContent();
        public Integer  getContentAsInt();
        public Double  getContentAsDouble();
        public ResponseItem getAttr(String name);
        public List<ResponseItem> getAttrAsList(String name) throws NoSuchFieldException;
        public void addItem(ResponseItem item);
        public void addContent(String content);
        public HashMap<String, String> getTagParameters();

}
