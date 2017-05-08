public class ForInstruction extends TreeNode {
	public int accept(SyntaxTreeVisitor visitor) {
		return visitor.visit(this);
	}
}
