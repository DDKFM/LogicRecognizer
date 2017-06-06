package de.mschaedlich.logic.recognizer.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AND extends Logic {

	private Map<String, Logic> elements = new HashMap<String, Logic>();
	public AND(String name, Logic...elements) {
		super(name);
		for(Logic element: elements) {
			this.elements.put(element.getName(), element);
		}
	}
	public AND(String name, List<Logic> elements) {
		super(name);
		for(Logic element: elements) {
			this.elements.put(element.getName(), element);
		}
	}

	@Override
	public boolean getResult(Map<String, Boolean> inputMap) {
		boolean result = true;
		for(Logic element : elements.values()) {
			result &= element.getResult(inputMap);
		}
		return result;
	}
	@Override
	public String toString() {
		String result = "";
		for(Logic element : elements.values()) {
			result += element.getName() + " & "; 
		}
		result = result.substring(0, result.length() - 3);
		return result;
	}
	
}
