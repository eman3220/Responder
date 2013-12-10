package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConversationPanel extends JPanel implements KeyListener,
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResponderFrame frame;
	private Rectangle bounds;

	private JTextField userSays;
	private static JTextArea conversation; 
	// TODO may have to use a textpane for images
	
	private static JScrollPane conversationSp;
	private JButton confirm;

	// quick fix
	private int yTranslate = 20;

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 950, 600);

		g.setColor(Color.white);
		g.fillRoundRect(bounds.x + 10, bounds.height - 140 + yTranslate,
				bounds.width - 150, 50, 10, 10);
		g.fillRoundRect(bounds.x + 20, bounds.y - 10 + yTranslate,
				bounds.width - 60, bounds.height - 140, 10, 10);
		this.paintComponents(g);
	}

	public ConversationPanel(ResponderFrame frame) {
		this.frame = frame;
		panelSetup();
	}

	private void panelSetup() {
		this.bounds = this.frame.getBounds();
		this.setLayout(null);

		userSays = new JTextField(100);
		userSays.setBounds(bounds.x + 20, bounds.height - 130 + yTranslate,
				bounds.width - 170, 30);
		userSays.addKeyListener(this);
		this.add(userSays);

		conversation = new JTextArea();
		conversation.setBounds(bounds.x + 30, bounds.y + yTranslate,
				bounds.width - 80, bounds.height - 160);
		conversation.setEditable(false);
		conversationSp = new JScrollPane(conversation);
		conversationSp.setBounds(bounds.x + 30, bounds.y + yTranslate,
				bounds.width - 80, bounds.height - 160);
		conversationSp
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(conversationSp);

		confirm = new JButton("Confirm");
		confirm.setBounds(bounds.width - 120, bounds.height - 110, 100, 30);
		confirm.addActionListener(this);
		this.add(confirm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.confirm) {
			confirmText();
		}
	}

	/**
	 * print user's text to screen, wait 3 seconds, determine how to respond to
	 * user's text.
	 */
	private void confirmText() {
		if (this.userSays.getText().equals(""))
			return;
		String text = this.userSays.getText();
		userToConversation(text);
		this.userSays.setText("");

		// TODO determine response
		this.frame.getClaire().respond(text);
	}

	/**
	 * Changes writing side to left side, before writing text to text area.
	 * 
	 * @param text
	 */
	private static void userToConversation(String text) {
		// conversation.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		conversation.setCaretPosition(conversation.getDocument().getLength());
		// conversation.setCaretPosition(conversation.getDocument().)
		conversation.append("YOU: " + text + "\n\n");
	}

	/**
	 * Used for taking Claire's text and putting it to conversation area.
	 * 
	 * @param text
	 */
	public static void claireToConversation(String text) {
		// conversation.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		conversation.setCaretPosition(conversation.getDocument().getLength());
		conversation.append("ARTIFICIAL INTELLIGENCE: " + text + "\n\n");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()) {
			if (e.getKeyChar() == KeyEvent.VK_NUMPAD1) {
				System.out.println("switch to conversation");
			} else if (e.getKeyChar() == KeyEvent.VK_NUMPAD2) {
				System.out.println("switch to dictionary");
			} else if (e.getKeyChar() == KeyEvent.VK_NUMPAD3) {
				System.out.println("switch to subject");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// unsupported
	}

	@Override
	public void keyTyped(KeyEvent k) {
		if (k.getKeyChar() == KeyEvent.VK_ENTER) {
			confirmText();
		}
	}

	// TODO Getters and Setters

	public JTextField getUserSays() {
		return userSays;
	}

	public void setUserSays(JTextField userSays) {
		this.userSays = userSays;
	}

	public JTextArea getConversation() {
		return conversation;
	}

	public void setConversation(JTextArea conversation) {
		this.conversation = conversation;
	}
}
