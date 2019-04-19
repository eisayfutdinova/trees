package edu.hse.cs.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImmutableParentNodeTest {


    @Test
    void hasChildWithValue() {
        ImmutableParentNode<String> root = checkCopyConstructor();
        Assertions.assertEquals(root.hasChildWithValue("Parent0"), true);
    }

    @Test
    void hasDescendantWithValue() {
        ImmutableParentNode<String> root = checkCopyConstructor();
        Assertions.assertEquals(root.hasDescendantWithValue("Child00"), true);
    }

    @Test
    void toStringForm() {
        ImmutableParentNode<String> root = checkCopyConstructor();
        String str = "ImmutableParentNode(String:Root)\n" +
                "    ImmutableParentNode(String:Parent0)\n" +
                "        ImmutableChildNode(String:Child00)\n" +
                "        ImmutableChildNode(String:Child01)\n" +
                "    ImmutableParentNode(String:Parent1)\n" +
                "        ImmutableChildNode(String:Child10)\n" +
                "    ImmutableChildNode(String:Child0)\n";
        Assertions.assertEquals(root.toStringForm(""), str);
    }

    public ImmutableParentNode<String> checkCopyConstructor() {
        String str = "MutableParentNode(String:Root)\n" +
                "    MutableParentNode(String:Parent0)\n" +
                "        MutableChildNode(String:Child00)\n" +
                "        MutableChildNode(String:Child01)\n" +
                "    MutableParentNode(String:Parent1)\n" +
                "        MutableChildNode(String:Child10)\n" +
                "    MutableChildNode(String:Child0)\n";
        MutableParentNode<String> res = (MutableParentNode<String>) TreeImporter.importMutableTree(str);

        ImmutableParentNode<String> imm = new ImmutableParentNode<>(res);
        return imm;
    }
}