import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class XMLReaderTest {
	
	 public static void main(String[] args) throws IOException {
	        XMLReaderTest xml = new XMLReaderTest();
	        try {
	            // Reads text from a character-input stream
	            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	 
	            // Defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML documents.
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	 
	            // The Document interface represents the entire HTML or XML document. Conceptually, it is the root of the document tree, and provides the primary access to the document's data.
	            Document doc = factory.newDocumentBuilder().parse(xml.getPath());
	            
	            // Get input element from user
	            System.out.print("Enter element name: ");
	            String element = reader.readLine();
	            
	            // Returns a NodeList of all the Elements in document order with a given tag name and are contained in the document.
	            NodeList nodes = doc.getElementsByTagName(element);
	            System.out.println("\nHere you go => Total # of Elements: " + nodes.getLength());
	            for(int i=0;i<nodes.getLength();i++) {
	            	System.out.println(nodes.item(i).getAttributes().item(0));
	            }
	            
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	 
	 public XMLReaderTest() {
		 
	 }
	 
	 public File getPath() {
		 return new File(getClass().getResource("itempools.xml").getFile());
	 }
}
