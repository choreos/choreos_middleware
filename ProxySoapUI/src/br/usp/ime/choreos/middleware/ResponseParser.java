package br.usp.ime.choreos.middleware;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Utility class to parse the Soap XML response of a Web Service operation
 * Obs: it's a package visibility class, since it's not used by the client
 * 
 * @author leonardo, guilherme
 *
 */
class ResponseParser {
	
	private static SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	private SAXParser parser;
	private StringBuilder result = new StringBuilder("");

	
	private class ResponseParserHandler extends DefaultHandler {
		private String opName;
		private boolean processing;
		private Stack<String> tagStack = new Stack<String>();

		/**
		 * @param ch     - The characters.
		 * @param start  - The start position in the character array.
		 * @param length - The number of characters to use from the character array.
		 */
		@Override
		public void characters(char[] ch, int start, int lenght) 
			throws SAXException {
			
			String trimmed = new String(ch, start, lenght).trim();
			
			if (processing && !trimmed.isEmpty()){
				result.append(tagStack.peek() + ".setContent()").append("\n");
			}
		}

		/**
		 * @param uri       - The Namespace URI.
		 * @param localName - The local name (without prefix).
		 * @param qName     - The qualified name (with prefix).
		 */
		@Override
		public void endElement(String uri, String localName, String qName)
			throws SAXException {
			
			//deletes namespace
			String name = qName.split(":")[1];
			
			if (processing) {
				if (name.equals(opName)){
					processing = false;
				} else {
					String poped = tagStack.pop();
					if (!tagStack.empty()){
						String father = tagStack.peek();
						result.append(father + ".add(" + poped + ")").append("\n");
					}
					result.append("stack.pop()").append("\n");
				}
			}
			
		}

		/**
		 *	@param uri        - The Namespace URI.
		 *	@param localName  - The local name (without prefix).
		 *	@param qName      - The qualified name (with prefix).
		 *	@param attributes - The attributes attached to the element.
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) 
			throws SAXException {
			
			//deletes namespace
			String name = qName.split(":")[1];
			
			if (name.endsWith("Response") && !processing) {
				opName = name;
				processing = true;
			} 
			else if (processing) {
				result.append("create: ResponseItem(" + name + ")").append("\n");
				result.append("stack.push(" + name + ")").append("\n");
				tagStack.push(name);
			}
			
		}

	}
	
	public String parse(String xml) {
		try {
	        InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			parser = parserFactory.newSAXParser();
			parser.parse(is, new ResponseParserHandler());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result.toString());
		
		return result.toString();
	}

}
