package utilities;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * 
 * @author Emmanuel Godinez, Will Hardwick-Smith
 * 
 */
public class DefinitionSnatcher {

	public DefinitionSnatcher(String search) throws Exception {
		clearSnatcherMemory();
		search = checkWhiteSpace(search);

		try {
			URL url = new URL("http://dictionary.reference.com/browse/"
					+ search + "?s=t");
			InputStream is = url.openStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			FileWriter filewrite = new FileWriter(new File(
					"dataSnatcher/definitions/urlfile.txt"));
			String line;

			// reads the whole html file into a string.
			while ((line = in.readLine()) != null) {
				//System.out.println(line);
				filewrite.write(line+"\n");
			}
			in.close();
			filewrite.close();

		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (UnknownHostException uhe){
			System.out.println("Unable to connect to the web");
			return;
		}
		
		BufferedReader read = new BufferedReader(new FileReader(new File(
				"dataSnatcher/definitions/urlfile.txt")));

		ArrayList<String> defs = parseHTML(read);
		
		// write definitions to file
		writeToFile(defs,search);
		System.out.println("Definitions written to file");
	}

	/**
	 * Writes word and matching definitions to file...
	 * @param defs
	 */
	private void writeToFile(ArrayList<String> defs, String search) throws IOException{
		int count = 0;
		String filename = search+".txt"; // TODO change this to ".def" file 
		File path = new File("memory/dictionary/definition/");
		
		if(!path.exists()){
			path.mkdir();
		}
		
		File file = new File("memory/dictionary/definition/"+filename);
		FileWriter fw = new FileWriter(file);
		
		for(String s : defs){
			count++;
			fw.write(s+"\n");
		}
		
		if(count==0){
			fw.write("[[unknown]]");
		}
		
		fw.close();
	}

	private ArrayList<String> parseHTML(BufferedReader read) throws IOException {
		String start = "<div class=\"dndata\">";
		ArrayList<String> toReturn = new ArrayList<String>();
		String line;

		while ((line = read.readLine()) != null) {
			// need to extract lines in HTML file that contain the definition
			if (line.contains(start)) {
				String[] defs = line.split(start);

				// for each of the definitions, we need to remove all excess
				// tags
				for (int i = 1; i < defs.length; i++) {
					defs[i] = removeTags(defs[i]);
					defs[i] = cleanUp(defs[i]);
					toReturn.add(defs[i]);
				}
			}
		}
		return toReturn;
	}

	/**
	 * Consists of cleaning up the string...
	 * such as numbers at the end of each string
	 * @param string
	 * @return
	 */
	private String cleanUp(String string) {
		return string.substring(0, string.length()-3);
	}

	private String removeTags(String string) {
		//System.out.println("Old String: "+string);
		boolean tag = false;
		char c;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < string.length(); i++) {
			c = string.charAt(i);
			if (c == '<') {
				tag = true;
			} else if (c == '>') {
				tag = false;
			} else {
				if(tag)continue;
				sb.append(c);
			}
		}
		//System.out.println("New String: "+newString);
		return sb.toString();
	}

	/**
	 * Replaces any whitespace with a '+' character
	 * 
	 * @param search
	 */
	private String checkWhiteSpace(String search) {
		char[] c = search.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '+';
			}
		}
		return new String(c);
	}

	/**
	 * Clears all data in the data snatcher directory.
	 */
	private void clearSnatcherMemory() {
		File path = new File("dataSnatcher/definitions/");
		if (!path.exists()) {
			path.mkdir();
		}
		// System.out.println(path.exists());
		for (File f : path.listFiles()) {
			f.delete();
		}
	}

	public static void main(String[] args) throws Exception {
		new DefinitionSnatcher("apple");
	}

	public void fail(String text) {
		System.err.println("An error occured: " + text);
		System.exit(1);
	}
}