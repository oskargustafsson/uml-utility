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
}
