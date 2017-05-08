public class Symbol extends TreeNode {
	private char ch;
	
	public Symbol(char ch){ this.ch = ch; }

	public char getCh() { return ch; }

	public void setCh(char ch) { this.ch = ch; }

	public int accept(SyntaxTreeVisitor visitor) {
		return visitor.visit(this);
	}
}
