package edu.hse.cs.tree;

public class ImmutableChildNode<T>
        extends AbstractTreeNode<T>
        implements ChildImmutable<T> {
    private final ParentImmutable<T> parent;

    ImmutableChildNode(T object, ParentImmutable<T> parent) {
        // TODO implement constructor
        super(object);
        this.parent = parent;
    }

    @Override
    public ParentImmutable<T> getParent() {
        // TODO implement getter
        return parent;
    }

    @Override
    public String toStringForm(String indent) {
        // TODO implement toStringForm
        String res = indent + this.getClass().getSimpleName() + "(" + getObject().getClass().getSimpleName() + ":" + getObject().toString() + ")" + "\n";
        return res;
    }
}
