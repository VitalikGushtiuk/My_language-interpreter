import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String []argv) {
		String program = Main.getProgram(argv[0]);
		SyntaxTree testTree = SyntaxTree.parse(program);
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(argv[1]));
			testTree.evaluateTree(writer);
			writer.close();
		} catch (IOException e) {
			System.out.println("Unable to open output file");
		}
	}
	
	
	public static String getProgram(String filename){
		BufferedReader reader = null;
		FileInputStream is = null;
		String buffer = "";
		String cutted = "";
		String program = new String();
		try {
			is = new FileInputStream(filename);
			reader = new BufferedReader(new InputStreamReader(is));
		}catch(FileNotFoundException e){
			System.out.println("File not found");
		}
		
		
		try {
			buffer = reader.readLine();
			program += buffer;
			
			while((buffer = reader.readLine()) != null) {
				cutted = buffer.replace("\t", "");
				
				if(!cutted.equals("]") && !cutted.isEmpty()) program += " ";
				
				program = program + cutted;
			}
		} catch(IOException e){
			System.out.println("Unable to read from file");
		}
		try {
			reader.close();
		} catch (IOException e) {
			System.out.println("Unable to close file");
		}
		return program;
	}
}
