package edu.hse.cs.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeImporterTest {

    @Test
    void importMutableTree() {
        String str = "MutableParentNode(Integer:8)\n" +
                "    MutableParentNode(Integer:9)\n" +
                "        MutableChildNode(Integer:10)\n" +
                "        MutableChildNode(Integer:11)\n" +
                "    MutableParentNode(Integer:12)\n" +
                "        MutableChildNode(Integer:13)\n" +
                "    MutableChildNode(Integer:7)\n";
        AbstractTreeNode<String> res = TreeImporter.importMutableTree(str);

        Assertions.assertEquals(res.toStringForm(""), str);

        str = "MutableParentNode(Integer:8)\n" +
                " MutableParentNode(Integer:9)\n";

        res = TreeImporter.importMutableTree(str);
        // неправильные дети - игнорируются
        Assertions.assertEquals(res.toStringForm(""), "MutableParentNode(Integer:8)\n");

        str = " MutableChildNode(Integer:11)\n" +
                "    MutableParentNode(Integer:8)\n";

        res = TreeImporter.importMutableTree(str);
        // у Child не может быть детей, значит их игнорируем ⛔
        Assertions.assertEquals(res.toStringForm(""), "MutableChildNode(Integer:11)\n");

    }
}