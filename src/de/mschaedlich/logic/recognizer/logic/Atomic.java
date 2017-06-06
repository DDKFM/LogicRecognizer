package de.mschaedlich.logic.recognizer.logic;

import java.util.Map;

public class Atomic extends Logic{

	public Atomic(String name) {
		super(name);
	}
	@Override
	public boolean getResult(Map<String, Boolean> inputMap) {
		if(inputMap != null && inputMap.containsKey(this.name)) {
			return inputMap.get(this.name);
		}
		return false;
	}
	@Override
	public String toString() {
		return "-" + name;
	}
}
