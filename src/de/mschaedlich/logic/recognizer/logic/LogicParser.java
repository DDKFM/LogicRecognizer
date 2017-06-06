package de.mschaedlich.logic.recognizer.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicParser {

	public static String prefix = "VAR_";
	private int currentVariableCount = 0;
	
	private Map<String, Logic> replacementsAsLogic = new HashMap<String, Logic>();
	private Logic lastLogic = null;
	
	private String input = "";
	private String rootInput = "";
	
	public LogicParser(String input) {
		this.input = input;
		this.rootInput = input;
	}
	
	public List<String> getAtomicVars() {
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(\\w|\\d)+");
		Matcher matcher = pattern.matcher(rootInput);
		while(matcher.find()) {
			String matchingElement = matcher.group();
			if(!list.contains(matchingElement))
				list.add(matchingElement);
		}
		return list;
	}
	public Logic parse() {
		boolean replaced = false;
		System.out.println("Input:\n\t" + input);
		do {
			replaced = this.replaceInverted();
			System.out.println("replaceInverted:\n\t" + this.input);
			replaced = this.replaceAND();
			System.out.println("replaceAND:\n\t" + this.input);
			replaced = replaceOR();
			System.out.println("replaceOR:\n\t" + this.input);
			replaced = replaceUnimportantParts();
			System.out.println("replaceUnimportantParts:\n\t" + this.input);
		} while (replaced);
		
		return lastLogic;
	}
	private boolean replaceUnimportantParts() {
		Pattern pattern = Pattern.compile("\\((\\w|\\d|_)+\\)");
		Matcher matcher = pattern.matcher(input);
		boolean replaced = false;
		while(matcher.find()) {
			replaced = true;			
			String expression = matcher.group().substring(1, matcher.group().length() - 1);
			input = input.replace(matcher.group(), expression);
		}
		return replaced;
	}
	
	private boolean replaceInverted() {
		Pattern pattern = Pattern.compile("\\-(\\w|\\d|_)+");
		Matcher matcher = pattern.matcher(input);
		boolean replaced = false;
		while (matcher.find()) {
			replaced = true;
		    String newVariableName = prefix + currentVariableCount;
		    String variableName = matcher.group().substring(1);
		    replacementsAsLogic.put(newVariableName, new InvertedAtomic(variableName));
		    this.lastLogic = replacementsAsLogic.get(newVariableName);
		    currentVariableCount++;
		    input = input.replace(matcher.group(), newVariableName);
		}
		return replaced;
	}
	private boolean replaceAND() {
		Pattern pattern = Pattern.compile("(\\w|\\d|_)+(&(\\w|\\d|_)+)+");
		Matcher matcher = pattern.matcher(input);
		boolean replaced = false;
		while(matcher.find()) {
			replaced = true;
			String newVariableName = prefix + currentVariableCount;
			String[] vars = matcher.group().replace(" ", "").split("&");
			List<Logic> andElements = new ArrayList<Logic>();
			for(String var : vars) {
				if(replacementsAsLogic.containsKey(var)) {
					andElements.add(replacementsAsLogic.get(var));
					
				} else {
					andElements.add(new Atomic(var));
				}
			}
			replacementsAsLogic.put(newVariableName, new AND(newVariableName, andElements));
			this.lastLogic = replacementsAsLogic.get(newVariableName);
		    currentVariableCount++;
		    input = input.replace(matcher.group(), newVariableName);
		}
		return replaced;
	}
	private boolean replaceOR() {
		Pattern pattern = Pattern.compile("(\\w|\\d|_)+(\\|(\\w|\\d|_)+)+");
		Matcher matcher = pattern.matcher(input);
		boolean replaced = false;
		while(matcher.find()) {
			replaced = true;
			String newVariableName = prefix + currentVariableCount;
			String[] vars = matcher.group().replace(" ", "").split("\\|");
			List<Logic> andElements = new ArrayList<Logic>();
			for(String var : vars) {
				if(replacementsAsLogic.containsKey(var)) {
					andElements.add(replacementsAsLogic.get(var));
				} else {
					andElements.add(new Atomic(var));
				}
			}
			replacementsAsLogic.put(newVariableName, new OR(newVariableName, andElements));
			this.lastLogic = replacementsAsLogic.get(newVariableName);
		    currentVariableCount++;
		    input = input.replace(matcher.group(), newVariableName);
		}
		return replaced;
	}
}
