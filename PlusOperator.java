public class PlusOperator extends TreeNode {
	
	public int accept(SyntaxTreeVisitor visitor) {
		return visitor.visit(this);
	}
}
