public class AssertDecorator extends NodeDecorator {
	public AssertDecorator(AssertInstruction instr) {
		super(instr);
	}
	
	public int accept(SyntaxTreeVisitor visitor) {
		int retVal = this.decoratedNode.accept(visitor);
		return retVal;
	}
}
