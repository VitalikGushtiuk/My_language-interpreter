public class ReturnInstruction extends TreeNode {
	public int accept(SyntaxTreeVisitor visitor) {
		return visitor.visit(this);
	}
}
