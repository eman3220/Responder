package Main;

import java.awt.EventQueue;

import ai.Claire;

import thread.ClockThread;
import data.Data;
import gui.ConversationPanel;
import gui.ResponderFrame;

/**
 * Responder is a artificial intelligence program that takes in
 * a sentence from the user and determines what to say back to the
 * user. 
 * 
 * This will require...
 * a dictionary of words the program can say,
 * a parser that identifies keywords,
 * 
 * @author Emmanuel
 *
 */
public class Responder {
	
	private ResponderFrame frame;
	private Claire claire;

	public Responder(){
		guiSetup();
		threadSetup();
		setupClaire();
		
		// setup complete, responder now waiting for user input
		Claire.state = Claire.WAITING;
	}

	private void guiSetup() {
		frame = new ResponderFrame(this);
		frame.repaint();
	}

	private void threadSetup() {
		ClockThread clk = new ClockThread(this);
		//clk.start();
	}
	
	private void setupClaire() {
		this.claire = new Claire();
		//ConversationPanel.claireToConversation("Hello "+frame.getClaire().getMemory().getUsersName());
	}

	public static void main(String[]args){
		EventQueue.invokeLater(new Runnable(){
	         public void run(){
	        	 new Responder();
	         }
	      });
		
	}

	public ResponderFrame getFrame() {
		return this.frame;
	}
	
	public Claire getClaire(){
		return this.claire;
	}
}
