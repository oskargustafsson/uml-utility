package base;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class JavaParser {

    public static void parse(File dir) {

	File[] children = dir.listFiles();

	if (children == null) {
	    // Either dir does not exist or is not a directory
	    System.out.println("No children");
	} else {
	    for(File file : children) {
		if(file.isFile() && file.getName().endsWith(".java")) {
		    GUI.getInstance().addClass(file);
		}
		if(file.isDirectory()) {
		    parse(file);
		}
	    }
	}
    }

}
