package de.mschaedlich.logic.recognizer.logic;

import java.util.Map;

public class Logic {
	protected String name;
	
	public Logic(String name) {
		this.name = name;
	}
	
	public boolean getResult(Map<String, Boolean> inputMap) {
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
