package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import ai.Claire;

import data.Dictionary;
import data.Subject;

public class SubjectPanel extends JPanel implements KeyListener{
	
	private ResponderFrame frame;
	private JLabel knownSubjectsLabel = new JLabel("--- SUBJECTS ---");
	private Rectangle kslBounds = new Rectangle(25,35,200,40);
	
	private DefaultListModel dlm;
	private JList knownSubjects;
	private JScrollPane knownSubjectsSp;
	private Rectangle ksBounds = new Rectangle(25, 80, 320, 420);
	
	private JTextField search;
	private Rectangle sBounds = new Rectangle((int)(950*0.5)-110,20,220,30);
	
	public void paint(Graphics g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 950, 600);
		
		g.setColor(Color.white);
		g.fillRoundRect(kslBounds.x, kslBounds.y, kslBounds.width, kslBounds.height, 10, 10);
		g.fillRoundRect(ksBounds.x, ksBounds.y, ksBounds.width, ksBounds.height, 10, 10);
		g.fillRoundRect(sBounds.x-3,sBounds.y-3,sBounds.width+6,sBounds.height+6,10,10);
		
		this.paintComponents(g);
	}
	
	public SubjectPanel(ResponderFrame frame) {
		this.frame = frame;
		panelSetup();
	}

	private void panelSetup() {
		this.setLayout(null);
		knownSubjectsLabel.setBounds(30, 40, 200, 30);
		this.add(knownSubjectsLabel);
		
		dlm = new DefaultListModel();
		fillSubjectContent();
		knownSubjects = new JList(dlm);
		knownSubjects.setBounds(35, 90, 300, 400);
		//this.add(knownSubjects);
		
		knownSubjectsSp = new JScrollPane(knownSubjects);
		knownSubjectsSp.setBounds(35, 90, 300, 400);
		this.add(knownSubjectsSp);
		
		search = new JTextField(100);
		search.addKeyListener(this);
		search.setBounds(sBounds);
		this.add(search, BorderLayout.NORTH);
	}

	/**
	 * Fills the JList with the subjects Claire knows about.
	 * @return
	 */
	private void fillSubjectContent() {
		String[] subjects = null;
		try {
			Scanner scan = new Scanner(new File("memory/subject/frequentSubjects.txt"));
						
			while(scan.hasNext()){
				dlm.addElement(scan.nextLine());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Populates list of subjects known to Claire.
	 * Done by clearing DefaultListModel and adding in new
	 * strings found from file.
	 */
	private void populateList(){
		//System.out.println("\""+search.getText()+"\"");
		if(search.getText().equals(""))return;
		//System.out.println("two");
		
		// TODO list of subject names stored in claire
		Claire c = this.frame.getClaire();
		Dictionary subjects = c.getMemory().getSubjects();
		dlm.clear();
		
		// test contents of dictionary
		//System.out.println("begin");
		//for(String s : subjects.search("appl")){
		//	System.out.println(s);
		//}
		
		long ini = System.currentTimeMillis();
		//System.out.println(ini);
		
		for(String s : subjects.search(search.getText())){
			dlm.addElement(s);
		}
		
		long fin = System.currentTimeMillis() - ini;
		//System.out.println(fin);
		
		System.out.println("Searched subjects: "+search.getText()+" in "+fin+"ms");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()) {
			if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				System.out.println("switch to conversation");
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				System.out.println("switch to dictionary");
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				System.out.println("switch to subject");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//System.out.println("there "+search.getText());
		populateList();
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// unsupported
	}

}
