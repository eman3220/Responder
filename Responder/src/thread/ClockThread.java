package thread;

import Main.Responder;

public class ClockThread extends Thread{

	private Responder main;
	
	public ClockThread(Responder main){
		this.main = main;
	}
	
	public void run(){
		while(true){
			delay();
			main.getFrame().repaint();
		}
	}

	private void delay() {
		try {
			this.sleep(55);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
