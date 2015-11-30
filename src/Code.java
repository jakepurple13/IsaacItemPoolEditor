import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
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
	
	public Code(boolean useDefault) throws IOException {
		path = "NOPE!";//getClass().getResource("itempools.xml").getFile();
		itemAndIds = new HashMap<String, Integer>();
		idsAndItem = new HashMap<Integer, String>();
		items = getItems();
		itemsids = getItemIds();
		for(int i=0;i<items.size();i++) {
			itemAndIds.put(items.get(i), Integer.parseInt(itemsids.get(i)));
			idsAndItem.put(Integer.parseInt(itemsids.get(i)), items.get(i));
		}
	}
	
	public Code(File paths) throws IOException {
		path = findFile(paths);
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
	
	public String findFile(File file) {
	      File[] list = file.listFiles();
	      String name = "itempools.xml";
	      String returned = "";
	      if(list!=null)
	      for (File fil : list) {
	          if (fil.isDirectory()) {
	              findFile(name,fil);
	          } else if (fil.getName().contains(name)) {
	              //System.out.println(fil.getAbsolutePath());
	              returned = fil.getAbsolutePath();
	              //return fil.getAbsolutePath();
	              break;
	          }
	          
	         /* else if (name.equalsIgnoreCase(fil.getName())) {
	              System.out.println(fil.getAbsolutePath());
	              return fil.getAbsolutePath();
	          }*/
	      }
	      return returned;
	  }
	
	public void findFile(String name, File file) {
	      File[] list = file.listFiles();
	      if(list!=null)
	      for (File fil : list) {
	          if (fil.isDirectory()) {
	              findFile(name,fil);
	          } else if (name.equalsIgnoreCase(fil.getName())) {
	              System.out.println(fil.getAbsolutePath());
	          }
	      }
	  }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultListModel<?> saveXML1(String name) throws ParserConfigurationException {
		DefaultListModel dlm = new DefaultListModel();
		File fXmlFile;
		if(path.equalsIgnoreCase("NOPE!")) {
			fXmlFile = new File(getClass().getResource("itempools.xml").getFile());
		} else {
			fXmlFile = new File(path);
		}
		
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
	public void writeXML(@SuppressWarnings("rawtypes") DefaultListModel...objects) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
        	
        	PrintWriter out = null;
    		try {
    			if(path.equalsIgnoreCase("NOPE!")) {
    				out = new PrintWriter(new File(System.getProperty("user.home") + "/Desktop/itempools.xml"));
    			} else {
    				out = new PrintWriter(path);
    			}
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElement("ItemPools");
            doc.appendChild(mainRootElement);
            
            // append child elements to root element
            mainRootElement.appendChild(getCompany(doc, "treasure", objects[0]));
            mainRootElement.appendChild(getCompany(doc, "shop", objects[1]));
            mainRootElement.appendChild(getCompany(doc, "boss", objects[2]));
            mainRootElement.appendChild(getCompany(doc, "devil", objects[3]));
            mainRootElement.appendChild(getCompany(doc, "angel", objects[4]));
            mainRootElement.appendChild(getCompany(doc, "secret", objects[5]));
            mainRootElement.appendChild(getCompany(doc, "library", objects[6]));
            mainRootElement.appendChild(getCompany(doc, "challenge", objects[7]));
            mainRootElement.appendChild(getCompany(doc, "goldenChest", objects[8]));
            mainRootElement.appendChild(getCompany(doc, "redChest", objects[9]));
            mainRootElement.appendChild(getCompany(doc, "beggar", objects[10]));
            mainRootElement.appendChild(getCompany(doc, "demonBeggar", objects[11]));
            mainRootElement.appendChild(getCompany(doc, "curse", objects[12]));
            mainRootElement.appendChild(getCompany(doc, "keyMaster", objects[13]));
            mainRootElement.appendChild(getCompany(doc, "bossrush", objects[14]));
            mainRootElement.appendChild(getCompany(doc, "dungeon", objects[15]));
            mainRootElement.appendChild(getCompany(doc, "greedTreasure", objects[16]));
            mainRootElement.appendChild(getCompany(doc, "greedBoss", objects[17]));
            mainRootElement.appendChild(getCompany(doc, "greedShop", objects[18]));
            mainRootElement.appendChild(getCompany(doc, "greedCurse", objects[19]));
            mainRootElement.appendChild(getCompany(doc, "greedDevil", objects[20]));
            mainRootElement.appendChild(getCompany(doc, "greedAngel", objects[21]));
            mainRootElement.appendChild(getCompany(doc, "greedLibrary", objects[22]));
            mainRootElement.appendChild(getCompany(doc, "greedSecret", objects[23]));
            mainRootElement.appendChild(getCompany(doc, "greedGoldenChest", objects[24]));
            mainRootElement.appendChild(getCompany(doc, "bombBum", objects[25]));
            // output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(out);
            transformer.transform(source, console);
 
            System.out.println("\nXML DOM Created Successfully..");
            
    		out.close();
    		if(path.equalsIgnoreCase("NOPE!")) {
    			deleteFirstLine(System.getProperty("user.home") + "/Desktop/itempools.xml");
    		} else {
    			deleteFirstLine(path);
    		}
    		
 
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
	
    public void deleteFirstLine(String paths) throws IOException {
    	 RandomAccessFile raf = new RandomAccessFile(paths, "rw");          
         //Initial write position                                             
        long writePosition = raf.getFilePointer();                            
        raf.readLine();                                                       
        // Shift the next lines upwards.                                      
        long readPosition = raf.getFilePointer();                             

        byte[] buff = new byte[1024];                                         
        int n;                                                                
        while (-1 != (n = raf.read(buff))) {                                  
            raf.seek(writePosition);                                          
            raf.write(buff, 0, n);                                            
            readPosition += n;                                                
            writePosition += n;                                               
            raf.seek(readPosition);                                           
        }                                                                     
        raf.setLength(writePosition);                                         
        raf.close();   
    }
    
	public void saveFile(String path) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(path+"/itempools.xml");
		for(int i=0;i<defaultCode.size();i++) {
			out.println(defaultCode.get(i));
		}
		out.close();
	}
	
}
