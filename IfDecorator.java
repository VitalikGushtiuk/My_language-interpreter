public class IfDecorator extends NodeDecorator {
	public IfDecorator(IfInstruction instr) {
		super(instr);
	}
	
	public int accept(SyntaxTreeVisitor visitor) {
		int retVal = this.decoratedNode.accept(visitor);
		return retVal;
	}
}
