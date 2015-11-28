import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Code {
	
	ArrayList<String> defaultCode = new ArrayList<String>();
	HashMap<String, Integer> itemAndIds;
	HashMap<Integer, String> idsAndItem;
	ArrayList<String> items;
	ArrayList<String> itemsids;
	String path;
	
	public Code() throws IOException {
		itemAndIds = new HashMap<String, Integer>();
		idsAndItem = new HashMap<Integer, String>();
		items = getItems();
		itemsids = getItemIds();
		for(int i=0;i<items.size();i++) {
			itemAndIds.put(items.get(i), Integer.parseInt(itemsids.get(i)));
			idsAndItem.put(Integer.parseInt(itemsids.get(i)), items.get(i));
		}
	}
	
	public Code(String paths) throws IOException {
		path = paths;
		itemAndIds = new HashMap<String, Integer>();
		idsAndItem = new HashMap<Integer, String>();
		items = getItems();
		itemsids = getItemIds();
		for(int i=0;i<items.size();i++) {
			itemAndIds.put(items.get(i), Integer.parseInt(itemsids.get(i)));
			idsAndItem.put(Integer.parseInt(itemsids.get(i)), items.get(i));
		}
	}
	
	public ArrayList<String> getItemList() {
		return items;
	}
	
	public ArrayList<String> getItems() throws IOException {
		ArrayList<String> al = getText("items");
		
		for(int i=0;i<al.size();i++) {
			String s = al.remove(i);
			int ind = s.indexOf("name=\"")+6;
			s = s.substring(ind, s.indexOf("\"", ind));
			al.add(i, s);
		}

		return al;
	}
	
	public ArrayList<String> getItemIds() throws IOException {
		ArrayList<String> id = getText("items");
		
		for(int i=id.size()-1;i>=0;i--) {
			String s = id.remove(i);
			int ind = s.indexOf("id=\"")+4;
			s = s.substring(ind, s.indexOf("\"", ind));
			id.add(i, s);
		}
		
		return id;
	}
	
	public ArrayList<String> getText(String list) throws IOException {
		ArrayList<String> al = new ArrayList<String>();
		InputStream is = getClass().getResourceAsStream(list+".txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			try {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	// process the line.
			    	al.add(line);
			    }
			    
			    
		} catch(IOException e) {
			System.out.print(e);
		}
		br.close();
		return al;
	}
	
	public void fillPools(ArrayList<String> al, DefaultListModel<?> dlm) {
		//writeXML(al, dlm);
		for(int i=0;i<dlm.size();i++) {
			//al.add("<Item DecreaseBy=\"1\" Id=\""+itemAndIds.getOrDefault(dlm.getElementAt(i), 1)+"\" RemoveOn=\"0.1\" Weight=\"1\" />");	
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultListModel<?> saveXML1(String name) throws ParserConfigurationException {
		DefaultListModel dlm = new DefaultListModel();
		File fXmlFile = new File(getClass().getResource("itempools.xml").getFile());
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = null;
	    try {
	        doc = dBuilder.parse(fXmlFile);
	    } catch (SAXException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    doc.getDocumentElement().normalize();

	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	    NodeList nList = doc.getElementsByTagName("Pool");

	    System.out.println("----------------------------");

	    for (int temp = 0; temp < nList.getLength(); temp++) {

	        Node nNode = nList.item(temp);

	        System.out.println("\nCurrent Element :" + nNode.getNodeName() + " " + nNode.getAttributes().getNamedItem("Name").getNodeValue());
	        
	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	            Element eElement = (Element) nNode;
	            //System.out.println(eElement.getAttribute("Name"));
	            //System.out.println("Item : " + eElement.getElementsByTagName("Item").item(0).getTextContent());
	            //System.out.println("Item Id : " + eElement.getElementsByTagName("Item").item(0).getAttributes().getNamedItem("Id").getNodeValue());
	            //System.out.println("Item Id : " + idsAndItem.get(new Integer(eElement.getElementsByTagName("Item").item(0).getAttributes().getNamedItem("Id").getNodeValue())));
	            for(int i=0;i<eElement.getElementsByTagName("Item").getLength();i++) {
	            	System.out.println("Item Id : " + eElement.getElementsByTagName("Item").item(i).getAttributes().getNamedItem("Id").getNodeValue());
		            System.out.println("Item : " + idsAndItem.get(new Integer(eElement.getElementsByTagName("Item").item(i).getAttributes().getNamedItem("Id").getNodeValue())));
		            if(nNode.getAttributes().getNamedItem("Name").getNodeValue().equalsIgnoreCase(name)) {
		            	dlm.addElement(idsAndItem.get(new Integer(eElement.getElementsByTagName("Item").item(i).getAttributes().getNamedItem("Id").getNodeValue())));
		            }
	            }
	        }
	    }
	    
	    return dlm;
	}
	
	public void saveXML() throws ParserConfigurationException {
		File fXmlFile = new File(getClass().getResource("itempools.xml").getFile());
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = null;
	    try {
	        doc = dBuilder.parse(fXmlFile);
	    } catch (SAXException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    doc.getDocumentElement().normalize();

	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	    NodeList nList = doc.getElementsByTagName("Pool");

	    System.out.println("----------------------------");

	    for (int temp = 0; temp < nList.getLength(); temp++) {

	        Node nNode = nList.item(temp);

	        System.out.println("\nCurrent Element :" + nNode.getNodeName() + " " + nNode.getAttributes().getNamedItem("Name").getNodeValue());
	        
	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	            Element eElement = (Element) nNode;
	            //System.out.println(eElement.getAttribute("Name"));
	            //System.out.println("Item : " + eElement.getElementsByTagName("Item").item(0).getTextContent());
	            //System.out.println("Item Id : " + eElement.getElementsByTagName("Item").item(0).getAttributes().getNamedItem("Id").getNodeValue());
	            //System.out.println("Item Id : " + idsAndItem.get(new Integer(eElement.getElementsByTagName("Item").item(0).getAttributes().getNamedItem("Id").getNodeValue())));
	            for(int i=0;i<eElement.getElementsByTagName("Item").getLength();i++) {
	            	System.out.println("Item Id : " + eElement.getElementsByTagName("Item").item(i).getAttributes().getNamedItem("Id").getNodeValue());
		            System.out.println("Item : " + idsAndItem.get(new Integer(eElement.getElementsByTagName("Item").item(i).getAttributes().getNamedItem("Id").getNodeValue())));
	            }
	        }
	    }
	    
	    //writeXML();
	    
	    //System.exit(-1);
	}
	//put in every DefaultListModel and append everything
	public void writeXML(DefaultListModel<?> treasure, DefaultListModel<?> shop, DefaultListModel<?> boss, DefaultListModel<?> devil, DefaultListModel<?> angel) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
        	
        	PrintWriter out = null;
    		try {
    			out = new PrintWriter(path+"/itempools.xml");
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElement("ItemPools");
            doc.appendChild(mainRootElement);
            
            // append child elements to root element
            mainRootElement.appendChild(getCompany(doc, "treasure", treasure));
            mainRootElement.appendChild(getCompany(doc, "shop", shop));
            mainRootElement.appendChild(getCompany(doc, "boss", boss));
            mainRootElement.appendChild(getCompany(doc, "devil", devil));
            mainRootElement.appendChild(getCompany(doc, "angel", angel));
            
            // output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(out);
            transformer.transform(source, console);
 
            System.out.println("\nXML DOM Created Successfully..");
            
    		out.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       
	}
	
	private Node getCompany(Document doc, String id, DefaultListModel<?> dlm) {
        Element company = doc.createElement("Pool");
        company.setAttribute("Name", id);
        
        for(int i=0;i<dlm.size();i++) {
        	company.appendChild(getCompanyElements(doc, company, "Item", itemAndIds.getOrDefault(dlm.getElementAt(i), 1)+""));
        }
        //company.appendChild(getCompanyElements(doc, company, "Item", role));
        return company;
    }
    
    // utility method to create text node
    private Node getCompanyElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.setAttribute("DecreaseBy", "1");
        node.setAttribute("Id", value);
        node.setAttribute("RemoveOn", "0.1");
        node.setAttribute("Weight", "1");
        return node;
    }
	
	public void saveFile(String path) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(path+"/itempools.xml");
		for(int i=0;i<defaultCode.size();i++) {
			out.println(defaultCode.get(i));
		}
		out.close();
	}
	
}
