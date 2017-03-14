package ultility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class ReadWriteTrace {

	private static final String TAG = "ReadWriteTrace";
	private static final boolean LOG = false;
	
	
	/*given a file path, read the traces and return*/
	static public List<String> readFile(String filePath, int dim) {
		List<String> data = new ArrayList<String>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr); 
			String line; 
			while((line = br.readLine()) != null) {
				String record = new String(line);
				data.add(record);
			} 
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(TAG, e.toString());
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;  
	}
	
	
	/*write traces into a file*/
	public static void writeFile(List<String> records, String filePath) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filePath));
			for (String tr: records) {
				String line = tr.toString() + "\n";
				bw.write(line);	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	

}

