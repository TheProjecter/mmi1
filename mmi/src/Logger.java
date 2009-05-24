//package mmi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Gathers and writes the results of the tests.
 * 
 * @author Shterev
 * 
 */
public final class Logger {
	
//	/**
//	 * The singleton instance.
//	 */
//	public static final Logger INSTANCE = new Logger();

	private HashMap<String, ArrayList<Testcase>> data;
	private String valueSeparator = ";";
	private String lineFeed = System.getProperty("line.separator");

	public Logger() {
		data = new HashMap<String, ArrayList<Testcase>>();
	}

	/**
	 * Adds a new testcase result set to the database of the logger.
	 * 
	 * @param user
	 *            the user
	 * @param testcase
	 *            the testcase
	 */
	public void addTestcase(String user, Testcase testcase) {
		ArrayList<Testcase> arrayList = data.get(user);
		if (arrayList == null) {
			arrayList = new ArrayList<Testcase>();
			data.put(user, arrayList);
		}

		arrayList.add(testcase);
	}

	/**
	 * Flushes the buffer of the Logger to a given path.
	 * @param path the path.
	 */
	public void flush(String path){
		for(String user : data.keySet()){
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(path+File.separator+user+".csv"));
				writer.write(getHeader());
				for(Testcase t : data.get(user)){
					try{
						writer.write(toString(t));
						writer.flush();
					} catch  (IOException e){
						e.printStackTrace();
					}
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		data.clear();
	}

	private String getHeader() {
		String line	= "TestId" + valueSeparator
		+ "TunnelId" + valueSeparator
		+ "MouseSpeed" + valueSeparator
		+ "Height" + valueSeparator
		+ "Width" + valueSeparator
		+ "Time" + valueSeparator
		+ "User" + valueSeparator
		+ "Gender" + valueSeparator
		+ "ErrorCount" + lineFeed;
		
		return line;
	}
	
	private String toString(Testcase t) {
		SimpleDateFormat format = new SimpleDateFormat("mm:ss:SS");
		
		String line	= t.getTestId() + valueSeparator
					+ t.getTunnelId() + valueSeparator
					+ t.getMouseSpeed() + valueSeparator
					+ t.getTunnelSize().getHeight() + valueSeparator
					+ t.getTunnelSize().getWidth() + valueSeparator
					+ format.format(t.getTime()) + valueSeparator
					+ t.getUserName() + valueSeparator
					+ t.getUserGender() + valueSeparator
					+ t.getErrorCount() + lineFeed;
		
		return line;
	}
}
