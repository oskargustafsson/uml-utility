package base;

import japa.parser.*;
import japa.parser.ast.*;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.DumpVisitor;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.AbstractCollection;
import java.util.HashMap;

import uml_entities.Entity;
import uml_entities.SimpleInterface;
import uml_entities.UmlClass;
import uml_entity_components.Attribute;
import uml_entity_components.Operation;
import uml_entity_connectives.Association;
import uml_entity_connectives.Generalization;
import uml_entity_connectives.Realization;
import utils.Functions;

public class DirectoryParser {

	private static HashMap<String, Entity> classes;

	public static void generateUmlClassDiagram(File rootDir) {
		readDirectory(rootDir);

		classes = GUI.getInstance().getClassMap();

		// iterate a copy, so new elements can be added on the fly
		HashMap<String, Entity> shallowCopy = (HashMap<String, Entity>)classes.clone();
		for(Entity c : shallowCopy.values()) {
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
					Entity e = GUI.getInstance().addClass(file);
					//System.out.println("Added class" + e.getIdentifier());
				}
				if(file.isDirectory()) {
					readDirectory(file);
				}
			}
		}
	}

	public static void parseJava(Entity cl) {
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

		private Entity cl;

		public ASTVisitor(Entity cl) {
			super();
			this.cl = cl;
		}

		@Override
		public void visit(MethodDeclaration n, A arg) {
			//System.out.println("Method: " + n.getName() + "   " + n.getParameters());
			
			Object[] args = new Object[4];
			args[0] = Functions.visibilityFromInt(n.getModifiers());
			args[1] = n.getName();
			args[2] = n.getType().toString();
			args[3] = "";
			Operation operation = new Operation(args);
			for(Parameter parameter : n.getParameters()) {
				args[0] = Functions.visibilityFromInt(parameter.getModifiers());
				args[1] = parameter.getId() == null ? "" : parameter.getId().toString();
				args[2] = parameter.getType().toString();
				args[3] = "";
				operation.addArgument(new Attribute(args));
			}
			((UmlClass)cl).addOperation(operation);
		}

		@Override
		public void visit(FieldDeclaration n, A arg) {
			Object[] args = new Object[4];
			args[0] = Functions.visibilityFromInt(n.getModifiers());
			args[2] = n.getType().toString();

			try {
				if( classes.containsKey(args[2]) && !cl.getIdentifier().equals(args[2]) ) {
					// if local class, add association
					GUI.getInstance().addConnective(new Association(), cl, classes.get(args[2]));
				}
				else {
					// else, add attribute
					if(n.getVariables() != null) {
						for(VariableDeclarator d : n.getVariables()) {
							args[1] = d.getId() == null ? "" : d.getId().toString();
							args[3] = d.getInit() == null ? "" : d.getInit().toString();
							((UmlClass)cl).addAttribute(new Attribute(args)); 
						}
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}

			//System.out.println("Field: " + n.toString());
		}

		/*public void visit(TypeDeclarationStmt n, A arg) {
			System.out.println("type decl: " + n.getTypeDeclaration());
		}*/

		public void visit(ClassOrInterfaceDeclaration n, A arg) {
			try{
				if(n.getExtends() != null) {
					for(ClassOrInterfaceType t : n.getExtends() ) {
						if(classes.containsKey(t.getName()) && !cl.getIdentifier().equals(t.getName())) {
							//System.out.println(cl.getIdentifier() +" extends " + t.getName());
							GUI.getInstance().addConnective(new Generalization() ,cl, classes.get(t.getName()));
						}
					}
				}

				if(n.getImplements() != null) {
					for(ClassOrInterfaceType t : n.getImplements()) {
						if(classes.containsKey(t.getName())) {
							// if local class, add realization connective
							//System.out.println(cl.getIdentifier() +" implements " + t.getName());
							GUI.getInstance().addConnective(new Realization(), cl, classes.get(t.getName()));
						}
						else {
							// else, add a Simple Interface and a realization connective to it
							System.out.println(cl.getIdentifier() +" implements simple interface" + t.getName());
							SimpleInterface i = GUI.getInstance().addSimpleInterface(cl, t.getName());
							classes.put(i.getIdentifier(), i);
							GUI.getInstance().addConnective(new Realization(), cl, i);
						}
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}

			super.visit(n, arg);
		}
	}
}
