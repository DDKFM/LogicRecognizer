package de.mschaedlich.logic.recognizer.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OR extends Logic {

	private Map<String, Logic> elements = new HashMap<String, Logic>();
	public OR(String name, Logic...elements) {
		super(name);
		for(Logic element: elements) {
			this.elements.put(element.getName(), element);
		}
	}

	public OR(String name, List<Logic> elements) {
		super(name);
		for(Logic element: elements) {
			this.elements.put(element.getName(), element);
		}
	}
	
	@Override
	public boolean getResult(Map<String, Boolean> inputMap) {
		boolean result = false;
		for(Logic element : elements.values()) {
			if(element instanceof AND || element instanceof OR) {
				result |= element.getResult(inputMap);
			} else {
				result |= inputMap.get(element.getName());
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(Logic element : elements.values()) {
			result += element.getName() + " | "; 
		}
		result = result.substring(0, result.length() - 3);
		return result;
	}
}
