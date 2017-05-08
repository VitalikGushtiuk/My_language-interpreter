import java.util.HashMap;

public class EvaluationVisitor implements SyntaxTreeVisitor {
	private HashMap<Character, Variable> varList = new HashMap<>();
	
	private boolean returnCheck = false;
	private boolean varCheck = false;
	private boolean assertCheck = false;
	
	public boolean getReturnCheck() { return returnCheck; };
	public boolean getVarCheck() { return varCheck; };
	public boolean getAssertCheck() { return assertCheck; };
	
	@Override
	public int visit(AssertInstruction instr) {
		if(instr.left.accept(this) != 0) {
			assertCheck = true;
		}
		return 0;
	}

	@Override
	public int visit(ForInstruction instr) {
		instr.left.accept(this);
		while(instr.right.accept(this) == 0) {
			instr.secondOptional.accept(this);
			instr.firstOptional.accept(this);
		}
		return instr.secondOptional.accept(this);
	}

	@Override
	public int visit(IfInstruction instr) {
		if(instr.left.accept(this) == 0) {
			return instr.right.accept(this);
		}
		else {
			return instr.firstOptional.accept(this); 
		}
	}

	@Override
	public int visit(ReturnInstruction instr) {
		returnCheck = true;
		return retrieveValue(instr.left);
	}

	@Override
	public int visit(SemicolonInstruction instr) {
		retrieveValue(instr.left);
		return retrieveValue(instr.right);
	}

	@Override
	public int visit(AssignOperator opr) {
		char varName = (char)opr.left.accept(this);
		int val = opr.right.accept(this);
		if(varList.containsKey(varName)) {
			varList.remove(varName);
			varList.put(varName, new Variable(varName, val));
		}
		else {
			varList.put(varName, new Variable(varName, val));
		}
		return val;
	}

	@Override
	public int visit(EqualOperator opr) {
		int x = retrieveValue(opr.left);
		int y = retrieveValue(opr.right);
		
		if(x == y) return 0;
		return -1;
	}

	@Override
	public int visit(LessOperator opr) {
		int x = retrieveValue(opr.left);
		int y = retrieveValue(opr.right);
		
		if(x < y) return 0;
		return -1;
	}

	@Override
	public int visit(MultiplyOperator opr) {
		int x = retrieveValue(opr.left);
		int y = retrieveValue(opr.right);
		return x * y;
	}

	@Override
	public int visit(PlusOperator opr) {
		int x = retrieveValue(opr.left);
		int y = retrieveValue(opr.right);
		return x + y;
	}

	@Override
	public int visit(Symbol sym) {
		return sym.getCh();
	}

	@Override
	public int visit(Value val) {
		return val.getValue();
	}
	
	public int retrieveValue(TreeNode child) {
		int x = -1;
		if(child instanceof Symbol) {
			char varName = (char)(child.accept(this));
			if(!varList.containsKey(varName)) {
				varCheck = true;
			}
			else {
				x = varList.get(varName).getVarValue();
			}
		}
		else {
			x = child.accept(this);
		}
		return x;
	}
}
