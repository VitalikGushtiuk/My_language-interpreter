public abstract class NodeDecorator extends TreeNode implements VisitableNode {
	protected TreeNode decoratedNode;
	
	public NodeDecorator(TreeNode decoratedNode) {
		this.decoratedNode = decoratedNode;
	}
	
	public int accept(SyntaxTreeVisitor visitor) { return -1; }
}
