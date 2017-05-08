public class ReturnDecorator extends NodeDecorator {
	public ReturnDecorator(ReturnInstruction instr) {
		super(instr);
	}
	
	public int accept(SyntaxTreeVisitor visitor) {
		int retVal = this.decoratedNode.accept(visitor);
		return retVal;
	}
}
