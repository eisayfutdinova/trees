package edu.hse.cs.tree;

public class MutableChildNode<T>
        extends AbstractTreeNode<T>
        implements ChildMutable<T> {

    private ParentMutable<T> parent;

    MutableChildNode(T object) {
        super(object);
        // TODO implement ctor
    }

    @Override
    public ParentMutable<T> getParent() {
        // TODO implement getter
        return parent;
    }

    @Override
    public void setParent(ParentMutable<T> newParent) {
        // TODO implement setter
        parent = newParent;
    }

    @Override
    public String toStringForm(String indent) {
        // TODO implement toStringForm
        String res = indent + this.getClass().getSimpleName() + "(" + getObject().getClass().getSimpleName() + ":" + getObject().toString() + ")" + "\n";
        return res;
    }
}
