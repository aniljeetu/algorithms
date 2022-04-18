package indexing.inverted;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class FileIndex {

	public static void main(String[] args) {
		//split delimiters
		String splitDelimiters = ":;,- ";
		//inverted index - keyword->filenames
		Map<String, Set<String>> keyFiles = new HashMap<String, Set<String>>();

		//prepare the inverted index
		try {
			//for each file
			for(String filename: args) {
				BufferedReader buffer = new BufferedReader(new FileReader(new File(filename)));
				String line;
				//for each line
				while((line = buffer.readLine()) != null) {
					String[] tokens = StringUtils.split(line, splitDelimiters);
					//for each word
					for(String token: tokens) {
						//if word is not indexed, create an index entry
						if(!keyFiles.containsKey(token)) {
							keyFiles.put(token, new HashSet<String>());
						}
						//add filename to the word index
						keyFiles.get(token).add(filename);
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//use the inverted index
		Scanner scanner = new Scanner(System.in);
		String input;
		while(!(input = scanner.nextLine()).isBlank()) {
			if(keyFiles.containsKey(input)) {
				for(String filename: keyFiles.get(input)) {
					System.out.print(filename + " ");
				}
				System.out.println();
			}
		}
	}

}
