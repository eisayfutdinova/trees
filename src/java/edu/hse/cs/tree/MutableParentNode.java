package edu.hse.cs.tree;

import java.util.*;

/**
 * Implementation of mutable parent node.
 * Parent node represents an inner node and a root node as well.
 *
 * @param <T> - type of wrapper object.
 */
public class MutableParentNode<T>
        extends MutableChildNode<T>
        implements ParentMutable<T>, ChildMutable<T> {
    private Set<ChildMutable<T>> children;

    MutableParentNode(T object) {
        super(object); // stub
        // TODO implement ctor

    }

    // ParentMutable implementation:
    @Override
    public Set<? extends ChildMutable<T>> getChildren() {
        // TODO implement getter
        return children;
    }

    @Override
    public void setChildren(Set<? extends ChildMutable<T>> children) {
        // TODO implement children set reassignment
        this.children = new LinkedHashSet<>();
        for (ChildMutable<T> child : children) {
            this.children.add(child);
            child.setParent(this);
        }
    }

    @Override
    public boolean addChild(ChildMutable<T> child) {
        // TODO implement child adding

        if (child == null) // if nothing to add
            return false;

        if (children == null) // if there are no children yet
            this.children = new LinkedHashSet<>();

        children.add(child);
        child.setParent(this);
        return true;
    }

    @Override
    public boolean removeChild(ChildMutable<T> child) {
        // TODO implement child removing

        for (ChildMutable<T> olive : children)  // go through the list of children
        {
            if (olive instanceof MutableParentNode && olive.getObject().equals(child.getObject())) // if we found child and it's parent
            {
                for (ChildMutable<T> ch : ((MutableParentNode<T>) olive).getAllDescendants()) // remove its descendants
                    ((MutableParentNode<T>) olive).children.remove(ch);
                children.remove(olive); // remove olive
                return true;
            } else if (olive instanceof MutableChildNode && olive.getObject().equals(child.getObject())) { // if we found and it's just child
                children.remove(olive); // remove it
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<? extends ChildMutable<T>> getAllDescendants() {
        // TODO implement tree traversing and collecting
        Collection<ChildMutable<T>> descendants = new LinkedHashSet<>();
        for (ChildMutable<T> child : children) // go through the list of children
        {
            descendants.add(child);
            if (child instanceof ParentMutable) // if it has children
                descendants.addAll(((ParentMutable<T>) child).getAllDescendants()); // recall this function to find descendant
        }

        return descendants;
    }

    @Override
    public boolean hasChildWithValue(T childValue) {
        // TODO implement collection search
        for (ChildMutable<T> child : children)
            if (child.getObject().equals(childValue))
                return true;

        return false;
    }

    @Override
    public boolean hasDescendantWithValue(T childValue) {
        // TODO implement recursive search
        for (ChildMutable<T> child : children) {
            if (child.getObject().equals(childValue)) // if found
                return true;
            else if (child instanceof MutableParentNode) // else if it has children
            {
                if (((MutableParentNode<Object>) child).hasDescendantWithValue(childValue)) // recursive call
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
        if (this.children != null) {
            for (ChildMutable<T> child : getChildren()) {
                if (child instanceof MutableParentNode)
                    res += ((MutableParentNode<T>) child).toStringForm(indent);
                else
                    res += ((MutableChildNode<T>) child).toStringForm(indent);
            }
        }
        return res;
    }
}
