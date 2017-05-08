import java.io.BufferedWriter;
import java.io.IOException;

public class SyntaxTree {
	TreeNode root;

	public static SyntaxTree parse(String program) {
		SyntaxTree t = new SyntaxTree();
		t.root = SyntaxTree.parse(t.root, program);
		return t;
	}
	
	public int evaluateTree(BufferedWriter logWriter) {
		if(root.equals(null)){
			return 0;
		}
		
		EvaluationVisitor visitor = new EvaluationVisitor();
		int returnValue = root.accept(visitor);
		
		try {
			if(visitor.getVarCheck()) {
				logWriter.write("Check failed");
			}
			else if(!visitor.getReturnCheck()){
				logWriter.write("Missing return");
			}
			else if(visitor.getAssertCheck()){
				logWriter.write("Assert failed");
			}
			else {
				logWriter.write(Integer.toString(returnValue));
			}
		}catch(IOException ex){
			System.out.println("Unable to write in log file");
		}
		return 0;
	}

	private static TreeNode parse(TreeNode currentNode, String currentProgram) {
		if(currentProgram.equals(null)){
			System.out.println("Invalid program");
			return null;
		}
		char ch = currentProgram.charAt(1);
		Pair<Integer> exprBounds = null;
		String subprograms = null;
		
		switch (ch) {
		case '+':
			currentNode = new PlusOperator();
			currentNode = expressionParser(currentNode, currentProgram);
			break;
		case '<':
			currentNode = new LessOperator();
			currentNode = expressionParser(currentNode, currentProgram);
			break;
		case '*':
			currentNode = new MultiplyOperator();
			currentNode = expressionParser(currentNode, currentProgram);
			break;
		case '=':
			if(currentProgram.charAt(2) == '=') {
				currentNode = new EqualOperator();
				currentNode = expressionParser(currentNode, currentProgram);
				break;
			}
			else {
				currentNode = new AssignOperator();
				currentNode = expressionParser(currentNode, currentProgram);
				break;
			}
		case 'f':
			currentNode = new ForInstruction();
			
			exprBounds = getNextBounds(currentProgram);
			currentNode.left = parse(currentNode.left, currentProgram.substring(exprBounds.getFirst(), exprBounds.getSecond() + 1));
			
			subprograms = currentProgram.substring(exprBounds.getSecond() + 1, currentProgram.length() - 1);
			exprBounds = getNextBounds(subprograms);
			currentNode.right = parse(currentNode.right, subprograms.substring(exprBounds.getFirst(), exprBounds.getSecond() + 1));
			
			subprograms = subprograms.substring(exprBounds.getSecond() + 1, subprograms.length());
			exprBounds = getNextBounds(subprograms);
			currentNode.firstOptional = parse(currentNode.firstOptional, subprograms.substring(exprBounds.getFirst(), exprBounds.getSecond() + 1));
			
			exprBounds.setFirst(exprBounds.getSecond() + 2);
			exprBounds.setSecond(subprograms.length());
			currentNode.secondOptional = parse(currentNode.secondOptional, subprograms.substring(exprBounds.getFirst(), exprBounds.getSecond()));
			
			break;
		case 'i':
			currentNode = new IfInstruction();
			
			exprBounds = getNextBounds(currentProgram);
			currentNode.left = parse(currentNode.left, currentProgram.substring(exprBounds.getFirst(), exprBounds.getSecond() + 1));
			
			subprograms = currentProgram.substring(exprBounds.getSecond() + 1, currentProgram.length() - 1);
			exprBounds = getNextBounds(subprograms);
			currentNode.right = parse(currentNode.right, subprograms.substring(exprBounds.getFirst(), exprBounds.getSecond() + 1));
			
			exprBounds.setFirst(exprBounds.getSecond() + 2);
			exprBounds.setSecond(subprograms.length());
			currentNode.firstOptional = parse(currentNode.firstOptional, subprograms.substring(exprBounds.getFirst(), exprBounds.getSecond()));
			break;
		case 'a':
			currentNode = new AssertInstruction();
			if(currentProgram.charAt(8) == '[')
				currentNode.left = parse(currentNode.left, currentProgram.substring(8, currentProgram.length() - 1));
			else
				currentNode.left = new Symbol(currentProgram.charAt(8));
			break;
		case 'r':
			currentNode = new ReturnInstruction();
			if(currentProgram.charAt(8) == '[')
				currentNode.left = parse(currentNode.left, currentProgram.substring(8, currentProgram.length() - 1));
			else
				currentNode.left = new Symbol(currentProgram.charAt(8));
			break;
		case ';':
			currentNode = new SemicolonInstruction();
			
			exprBounds = getNextBounds(currentProgram);
			currentNode.left = parse(currentNode.left, currentProgram.substring(exprBounds.getFirst(), exprBounds.getSecond() + 1));
			
			currentNode.right = parse(currentNode.right, currentProgram.substring(exprBounds.getSecond() + 2, currentProgram.length() - 1));
			break;
		}
		return currentNode;
	}
	
	private static TreeNode expressionParser(TreeNode currentNode, String currentProgram) {
		boolean leftExecuted = false;
		
		if(Character.isAlphabetic(currentProgram.charAt(0))){
			currentNode.left = new Symbol(currentProgram.charAt(0));
			return currentNode;
		}
		else if(Character.isDigit(currentProgram.charAt(0))){
			currentNode.left = new Value(Character.getNumericValue(currentProgram.charAt(0)));
			return currentNode;
		}
		
		for(int i = 1; i < currentProgram.length(); i++) {
			if(Character.isAlphabetic(currentProgram.charAt(i))) {
				if(!leftExecuted) {
					currentNode.left = new Symbol(currentProgram.charAt(i));
					leftExecuted = true;
				}
				else {
					currentNode.right = new Symbol(currentProgram.charAt(i));
					break;
				}
			}
			else if(Character.isDigit(currentProgram.charAt(i))) {
				if(!leftExecuted) {
					currentNode.left = new Value(Integer.valueOf(currentProgram.substring(i, i = currentProgram.indexOf(' ', i))));
					leftExecuted = true;
				}
				else {
					currentNode.right = new Value(Integer.valueOf(currentProgram.substring(i, i = currentProgram.indexOf(']', i))));
					break;
				}
			}
			else if(currentProgram.charAt(i) == '[') {
				if(!leftExecuted) {
					currentNode.left = parse(currentNode.left, currentProgram.substring(i, currentProgram.indexOf(']', i) + 1));
					i = currentProgram.indexOf(']', i);
					leftExecuted = true;
				}
				else {
					currentNode.right = parse(currentNode.right, currentProgram.substring(i, currentProgram.indexOf(']', i) + 1));
					break;
				}
			}
		}
		return currentNode;
	}
	private static Pair<Integer> getNextBounds(String currentProgram) {
		if(currentProgram.equals(null)) {
			System.out.println("getNextBounds issue");
			return null;
		}
		Pair<Integer> bounds = new Pair<>();
		// bounds of expression that will be parsed
		bounds.setFirst(currentProgram.indexOf('[', 1));
		// find where it starts
		int searchRear = 1;
		// set counter 1 and looking through the program to find where she ends 
		
		for(int i = bounds.getFirst() + 1; i < currentProgram.length(); i++) {
			if(currentProgram.charAt(i) == '[') searchRear++;
			else if(currentProgram.charAt(i) == ']') searchRear--;
			if(searchRear == 0) {
				bounds.setSecond(i);
				break;
			}
		}
		return bounds;
	}
}
