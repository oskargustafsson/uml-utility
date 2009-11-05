package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.reflect.*;

import uml_entities.UmlClass;

public class JavaParser {

	private static Pattern 
	regPackage = Pattern.compile("package (.*);", Pattern.MULTILINE), 
	regAttribute = Pattern.compile(
			"((public|private|protected|static|final)(?:\\s+))*" +	// modifier
			"([A-Za-z0-9]+)(?:\\s*)" +								// type
			"(([A-Za-z0-9]+)(?:\\s*)" +								// name
			"(?:=\\s*)(.+)?[,;])+", Pattern.MULTILINE),				// value
			regOperation = Pattern.compile("", Pattern.MULTILINE);

	public static void parseDirectory(File dir) {

		for(File file : dir.listFiles()) {
			if(file.isFile() && file.getName().endsWith(".java")) {
				parseJava(GUI.getInstance().addClass(file));
			}
			if(file.isDirectory()) {
				parseDirectory(file);
			}
		}
	}

	private static void parseJava(UmlClass umlClass) {
		File source = umlClass.getSourceFile();
		String contents = getContents(source);

	}

	private static String getContents(File aFile) {
		//...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			//use buffering, reading one line at a time
			//FileReader always assumes default encoding is OK!
			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try {
				String line = null; //not declared within while loop
				/*
				 * readLine is a bit quirky :
				 * it returns the content of a line MINUS the newline.
				 * it returns null only for the END of the stream.
				 * it returns an empty String if two newlines appear in a row.
				 */
				while (( line = input.readLine()) != null){
					contents.append(line);
					contents.append(System.getProperty("line.separator"));

					Scanner scanner = new Scanner(line);
					scanner.useDelimiter(" ");
					while ( scanner.hasNext() ){
						String name = scanner.next();
						System.out.println(" " + name);
					}
					//(no need for finally here, since String is source)
					scanner.close();
				}
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return contents.toString();
	}



	public static void dumpMethods(Class c) {

		try {
			Method methods[] = c.getDeclaredMethods();
			for (Method method : methods) {
				System.out.println(method.toString());
			}
		}
		catch (Throwable e) {
			System.err.println(e);
		}
	}

}
