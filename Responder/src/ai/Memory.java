package ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import data.Dictionary;

/**
 * Consists of Claire's memories, saved as text files. Should consist of
 * important things Claire needs to remember. (eg dictionary)
 * 
 * @author Emmanuel
 * 
 */
public class Memory {

	private String usersName = "Emmanuel";
	private Dictionary dictionary;
	private Dictionary subjectDictionary;

	public Memory() {

	}

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}
	
	public void assembleSubjectContent() {
		File dictionaryFile = new File("memory/subject/subjectContent.txt");
		if (dictionaryFile.exists()) {
			// if memory files exist, scan files
			System.out.println(">>> Subjects found. Reading memory...");
			this.subjectDictionary = new Dictionary();
			this.subjectDictionary.assemble(dictionaryFile);
			//assembleTrie(dictionaryFile);
			
		} else {
			// else make a new memory
			System.out.println(">>> Subjects file not found. Creating memory...");
			PrintWriter writer;
			try {
				writer = new PrintWriter("memory/subject/subjectContent.txt",
						"UTF-8");
				
				// take a dictionary/big piece of text, and use it as dictionary
				
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.subjectDictionary = new Dictionary();
			this.subjectDictionary.assemble(dictionaryFile);
			//assembleTrie(dictionaryFile);
		}
		
	}

	public void assembleDictionary() {
		File dictionaryFile = new File("memory/dictionary/dictionary.txt");
		if (dictionaryFile.exists()) {
			// if memory files exist, scan files
			System.out.println(">>> Dictionary found. Reading memory...");
			this.dictionary = new Dictionary();
			this.dictionary.assemble(dictionaryFile);
			//assembleTrie(dictionaryFile);
			
		} else {
			// else make a new memory
			System.out.println(">>> Dictionary not found. Creating memory...");
			PrintWriter writer;
			try {
				writer = new PrintWriter("memory/dictionary/dictionary.txt",
						"UTF-8");
				
				// take a dictionary/big piece of text, and use it as dictionary
				
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.dictionary = new Dictionary();
			this.dictionary.assemble(dictionaryFile);
			//assembleTrie(dictionaryFile);
		}
		
	}

	public Dictionary getDictionary() {
		return this.dictionary;
	}
	
	public Dictionary getSubjects(){
		return this.subjectDictionary;
	}

}
