package de.mschaedlich.logic.recognizer;

import java.util.HashMap;
import java.util.Map;

import de.mschaedlich.logic.recognizer.logic.AND;
import de.mschaedlich.logic.recognizer.logic.Atomic;
import de.mschaedlich.logic.recognizer.logic.InvertedAtomic;
import de.mschaedlich.logic.recognizer.logic.Logic;
import de.mschaedlich.logic.recognizer.logic.LogicParser;
import de.mschaedlich.logic.recognizer.logic.OR;

public class Main {

	public static void main(String[] args) {
		
		if(args.length > 0) {
			
			String function = args[0];
			Map<String, Boolean> assignment = new HashMap<String, Boolean>();
			
			
			/*
			for(int i = 1 ; i < args.length ; i++) {
				String parameter = args[i];
				String variable = parameter.split("=")[0];
				String value = parameter.split("=")[1];
				assignment.put(variable, Boolean.parseBoolean(value));
			}
			*/
			function = "(a&-b)|(-a&b)|(c&(-b|a&-(-c|b))";
			assignment.put("a", true);
			assignment.put("b", true);
			assignment.put("c", true);
			LogicParser parser = new LogicParser(function);
			for(String var : parser.getAtomicVars()) {
				System.out.println("AtomicVars: " + var);
			}
			Logic logic = parser.parse();
			
			if(logic != null) {
				boolean result = logic.getResult(assignment);
				System.out.println("Ergebnis: " + result);
			}
		}
	}
}
