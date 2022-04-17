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

public class LookupIndex {

	public static void main(String[] args) {
		try {
			//straight index - key->values
			Map<String, Set<String>> keyValues = new HashMap<String, Set<String>> ();
			//inverted index - value->keys
			Map<String, Set<String>> valueKeys = new HashMap<String, Set<String>> ();

			//read the file, a line at a time
			File content = new File(args[0]);
			String separator = args[1];
			FileReader reader = new FileReader(content);
			BufferedReader buffer = new BufferedReader(reader);
			String line;
			while((line = buffer.readLine()) != null) {
				//split line. token[0] is the key, and the following tokens are values
				String[] tokens = line.split(separator);
				String key = tokens[0];
				//if no key-value entry in map, insert one with empty value container
				if(!keyValues.containsKey(key)) {
					keyValues.put(tokens[0], new HashSet<String>());
				}
				//iterate over values
				for(int i = 1; i < tokens.length; i++) {
					String value = tokens[i];
					//if no value-key entry in map, insert one with empty key container
					if(!valueKeys.containsKey(value)) {
						valueKeys.put(value, new HashSet<String>());
					}
					//update the straight index
					keyValues.get(key).add(value);
					//update the inverted index
					valueKeys.get(value).add(key);
				}
			}
			System.out.println("Indexes created.");
			
			Scanner scanner = new Scanner(System.in);
			String input;
			while(!(input = scanner.nextLine()).isBlank()) {
				if(keyValues.containsKey(input)) {
					System.out.println(input);
					for(String value : keyValues.get(input)) {
						System.out.println(" " + value);
					}
				}
				if(valueKeys.containsKey(input)) {
					System.out.println(input);
					for(String key : valueKeys.get(input)) {
						System.out.println(" " + key);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
