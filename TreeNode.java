public abstract class TreeNode implements VisitableNode {
	TreeNode left;
	TreeNode right;
	TreeNode firstOptional;
	TreeNode secondOptional;
	
	public int accept(SyntaxTreeVisitor visitor){ return -1; };
	public TreeNode() {};
}
