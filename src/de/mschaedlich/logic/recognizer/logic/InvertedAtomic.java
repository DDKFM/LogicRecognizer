package de.mschaedlich.logic.recognizer.logic;

import java.util.Map;

public class InvertedAtomic extends Atomic {

	public InvertedAtomic(String name) {
		super(name);
	}
	
	@Override
	public boolean getResult(Map<String, Boolean> inputMap) {
		return !super.getResult(inputMap);
	}

}
