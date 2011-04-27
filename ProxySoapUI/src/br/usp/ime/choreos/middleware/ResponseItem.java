package br.usp.ime.choreos.middleware;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ResponseItem implements ResponseInterface{

        private String content;
        private String name;
        private HashMap<String, String> tagParameters;
        private HashMap<String, LinkedList<ResponseItem>>items;
        
        public ResponseItem(String tagName, HashMap<String, String> tagParameters) {
                this.name = tagName;
                this.tagParameters = tagParameters;
                items = new HashMap<String, LinkedList<ResponseItem>>();
        }

        public ResponseItem(String tagName) {
                this.name = tagName;
                items = new HashMap<String, LinkedList<ResponseItem>>();
        }

        public String getContent() {
                return content;
        }

        public Integer getContentAsInt() {
                        return Integer.parseInt(content);
        }

        public Double getContentAsDouble() {
                return Double.parseDouble(content);
        }
        
        public ResponseItem getAttr(String name) {
                return items.get(name).getFirst();
        }

        public List<ResponseItem> getAttrAsList(String name) throws NoSuchFieldException {
                if(items.get(name).size() == 1)
                        throw new NoSuchFieldException();
                return items.get(name);
        }

        public void addItem(ResponseItem item) {
                LinkedList<ResponseItem> currentItems = items.get(item.getName());
                
                if(currentItems == null)
                        currentItems = new LinkedList<ResponseItem>();
                
                currentItems.addLast(item);
                items.put(item.getName(), currentItems);
        }

        public void addContent(String content) {
               this.content = content;
                
        }

        public HashMap<String, String> getTagParameters() {
                return tagParameters;
        }
        
        public String getName(){
                return name;
        }

}
