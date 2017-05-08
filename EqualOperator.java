public class EqualOperator extends TreeNode {
	public int accept(SyntaxTreeVisitor visitor) {
		return visitor.visit(this);
	}
}
