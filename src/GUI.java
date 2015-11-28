import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;


public class GUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	@SuppressWarnings("rawtypes")
	DefaultListModel listModel = new DefaultListModel<String>();
	DefaultListModel treasureModel;
	DefaultListModel shopModel;
	DefaultListModel bossModel;
	DefaultListModel devilModel;
	DefaultListModel angelModel;
	DefaultListModel secretModel;
	DefaultListModel libraryModel;
	DefaultListModel challengeModel;
	DefaultListModel goldenChestModel;
	DefaultListModel redChestModel;
	
	private JTextField searchBar;
	String searcher = "";
	Code c;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 2, 0, 0));
		
		JMenuBar menuBar = new JMenuBar();

		//Build the first menu.
		JMenu menu = new JMenu("Submit here!");
		JMenuItem menuItem = new JMenuItem("Submit");
		menuItem.addActionListener(this);
		//TODO:make a menu submit button
		menu.add(menuItem);
		menuBar.add(menu);
		
		this.setJMenuBar(menuBar);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select the itempools.xml to save the file or choose a directory if you don't have one");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    int returnVal = chooser.showOpenDialog(contentPane);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       System.out.println(chooser.getSelectedFile().getAbsolutePath());
	    }
		
		c = new Code(chooser.getSelectedFile().getAbsolutePath());
		
		try {
			treasureModel = c.saveXML1("treasure");
			shopModel = c.saveXML1("shop");
			bossModel = c.saveXML1("boss");
			devilModel = c.saveXML1("devil");
			angelModel = c.saveXML1("angel");
			
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<String> items = c.getItemList();
		
		JList list = new JList(listModel);
		JList itemsToAdd = new JList(treasureModel);
		
		scrollPane.setViewportView(list);
		
		for(String s : items) {
			listModel.addElement(s);
		}
		
		list.setModel(listModel);
		
		
		
		//wantedItems.add(Integer.parseInt(itemId.get(comboBox.getSelectedIndex())));
		
		JButton btnAdd = new JButton("Add");
		scrollPane.setRowHeaderView(btnAdd);
		
		searchBar = new JTextField();
		scrollPane.setColumnHeaderView(searchBar);
		searchBar.setColumns(10);
		
		searchBar.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				listModel.clear();
				
				if(!searchBar.getText().equals(searcher)) {
					searcher = searchBar.getText();
				}
				
				try {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE) {
						searcher = searcher.substring(0, searcher.length()-1);
					} else {
						searcher += e.getKeyChar();
					}
				} catch(StringIndexOutOfBoundsException e1) {
					searcher = "";
				}
				
				for(String w : items) {
					if(w.toLowerCase().indexOf(searcher.toLowerCase())!=-1) {
						listModel.addElement(w);
					}
				}
				
				try {
					list.setModel(listModel);
				} catch(IndexOutOfBoundsException e1) {
			
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JScrollPane pools = new JScrollPane();
		contentPane.add(pools);
		
		pools.setViewportView(itemsToAdd);
		
		JComboBox comboBox = new JComboBox();
		pools.setColumnHeaderView(comboBox);
		
		JButton btnDelete = new JButton("Delete");
		pools.setRowHeaderView(btnDelete);
		
		comboBox.addItem("Treasure Room");
		comboBox.addItem("Shop");
		comboBox.addItem("Boss");
		comboBox.addItem("Devil");
		comboBox.addItem("Angel");
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String key = comboBox.getSelectedItem().toString();
				switch (key) {
				case "Shop":
					shopModel.addElement(list.getSelectedValue());
					itemsToAdd.setModel(shopModel);
					break;
				case "Treasure Room":
					treasureModel.addElement(list.getSelectedValue());
					itemsToAdd.setModel(treasureModel);
					break;
				case "Boss":
					bossModel.addElement(list.getSelectedValue());
					itemsToAdd.setModel(bossModel);
					break;
				case "Devil":
					devilModel.addElement(list.getSelectedValue());
					itemsToAdd.setModel(devilModel);
					break;
				case "Angel":
					angelModel.addElement(list.getSelectedValue());
					itemsToAdd.setModel(angelModel);
					break;
				default:
					break;
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String key = comboBox.getSelectedItem().toString();
					switch (key) {
					case "Shop":
						shopModel.remove(itemsToAdd.getSelectedIndex());
						itemsToAdd.setModel(shopModel);
						break;
					case "Treasure Room":
						treasureModel.remove(itemsToAdd.getSelectedIndex());
						itemsToAdd.setModel(treasureModel);
						break;
					case "Boss":
						bossModel.remove(itemsToAdd.getSelectedIndex());
						itemsToAdd.setModel(bossModel);
						break;
					case "Devil":
						devilModel.remove(itemsToAdd.getSelectedIndex());
						itemsToAdd.setModel(devilModel);
						break;
					case "Angel":
						angelModel.remove(itemsToAdd.getSelectedIndex());
						itemsToAdd.setModel(angelModel);
						break;
					default:
						break;
					}
				} catch(ArrayIndexOutOfBoundsException e1) {
					
				}
			}
		});
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String key = comboBox.getSelectedItem().toString();
				switch (key) {
				case "Shop":
					itemsToAdd.setModel(shopModel);
					break;
				case "Treasure Room":
					itemsToAdd.setModel(treasureModel);
					break;
				case "Boss":
					itemsToAdd.setModel(bossModel);
					break;
				case "Devil":
					itemsToAdd.setModel(devilModel);
					break;
				case "Angel":
					itemsToAdd.setModel(angelModel);
					break;
				default:
					break;
				}
				pools.setViewportView(itemsToAdd);
			}
		});
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//this is for the submit
		//<Item DecreaseBy="1" Id="415" RemoveOn="0.1" Weight="1" />
		c.writeXML(treasureModel, shopModel, bossModel, devilModel, angelModel);
		
	}
	
	

}
