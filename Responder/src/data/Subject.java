package data;

import java.io.File;
import java.util.ArrayList;

public class Subject {
	// this is what Eman will work on next
	
	private String title;
	private ArrayList<String> bulletPoints;
	private File subjFile;
	
	public Subject(File subjFile){
		this.subjFile = subjFile;
		readFile();
	}

	private void readFile() {
		
	}
}
