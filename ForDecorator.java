public class ForDecorator extends NodeDecorator {
	public ForDecorator(ForInstruction instr) {
		super(instr);
	}
	
	public int accept(SyntaxTreeVisitor visitor) {
		int retVal = this.decoratedNode.accept(visitor);
		return retVal;
	}
}
