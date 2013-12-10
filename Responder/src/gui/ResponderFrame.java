package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ai.Claire;

import Main.Responder;

public class ResponderFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Responder main;

	public static int mode = 0;
	public static int CONVERSE = 1;
	public static int DICTIONARY = 2;
	public static int SUBJECT = 3;

	private ConversationPanel cp;
	private DictionaryPanel dp;
	private SubjectPanel sp;

	private JMenuBar menuBar;
	private JMenu[] menus = { new JMenu("File"), new JMenu("Mode") };
	private JMenuItem[] menuItems = { new JMenuItem("Start Over"),
			new JMenuItem("Exit"), new JMenuItem("Conversation"),
			new JMenuItem("Dictionary"), new JMenuItem("Subject") };

	@Override
	public void paint(Graphics g) {
		if (mode == 0)
			return;

		// draw components
		this.remove(cp);
		this.remove(dp);
		this.remove(sp);
		if (ResponderFrame.mode == CONVERSE) {
			this.add(cp);
			cp.repaint();
		} else if (ResponderFrame.mode == DICTIONARY) {
			this.add(dp);
			dp.repaint();
		} else if (ResponderFrame.mode == SUBJECT) {
			this.add(sp);
			sp.repaint();
		}
		this.paintComponents(g);
		// draw state-determined features

	}

	public ResponderFrame(Responder main) {
		this.main = main;
		frameSetup();
		mode = ResponderFrame.CONVERSE;
	}

	private void lookAndFeelSetup() {
		LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
		int index = 1;
		try {
			UIManager.setLookAndFeel(laf[index].getClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private void frameSetup() {
		// frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(950, 600);
		this.setLayout(null);
		this.lookAndFeelSetup();
		// this.setUndecorated(true);
		this.setVisible(true);
		this.setTitle("Responder");

		// panels
		Rectangle bounds = this.getBounds();
		this.cp = new ConversationPanel(this);
		this.cp.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		this.add(cp);

		this.dp = new DictionaryPanel(this);
		this.dp.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		this.add(dp);

		this.sp = new SubjectPanel(this);
		this.sp.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		this.add(sp);

		// menu bar
		this.menuBar = new JMenuBar();

		this.setJMenuBar(menuBar);

		// listeners
		for (JMenuItem item : this.menuItems) {
			item.addActionListener(this);
		}

		// file
		menus[0].add(menuItems[0]);
		menus[0].add(menuItems[1]);
		menuBar.add(menus[0]);

		// mode
		menus[1].add(menuItems[2]);
		menus[1].add(menuItems[3]);
		menus[1].add(menuItems[4]);
		menuBar.add(menus[1]);
	}

	public void actionPerformed(ActionEvent e) {
		// file
		if (e.getSource() == this.menuItems[0]) {
			// Start over
			this.cp.getConversation().setText("");
		} else if (e.getSource() == this.menuItems[1]) {
			// exit program
			int i = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to close the program?");
			if (i == 0) {
				System.exit(1);
			}
		}

		// mode
		else if (e.getSource() == this.menuItems[2]) {
			ResponderFrame.mode = CONVERSE;
		} else if (e.getSource() == this.menuItems[3]) {
			ResponderFrame.mode = DICTIONARY;
		} else if (e.getSource() == this.menuItems[4]) {
			ResponderFrame.mode = SUBJECT;
		} else {
			ResponderFrame.mode = CONVERSE;
		}
		this.repaint();
	}

	public ConversationPanel getCp() {
		return cp;
	}

	public void setCp(ConversationPanel cp) {
		this.cp = cp;
	}

	public DictionaryPanel getDp() {
		return dp;
	}

	public void setDp(DictionaryPanel dp) {
		this.dp = dp;
	}

	public Claire getClaire() {
		return this.main.getClaire();
	}

}
