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
import java.io.File;
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
	DefaultListModel beggarModel;
	DefaultListModel demonBeggarModel;
	DefaultListModel curseModel;
	DefaultListModel keyMasterModel;
	DefaultListModel bossrushModel;
	DefaultListModel dungeonModel;
	DefaultListModel greedTreasureModel;
	DefaultListModel greedBossModel;
	DefaultListModel greedShopModel;
	DefaultListModel greedCurseModel;
	DefaultListModel greedDevilModel;
	DefaultListModel greedAngelModel;
	DefaultListModel greedLibraryModel;
	DefaultListModel greedSecretModel;
	DefaultListModel greedGoldenChestModel;
	DefaultListModel bombBumModel;
	
	@SuppressWarnings("rawtypes")
	JList itemsToAdd;
	
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
		
		JMenuItem deleteAll = new JMenuItem("Delete All");
		deleteAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearAll((DefaultListModel) itemsToAdd.getModel());
			}
		});
		//TODO:make a menu submit button
		menu.add(deleteAll);
		menuBar.add(menu);
		
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
	    
		try {
			c = new Code(chooser.getSelectedFile());
		} catch(NullPointerException e1) {
			c = new Code(false);
		}
		
		try {
			
			treasureModel = c.saveXML1("treasure");
			shopModel = c.saveXML1("shop");
			bossModel = c.saveXML1("boss");
			devilModel = c.saveXML1("devil");
			angelModel = c.saveXML1("angel");
			secretModel = c.saveXML1("secret");
			libraryModel = c.saveXML1("library");
			challengeModel = c.saveXML1("challenge");
			goldenChestModel= c.saveXML1("goldenChest");
			redChestModel = c.saveXML1("redChest");
			beggarModel = c.saveXML1("beggar");
			demonBeggarModel = c.saveXML1("demonBeggar");
			curseModel = c.saveXML1("curse");
			keyMasterModel = c.saveXML1("keyMaster");
			bossrushModel = c.saveXML1("bossrush");
			dungeonModel = c.saveXML1("dungeon");
			greedTreasureModel = c.saveXML1("greedTreasure");
			greedBossModel = c.saveXML1("greedBoss");
			greedShopModel = c.saveXML1("greedShop");
			greedCurseModel = c.saveXML1("greedCurse");
			greedDevilModel = c.saveXML1("greedDevil");
			greedAngelModel = c.saveXML1("greedAngel");
			greedLibraryModel = c.saveXML1("greedLibrary");
			greedSecretModel = c.saveXML1("greedSecret");
			greedGoldenChestModel = c.saveXML1("greedGoldenChest");
			bombBumModel = c.saveXML1("bombBum");
			
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<String> items = c.getItemList();
		
		JList list = new JList(listModel);
		itemsToAdd = new JList(treasureModel);
		
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
		comboBox.addItem("Secret");
		comboBox.addItem("Library");
		comboBox.addItem("Challenge");
		comboBox.addItem("Golden Chest");
		comboBox.addItem("Red Chest");
		comboBox.addItem("Beggar");
		comboBox.addItem("Demon Beggar");
		comboBox.addItem("Curse");
		comboBox.addItem("Key Master");
		comboBox.addItem("Boss Rush");
		comboBox.addItem("Dungeon");
		comboBox.addItem("Greed Treasure");
		comboBox.addItem("Greed Boss");
		comboBox.addItem("Greed Shop");
		comboBox.addItem("Greed Curse");
		comboBox.addItem("Greed Devil");
		comboBox.addItem("Greed Angel");
		comboBox.addItem("Greed Library");
		comboBox.addItem("Greed Secret");
		comboBox.addItem("Greed Golden Chest");
		comboBox.addItem("Bomb Bum");
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String key = comboBox.getSelectedItem().toString();
				DefaultListModel newOne;
				switch (key) {
				case "Shop":
					shopModel.addElement(list.getSelectedValue());
					newOne = shopModel;
					break;
				case "Treasure Room":
					treasureModel.addElement(list.getSelectedValue());
					newOne = treasureModel;
					break;
				case "Boss":
					bossModel.addElement(list.getSelectedValue());
					newOne = bossModel;
					break;
				case "Devil":
					devilModel.addElement(list.getSelectedValue());
					newOne = devilModel;
					break;
				case "Angel":
					angelModel.addElement(list.getSelectedValue());
					newOne = angelModel;
					break;
				case "Secret":
					secretModel.addElement(list.getSelectedValue());
					newOne = secretModel;
					break;
				case "Library":
					libraryModel.addElement(list.getSelectedValue());
					newOne = libraryModel;
					break;
				case "Challenge":
					challengeModel.addElement(list.getSelectedValue());
					newOne = challengeModel;
					break;
				case "Golden Chest":
					goldenChestModel.addElement(list.getSelectedValue());
					newOne = goldenChestModel;
					break;
				case "Red Chest":
					redChestModel.addElement(list.getSelectedValue());
					newOne = redChestModel;
					break;
				case "Beggar":
					beggarModel.addElement(list.getSelectedValue());
					newOne = beggarModel;
					break;
				case "Demon Beggar":
					demonBeggarModel.addElement(list.getSelectedValue());
					newOne = demonBeggarModel;
					break;
				case "Curse":
					curseModel.addElement(list.getSelectedValue());
					newOne = curseModel;
					break;
				case "Key Master":
					keyMasterModel.addElement(list.getSelectedValue());
					newOne = keyMasterModel;
					break;
				case "Boss Rush":
					bossrushModel.addElement(list.getSelectedValue());
					newOne = bossrushModel;
					break;
				case "Dungeon":
					dungeonModel.addElement(list.getSelectedValue());
					newOne = dungeonModel;
					break;
				case "Greed Treasure":
					greedTreasureModel.addElement(list.getSelectedValue());
					newOne = greedTreasureModel;
					break;
				case "Greed Boss":
					greedBossModel.addElement(list.getSelectedValue());
					newOne = greedBossModel;
					break;
				case "Greed Shop":
					greedShopModel.addElement(list.getSelectedValue());
					newOne = greedShopModel;
					break;
				case "Greed Curse":
					greedCurseModel.addElement(list.getSelectedValue());
					newOne = greedCurseModel;
					break;
				case "Greed Devil":
					greedDevilModel.addElement(list.getSelectedValue());
					newOne = greedDevilModel;
					break;
				case "Greed Angel":
					greedAngelModel.addElement(list.getSelectedValue());
					newOne = greedAngelModel;
					break;
				case "Greed Library":
					greedLibraryModel.addElement(list.getSelectedValue());
					newOne = greedLibraryModel;
					break;
				case "Greed Secret":
					greedSecretModel.addElement(list.getSelectedValue());
					newOne = greedSecretModel;
					break;
				case "Greed Golden Chest":
					greedGoldenChestModel.addElement(list.getSelectedValue());
					newOne = greedGoldenChestModel;
					break;
				case "Bomb Bum":
					bombBumModel.addElement(list.getSelectedValue());
					newOne = bombBumModel;
					break;
				default:
					newOne = new DefaultListModel();
					newOne.addElement("Nothing");
					break;
				}
				
				itemsToAdd.setModel(newOne);
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String key = comboBox.getSelectedItem().toString();
					DefaultListModel newOne;
					switch (key) {
					case "Shop":
						shopModel.remove(list.getSelectedIndex());
						newOne = shopModel;
						break;
					case "Treasure Room":
						treasureModel.remove(list.getSelectedIndex());
						newOne = treasureModel;
						break;
					case "Boss":
						bossModel.remove(list.getSelectedIndex());
						newOne = bossModel;
						break;
					case "Devil":
						devilModel.remove(list.getSelectedIndex());
						newOne = devilModel;
						break;
					case "Angel":
						angelModel.remove(list.getSelectedIndex());
						newOne = angelModel;
						break;
					case "Secret":
						secretModel.remove(list.getSelectedIndex());
						newOne = secretModel;
						break;
					case "Library":
						libraryModel.remove(list.getSelectedIndex());
						newOne = libraryModel;
						break;
					case "Challenge":
						challengeModel.remove(list.getSelectedIndex());
						newOne = challengeModel;
						break;
					case "Golden Chest":
						goldenChestModel.remove(list.getSelectedIndex());
						newOne = goldenChestModel;
						break;
					case "Red Chest":
						redChestModel.remove(list.getSelectedIndex());
						newOne = redChestModel;
						break;
					case "Beggar":
						beggarModel.remove(list.getSelectedIndex());
						newOne = beggarModel;
						break;
					case "Demon Beggar":
						demonBeggarModel.remove(list.getSelectedIndex());
						newOne = demonBeggarModel;
						break;
					case "Curse":
						curseModel.remove(list.getSelectedIndex());
						newOne = curseModel;
						break;
					case "Key Master":
						keyMasterModel.remove(list.getSelectedIndex());
						newOne = keyMasterModel;
						break;
					case "Boss Rush":
						bossrushModel.remove(list.getSelectedIndex());
						newOne = bossrushModel;
						break;
					case "Dungeon":
						dungeonModel.remove(list.getSelectedIndex());
						newOne = dungeonModel;
						break;
					case "Greed Treasure":
						greedTreasureModel.remove(list.getSelectedIndex());
						newOne = greedTreasureModel;
						break;
					case "Greed Boss":
						greedBossModel.remove(list.getSelectedIndex());
						newOne = greedBossModel;
						break;
					case "Greed Shop":
						greedShopModel.remove(list.getSelectedIndex());
						newOne = greedShopModel;
						break;
					case "Greed Curse":
						greedCurseModel.remove(list.getSelectedIndex());
						newOne = greedCurseModel;
						break;
					case "Greed Devil":
						greedDevilModel.remove(list.getSelectedIndex());
						newOne = greedDevilModel;
						break;
					case "Greed Angel":
						greedAngelModel.remove(list.getSelectedIndex());
						newOne = greedAngelModel;
						break;
					case "Greed Library":
						greedLibraryModel.remove(list.getSelectedIndex());
						newOne = greedLibraryModel;
						break;
					case "Greed Secret":
						greedSecretModel.remove(list.getSelectedIndex());
						newOne = greedSecretModel;
						break;
					case "Greed Golden Chest":
						greedGoldenChestModel.remove(list.getSelectedIndex());
						newOne = greedGoldenChestModel;
						break;
					case "Bomb Bum":
						bombBumModel.remove(list.getSelectedIndex());
						newOne = bombBumModel;
						break;
					default:
						newOne = new DefaultListModel();
						break;
					}
					
					itemsToAdd.setModel(newOne);
				} catch(ArrayIndexOutOfBoundsException e1) {
					
				}
			}
		});
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String key = comboBox.getSelectedItem().toString();
				DefaultListModel newOne;
				switch (key) {
				case "Shop":
					newOne = shopModel;
					break;
				case "Treasure Room":
					newOne = treasureModel;
					break;
				case "Boss":
					newOne = bossModel;
					break;
				case "Devil":
					newOne = devilModel;
					break;
				case "Angel":
					newOne = angelModel;
					break;
				case "Secret":
					newOne = secretModel;
					break;
				case "Library":
					newOne = libraryModel;
					break;
				case "Challenge":
					newOne = challengeModel;
					break;
				case "Golden Chest":
					newOne = goldenChestModel;
					break;
				case "Red Chest":
					newOne = redChestModel;
					break;
				case "Beggar":
					newOne = beggarModel;
					break;
				case "Demon Beggar":
					newOne = demonBeggarModel;
					break;
				case "Curse":
					newOne = curseModel;
					break;
				case "Key Master":
					newOne = keyMasterModel;
					break;
				case "Boss Rush":
					newOne = bossrushModel;
					break;
				case "Dungeon":
					newOne = dungeonModel;
					break;
				case "Greed Treasure":
					newOne = greedTreasureModel;
					break;
				case "Greed Boss":
					newOne = greedBossModel;
					break;
				case "Greed Shop":
					newOne = greedShopModel;
					break;
				case "Greed Curse":
					newOne = greedCurseModel;
					break;
				case "Greed Devil":
					newOne = greedDevilModel;
					break;
				case "Greed Angel":
					newOne = greedAngelModel;
					break;
				case "Greed Library":
					newOne = greedLibraryModel;
					break;
				case "Greed Secret":
					newOne = greedSecretModel;
					break;
				case "Greed Golden Chest":
					newOne = greedGoldenChestModel;
					break;
				case "Bomb Bum":
					newOne = bombBumModel;
					break;
				default:
					newOne = new DefaultListModel();
					break;
				}
				itemsToAdd.setModel(newOne);
				pools.setViewportView(itemsToAdd);
			}
		});
		
		
	}
	
	
	public void clearAll(DefaultListModel dlm) {
		dlm.clear();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//this is for the submit
		c.writeXML(treasureModel, shopModel, bossModel, devilModel, angelModel,
				secretModel, libraryModel, challengeModel, goldenChestModel, redChestModel,
				beggarModel, demonBeggarModel, curseModel, keyMasterModel, bossrushModel,
				dungeonModel, greedTreasureModel, greedBossModel, greedShopModel, greedCurseModel,
				greedDevilModel, greedAngelModel, greedLibraryModel, greedSecretModel, greedGoldenChestModel,
				bombBumModel);
		
	}
	
	

}
