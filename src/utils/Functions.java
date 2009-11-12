package utils;

import uml_entity_components.Visibility;

public class Functions {

	public static Visibility visibilityFromString(String string) {
		string = string.toUpperCase().trim();
		
		if(string.equals("PRIVATE") || string.equals("+"))
			return Visibility.PRIVATE;
		
		if(string.equals("PUBLIC"))
			return Visibility.PRIVATE;
		
		if(string.equals("PROTECTED"))
			return Visibility.PRIVATE;
		
		return Visibility.UNDEFINED;
	}
	
	public static String getVisibilityRepresentation(Visibility visibility) {
		switch(visibility) {
		case PRIVATE:	return "-";
		case PUBLIC:	return "+";
		case PROTECTED:	return "#";
		default:		return "";
		}
	}
	
	public static Visibility visibilityFromInt(int v) {
	    if((v & 1) != 0) {
		return Visibility.PUBLIC;
	    }
	    if((v & 2) != 0) {
		return Visibility.PROTECTED;
	    }
	    if((v & 4) != 0) {
		return Visibility.PRIVATE;
	    }
	    return Visibility.UNDEFINED;
	}
}
