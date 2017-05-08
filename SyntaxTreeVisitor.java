public interface SyntaxTreeVisitor {
	public int visit(AssertInstruction instr);
	public int visit(ForInstruction instr);
	public int visit(IfInstruction instr);
	public int visit(ReturnInstruction instr);
	public int visit(SemicolonInstruction instr);
	
	public int visit(AssignOperator opr);
	public int visit(EqualOperator opr);
	public int visit(LessOperator opr);
	public int visit(MultiplyOperator opr);
	public int visit(PlusOperator opr);
	
	public int visit(Symbol sym);
	public int visit(Value val);
	
}
