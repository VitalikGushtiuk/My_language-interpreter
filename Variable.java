public class Variable extends TreeNode {
	private Value varValue;
	private Symbol varName;
	
	public Variable(char sym, int val) {
		this.varValue = new Value(val);
		this.varName = new Symbol(sym); 
	}
	
	public int getVarValue() { return varValue.getValue(); }
	public char getVarName() { return varName.getCh(); }
	
	public void setVarValue(Value varValue) { this.varValue = varValue; }
	public void setVarName(Symbol varName) { this.varName = varName; }
}
