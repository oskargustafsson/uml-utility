package base;

import japa.parser.*;
import japa.parser.ast.*;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.visitor.DumpVisitor;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.AbstractCollection;
import java.util.HashMap;

import uml_entities.UmlClass;
import uml_entity_components.Attribute;
import utils.Functions;

public class DirectoryParser {

    private static HashMap<String, UmlClass> classes;

    public static void generateUmlClassDiagram(File rootDir) {
	readDirectory(rootDir);

	classes = GUI.getInstance().getClassMap();
	
	for(UmlClass c : classes.values()) {
	    parseJava(c);
	}
    }

    public static void readDirectory(File dir) {

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
		    readDirectory(file);
		}
	    }
	}
    }

    public static void parseJava(UmlClass cl) {
	try {
	    // creates an input stream for the file to be parsed
	    FileInputStream in = new FileInputStream(cl.getSourceFile());

	    CompilationUnit cu;
	    try {
		// parse the file
		cu = JavaParser.parse(in);
	    } finally {
		in.close();
	    }

	    new ASTVisitor(cl).visit(cu, null);

	}
	catch(Exception e) {}
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes. 
     */
    private static class ASTVisitor<A> extends VoidVisitorAdapter<A> {

	private UmlClass cl;

	public ASTVisitor(UmlClass cl) {
	    super();
	    this.cl = cl;
	}

	@Override
	public void visit(MethodDeclaration n, A arg) {
	    //System.out.println("Method: " + n.getName() + "   " + n.getParameters());
	}

	@Override
	public void visit(FieldDeclaration n, A arg) {
	    Object[] args = new Object[4];
	    args[0] = Functions.visibilityFromInt(n.getModifiers());
	    args[2] = n.getType().toString();

	    try {
		if( classes.containsKey(args[2]) && !cl.getIdentifier().equals(args[2]) ) {
		    GUI.getInstance().addConnective(cl, classes.get(args[2]));
		    //System.out.println("Added conn: " + cl.getIdentifier() + " to " + classes.get(args[2]).getIdentifier());
		}
		else {
		    for(VariableDeclarator d : n.getVariables()) {
			args[1] = d.getId() == null ? "" : d.getId().toString();
			args[3] = d.getInit() == null ? "" : d.getInit().toString();
			cl.addAttribute(new Attribute(args)); 
		    }
		}
	    }
	    catch(Exception e) {
		e.printStackTrace();
	    }

	    //System.out.println("Field: " + n.toString());
	}

	public void visit(TypeDeclarationStmt n, A arg) {
	    System.out.println("type decl: " + n.getTypeDeclaration());
	}
    }
}
