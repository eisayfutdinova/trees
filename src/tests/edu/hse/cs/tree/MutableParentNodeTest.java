package edu.hse.cs.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MutableParentNodeTest {

    @Test
    void getChildren() {
        MutableParentNode<String> root = populateTree();
        Assertions.assertEquals(root.getChildren().size(), 3);

        MutableParentNode<Integer> rootInt = populateTree1();
        Assertions.assertEquals(rootInt.getChildren().size(), 3);

        MutableParentNode<Double> rootDouble = populateTree2();
        Assertions.assertEquals(rootDouble.getChildren().size(), 3);
    }

    @Test
    void setChildren() {
    }

    @Test
    void addChild() {
        AbstractTreeNode<String> root = TreeImporter.importMutableTree("MutableParentNode(String:Root)\n");
        MutableChildNode<String> child = new MutableChildNode<>("Child01");
        ((MutableParentNode) root).addChild(child);

        String str = root.toStringForm("");
        Assertions.assertEquals(str, "MutableParentNode(String:Root)\n" +
                "    MutableChildNode(String:Child01)\n");

        MutableParentNode<String> parent1 = new MutableParentNode<>("Parent1");
        ((MutableParentNode) root).addChild(parent1);

        str = root.toStringForm("");
        Assertions.assertEquals(str, "MutableParentNode(String:Root)\n" +
                "    MutableChildNode(String:Child01)\n" +
                "    MutableParentNode(String:Parent1)\n");
    }

    @Test
    void removeChild() {
        MutableParentNode<String> root = populateTree();
        root.removeChild(new MutableParentNode<>("Parent0"));
        String res = root.toStringForm("");
        String str = "MutableParentNode(String:Root)\n" +
                "    MutableParentNode(String:Parent1)\n" +
                "        MutableChildNode(String:Child10)\n" +
                "    MutableChildNode(String:Child0)\n";

        Assertions.assertEquals(res, str);

        root.removeChild(new MutableParentNode<>("Root"));
        Assertions.assertEquals(root.toStringForm(""), str);

    }

    @Test
    void getAllDescendants() {
        MutableParentNode<String> root = populateTree();
        Collection<? extends ChildMutable<String>> collection = root.getAllDescendants();
        String str = "";
        for (ChildMutable<String> child : collection) {
            str += child.getObject() + " ";
        }

        Assertions.assertEquals(str, "Parent0 Child00 Child01 Parent1 Child10 Child0 ");
    }

    @Test
    void hasChildWithValue() {
        MutableParentNode<String> root = populateTree();

        Assertions.assertEquals(root.hasChildWithValue("Child0"), true);
        Assertions.assertEquals(root.hasChildWithValue("Parent0000"), false);
        Assertions.assertEquals(root.hasChildWithValue("child1"), false);
        Assertions.assertEquals(root.hasChildWithValue("Root"), false);
        Assertions.assertEquals(root.hasChildWithValue("Parent1"), true);
        Assertions.assertEquals(root.hasChildWithValue("Child10"), false);
    }

    @Test
    void hasDescendantWithValue() {
        MutableParentNode<Integer> root = populateTree1();

        Assertions.assertEquals(root.hasDescendantWithValue(12), true);
        Assertions.assertEquals(root.hasDescendantWithValue(11), true);
        Assertions.assertEquals(root.hasDescendantWithValue(32), false);
        Assertions.assertEquals(root.hasDescendantWithValue(14), false);
        Assertions.assertEquals(root.hasDescendantWithValue(10), true);

    }

    @Test
    void toStringForm() {
        MutableParentNode<String> root = populateTree();
        String str = root.toStringForm("");
        String myStr = "MutableParentNode(String:Root)\n" +
                "    MutableParentNode(String:Parent0)\n" +
                "        MutableChildNode(String:Child00)\n" +
                "        MutableChildNode(String:Child01)\n" +
                "    MutableParentNode(String:Parent1)\n" +
                "        MutableChildNode(String:Child10)\n" +
                "    MutableChildNode(String:Child0)\n";

        Assertions.assertEquals(str, myStr);

        MutableParentNode<Integer> root1 = populateTree1();
        String str1 = root1.toStringForm("");
        String myStr1 = "MutableParentNode(Integer:8)\n" +
                "    MutableParentNode(Integer:9)\n" +
                "        MutableChildNode(Integer:10)\n" +
                "        MutableChildNode(Integer:11)\n" +
                "    MutableParentNode(Integer:12)\n" +
                "        MutableChildNode(Integer:13)\n" +
                "    MutableChildNode(Integer:7)\n";

        Assertions.assertEquals(str1, myStr1);

        MutableParentNode<Double> root2 = populateTree2();
        String str2 = root2.toStringForm("");
        String myStr2 = "MutableParentNode(Double:8.0)\n" +
                "    MutableParentNode(Double:9.99)\n" +
                "        MutableChildNode(Double:10.32)\n" +
                "        MutableChildNode(Double:11.33)\n" +
                "    MutableParentNode(Double:12.234)\n" +
                "        MutableChildNode(Double:13.12)\n" +
                "    MutableChildNode(Double:7.12)\n";

        Assertions.assertEquals(str2, myStr2);

    }

    private static MutableParentNode<String> populateTree() {
        MutableParentNode<String> root = new MutableParentNode<>("Root");

        MutableParentNode<String> parent0 = new MutableParentNode<>("Parent0");
        MutableParentNode<String> parent1 = new MutableParentNode<>("Parent1");

        MutableChildNode<String> child0 = new MutableChildNode<>("Child0");
        MutableChildNode<String> child00 = new MutableChildNode<>("Child00");
        MutableChildNode<String> child01 = new MutableChildNode<>("Child01");
        MutableChildNode<String> child10 = new MutableChildNode<>("Child10");

        Set<ChildMutable<String>> rootChildren = new LinkedHashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);
        parent0.setParent(root);
        parent1.setParent(root);
        child0.setParent(root);

        Set<ChildMutable<String>> parent0Children = new LinkedHashSet<>(2);
        parent0Children.add(child00);
        parent0Children.add(child01);
        parent0.setChildren(parent0Children);
        child00.setParent(parent0);
        child01.setParent(parent0);


        Set<ChildMutable<String>> parent1Children = new LinkedHashSet<>(1);
        parent1Children.add(child10);
        parent1.setChildren(parent1Children);
        child10.setParent(parent1);

        return root;
    }

    private static MutableParentNode<Integer> populateTree1() {
        MutableParentNode<Integer> root = new MutableParentNode<>(8);

        MutableParentNode<Integer> parent0 = new MutableParentNode<>(9);
        MutableParentNode<Integer> parent1 = new MutableParentNode<>(12);

        MutableChildNode<Integer> child0 = new MutableChildNode<>(7);
        MutableChildNode<Integer> child00 = new MutableChildNode<>(10);
        MutableChildNode<Integer> child01 = new MutableChildNode<>(11);
        MutableChildNode<Integer> child10 = new MutableChildNode<>(13);

        Set<ChildMutable<Integer>> rootChildren = new LinkedHashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);
        parent0.setParent(root);
        parent1.setParent(root);
        child0.setParent(root);

        Set<ChildMutable<Integer>> parent0Children = new LinkedHashSet<>(2);
        parent0Children.add(child00);
        parent0Children.add(child01);
        parent0.setChildren(parent0Children);
        child00.setParent(parent0);
        child01.setParent(parent0);


        Set<ChildMutable<Integer>> parent1Children = new LinkedHashSet<>(1);
        parent1Children.add(child10);
        parent1.setChildren(parent1Children);
        child10.setParent(parent1);

        return root;
    }

    private static MutableParentNode<Double> populateTree2() {
        MutableParentNode<Double> root = new MutableParentNode<>(8.0);

        MutableParentNode<Double> parent0 = new MutableParentNode<>(9.99);
        MutableParentNode<Double> parent1 = new MutableParentNode<>(12.234);

        MutableChildNode<Double> child0 = new MutableChildNode<>(7.12);
        MutableChildNode<Double> child00 = new MutableChildNode<>(10.32);
        MutableChildNode<Double> child01 = new MutableChildNode<>(11.33);
        MutableChildNode<Double> child10 = new MutableChildNode<>(13.12);

        Set<ChildMutable<Double>> rootChildren = new LinkedHashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);
        parent0.setParent(root);
        parent1.setParent(root);
        child0.setParent(root);

        Set<ChildMutable<Double>> parent0Children = new LinkedHashSet<>(2);
        parent0Children.add(child00);
        parent0Children.add(child01);
        parent0.setChildren(parent0Children);
        child00.setParent(parent0);
        child01.setParent(parent0);


        Set<ChildMutable<Double>> parent1Children = new LinkedHashSet<>(1);
        parent1Children.add(child10);
        parent1.setChildren(parent1Children);
        child10.setParent(parent1);

        return root;
    }
}
