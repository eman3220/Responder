package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import thread.SnatcherThread;
import utilities.DefinitionSnatcher;

import data.Dictionary;

import ai.Claire;

public class DictionaryPanel extends JPanel implements KeyListener,
		MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResponderFrame frame;
	private JLabel dictionaryLabel = new JLabel("  --- Dictionary ---");
	private Rectangle dlBounds = new Rectangle(25, 35, 200, 40);

	private DefaultListModel dlm;
	private JList dictionaryList;
	private JScrollPane dictionaryListSp;
	private Rectangle dBounds = new Rectangle(30, 90, 320, 420);
	private int adj = 1;

	private JTextField search;
	private Rectangle sBounds = new Rectangle((int) (950 * 0.5) - 110, 20, 220,
			30);

	private ArrayList<String> definitions = new ArrayList<String>();
	private boolean isDisplayed = false;
	private Rectangle defaultDefBounds;

	public void paint(Graphics g) {
		// System.out.println("tick");
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 950, 600);

		g.setColor(Color.white);
		// borders
		g.fillRoundRect(dBounds.x - adj - 3, dBounds.y - adj - 3, dBounds.width
				+ (adj * 2) + 6, dBounds.height + (adj * 2) + 6, 10, 10);
		g.fillRoundRect(dlBounds.x - adj, dlBounds.y - adj, dlBounds.width
				+ (adj * 2), dlBounds.height + (adj * 2), 10, 10);
		g.fillRoundRect(sBounds.x - 3, sBounds.y - 3, sBounds.width + 6,
				sBounds.height + 6, 10, 10);

		// display list of definitions... as long there are definitions to
		// display

		// System.out.println(dictionaryList.getSelectedIndex());
		if (this.dictionaryList.getSelectedIndex() > -1) {
			isDisplayed = true;
			// System.out.println("Printing definitions...");
			int limit = 3;

			for (int i = 0; i < this.definitions.size(); i++) {
				// System.out.println(count);
				if (i == limit)
					break;

				g.setColor(Color.white);
				g.fillRoundRect(defaultDefBounds.x - 10, defaultDefBounds.y
						+ (i * 70) - 20, defaultDefBounds.width,
						defaultDefBounds.height, 10, 10);

				g.setColor(Color.black);
				g.drawString(definitions.get(i), defaultDefBounds.x,
						defaultDefBounds.y + (i * 70));
			}

		}

		this.paintComponents(g);
	}

	public DictionaryPanel(ResponderFrame frame) {
		this.frame = frame;
		panelSetup();
	}

	private void panelSetup() {
		this.setLayout(null);

		// label
		this.dictionaryLabel.setBounds(dlBounds);
		this.add(dictionaryLabel);

		// list
		this.dlm = new DefaultListModel();
		this.dictionaryList = new JList(dlm);
		this.dictionaryList.setBounds(dBounds);
		this.dictionaryList.addMouseListener(this);
		this.dictionaryListSp = new JScrollPane(this.dictionaryList);
		this.dictionaryListSp.setBounds(dBounds);
		this.add(dictionaryListSp);

		// search
		search = new JTextField(100);
		search.addKeyListener(this);
		search.setBounds(sBounds);
		this.add(search);

		// definitions bounds
		this.defaultDefBounds = new Rectangle(frame.getWidth() - 490, 100, 500,
				40);
	}

	/**
	 * Populates list of words known to Claire. Done by clearing
	 * DefaultListModel and adding in new strings found searching the trie.
	 */
	private void populateList() {
		isDisplayed = false;
		if (search.getText().equals(""))
			return;

		String lowered = search.getText().toLowerCase();

		Claire claire = frame.getClaire();
		Dictionary d = claire.getMemory().getDictionary();
		dlm.clear();

		// test contents of dictionary
		// for(String s : d.search("appl")){
		// System.out.println(s);
		// }

		int count = 0;
		// TODO search limit can change
		int limit = 50;

		// test for how long it takes to complete search
		long ini = System.currentTimeMillis();
		// System.out.println(ini);

		for (String s : d.search(lowered)) {
			if (count == limit) {
				break;
			}
			dlm.addElement(s);
			count++;
		}

		long fin = System.currentTimeMillis() - ini;
		// System.out.println(fin);
		// System.out.println("End fill");

		System.out.println("Searched dictionary: " + lowered + " in " + fin
				+ "ms");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()) {
			// TODO ctrl shortcuts...
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			reset();
		}
		populateList();
	}

	private void reset() {
		dlm.clear();
		dictionaryList.clearSelection();
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// unsupported
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int index = this.dictionaryList.getSelectedIndex();
		if (index == -1)
			return;
		// System.out.println(dictionaryList.getSelectedValue() + " at " +
		// index);

		// check if file exists
		String filename = dictionaryList.getSelectedValue() + ".txt";
		// TODO change to .def later
		String pathname = "memory/dictionary/definition/";
		File defFile = new File(pathname + filename);

		if (!defFile.exists()) {
			System.out.println("Doesnt Exist... Searching the web");
			// get definition from web...
			try {
				DefinitionSnatcher ds = new DefinitionSnatcher(
						(String) dictionaryList.getSelectedValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		// if file still doesnt exist, we dont know definition
		if (!defFile.exists()) {
			// fail();
			return;
		}

		// read the file, put top definitions to definitions list
		readFile(defFile);

		frame.repaint();
	}

	private void fail() {
		JOptionPane.showMessageDialog(null,
				"I do not know what this word means.");
	}

	private void readFile(File defFile) {
		this.definitions = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(defFile);
			String line;
			int count = 0;
			int limit = 4;

			// get four definitions
			while (scan.hasNext()) {
				if (count == limit)
					break;
				line = scan.nextLine();

				if (line.contains("[[unknown]]")) {
					fail();
					this.definitions
							.add("I don't know what this word means...");
					return;
				}

				this.definitions.add(line);
				count++;
			}

			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
