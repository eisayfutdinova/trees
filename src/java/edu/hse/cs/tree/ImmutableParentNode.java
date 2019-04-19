package edu.hse.cs.tree;

import javafx.scene.Parent;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ImmutableParentNode<T>
        extends ImmutableChildNode<T>
        implements ParentImmutable<T>, ChildImmutable<T> {
    private Set<ChildImmutable<T>> children;

    /**
     * Creates an immutable tree that is a copy of mutable tree represented by {@link MutableParentNode}.
     *
     * @param source a root of mutable tree (does not have a parent).
     * @throws IllegalArgumentException if source is not a root node ({@param source} has a parent)
     */
    public ImmutableParentNode(MutableParentNode<T> source) {
        super(source.getObject(), null); //
        MutToImmut(source.getChildren());
    }

    /**
     * Creates immutable node with specified parent based on {@link MutableParentNode}.
     *
     * @param source mutable node which is base for the new immutable one
     * @param parent parent of new immutable node
     */
    private ImmutableParentNode(MutableParentNode<T> source, ParentImmutable<T> parent) {
        super(source.getObject(), parent); //
        // TODO implement private constructor
        MutToImmut(source.getChildren());

    }

    /**
     * Create ImmutableTree from Mutable
     *
     * @param mutChildren
     */
    public void MutToImmut(Set<? extends ChildMutable<T>> mutChildren) {
        Set<ChildImmutable<T>> olives = new LinkedHashSet<>(); // create to add Immutable objects

        if (mutChildren != null) {
            for (ChildMutable<T> child : mutChildren) {
                if (child instanceof MutableParentNode) {
                    ImmutableParentNode<T> newIm = new ImmutableParentNode<>((MutableParentNode<T>) child, this); // create new ImmutableParent using private constructor
                    olives.add(newIm);
                } else {
                    ImmutableChildNode<T> newCh = new ImmutableChildNode<>(child.getObject(), this);
                    olives.add(newCh);
                }
            }
            children = olives;
        }
    }

    @Override
    public Set<? extends ChildImmutable<T>> getChildren() {
        // TODO implement getter
        return children;
    }

    @Override
    public Collection<? extends ChildImmutable<T>> getAllDescendants() {
        // TODO implement tree traversing and collecting
        Collection<ChildImmutable<T>> descendants = new LinkedHashSet<>(); // out descendants
        for (ChildImmutable<T> child : children) { // go through the list of children
            descendants.add(child); // add out child
            if (child instanceof ParentImmutable) // if child has children
                descendants.addAll(((ParentImmutable<T>) child).getAllDescendants()); // add its children by means of a recursive call of this method
        }

        return descendants;
    }

    @Override
    public boolean hasChildWithValue(T childValue) {
        // TODO implement collection search
        for (ChildImmutable<T> child : children) { // go through the list of children
            if (child.getObject().equals(childValue)) // if it's found
                return true;
        }
        return false;
    }

    @Override
    public boolean hasDescendantWithValue(T childValue) {
        for (ChildImmutable<T> child : children) { // go through the list of children
            if (child.getObject().equals(childValue))  // if it's found
                return true;
            else if (child instanceof ImmutableParentNode) { // check children of child
                if (((ImmutableParentNode<Object>) child).hasDescendantWithValue(childValue)) // recursive call
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toStringForm(String indent) {
        // TODO implement toStringForm
        String res = indent + getClass().getSimpleName() + "(" + getObject().getClass().getSimpleName() + ":" + getObject().toString() + ")" + "\n";
        indent += "    ";

        if (children != null) {
            for (ChildImmutable<T> child : getChildren()) {
                if (child instanceof ImmutableParentNode)
                    res += ((ImmutableParentNode<T>) child).toStringForm(indent);
                else
                    res += ((ImmutableChildNode<T>) child).toStringForm(indent);
            }
        }
        return res;
    }
}
