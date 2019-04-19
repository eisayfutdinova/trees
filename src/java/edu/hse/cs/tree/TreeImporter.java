package edu.hse.cs.tree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class TreeImporter {
    private static String[] indent; // отвечает за кол-во пробелов (за понимание связей)
    private static int i; // отвечает за текущую строку
    private static String[] className; // массив имен классов
    private static String[] name; // массив имен

    static AbstractTreeNode importMutableTree(String input) {

        String[] arr = input.split("\n"); // разделяем на подстроки
        indent = new String[arr.length]; //
        className = new String[arr.length];
        // массив типов
        String[] types = new String[arr.length];
        name = new String[arr.length];

        try {
            for (i = 0; i != arr.length; i++) { //идем по подстрокам
                indent[i] = "";
                className[i] = "";
                types[i] = "";
                name[i] = "";

                int j = 0;

                // считаем ко-во пробелов
                while (arr[i].charAt(j) == ' ') {
                    indent[i] += arr[i].charAt(j);
                    ++j;
                }

                // считывание имени класса
                while (arr[i].charAt(j) != '(')
                    className[i] += arr[i].charAt(j++);

                // считываем тип объекта
                types[i] = arr[i].substring(arr[i].indexOf('(') + 1, arr[i].indexOf(':'));

                // считываем имя объекта
                name[i] = arr[i].substring(arr[i].indexOf(':') + 1, arr[i].indexOf(')'));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            if (types[0].equals("Integer") && isNumberInt(name[0])) // если в строке написан тип Integer  и объект этому соответсвует
                return importIntegerMutableTree();
            if (types[0].equals("Double") && isNumberDouble(name[0])) // если объект - вещественное число
                return importDoubleMutableTree();
            if (types[0].equals("String")) // если объект - строка
                return importStringMutableTree();

        } catch (Exception ex) {
            throw new IllegalArgumentException("Inccorect types");
        }

        return null;
    }

    private static AbstractTreeNode<Integer> importIntegerMutableTree() {

        Map<Integer, MutableParentNode<Integer>> map = new LinkedHashMap<>(); // храним объекты с ключом=кол-во пробелом
        MutableParentNode<Integer> root = null; // запоминаю root, чтоб его вернуть

        for (i = 0; i != indent.length; i++) // иду по всем объектам
        {
            int num = Integer.parseInt(name[i]); // переводим строку в число
            switch (className[i]) // смотрим имя класса
            {
                case "MutableParentNode": {
                    MutableParentNode<Integer> parent = new MutableParentNode<>(num);

                    if (root == null) // если корня еще нет
                        root = parent; // тут он появляется!

                    if (map.get(indent[i].length()) != null) // проверяем, есть ли в мапе значение с таким кол-вом пробелом
                        map.remove(indent[i].length()); // если есть - удаляем

                    map.put(indent[i].length(), parent); // кладем туда объект с ключом

                    if (indent[i].length() != 0 && map.get(indent[i].length() - 4) != null) // если не корень
                    {
                        parent.setParent(map.get(indent[i].length() - 4)); // устанавливаем родителя, у которого было на 4 проблема меньше
                        map.get(indent[i].length() - 4).addChild(parent); // родителю добавляем ребенка
                    }
                    break;
                }
                case "MutableChildNode": {
                    MutableChildNode<Integer> child = new MutableChildNode<>(num);

                    if (indent[i].length() != 0 && map.get(indent[i].length() - 4) != null) {
                        map.get(indent[i].length() - 4).addChild(child);
                    } else if (root == null)
                        return child;
                    break;
                }
            }
        }

        return root;
    }

    private static AbstractTreeNode<Double> importDoubleMutableTree() { // все аналогично методу выше

        Map<Integer, MutableParentNode<Double>> map = new HashMap<>();
        MutableParentNode<Double> root = null;

        for (i = 0; i != indent.length; i++) {
            double num = Double.parseDouble(name[i]);
            switch (className[i]) {
                case "MutableParentNode": {
                    MutableParentNode<Double> parent = new MutableParentNode<>(num);

                    if (root == null)
                        root = parent;

                    if (map.get(indent[i].length()) != null)
                        map.remove(indent[i].length());

                    map.put(indent[i].length(), parent);

                    if (indent[i].length() != 0 && map.get(indent[i].length() - 4) != null) {
                        parent.setParent(map.get(indent[i].length() - 4));
                        map.get(indent[i].length() - 4).addChild(parent);
                    }
                    break;
                }
                case "MutableChildNode": {
                    MutableChildNode<Double> child = new MutableChildNode<>(num);

                    if (indent[i].length() != 0 && map.get(indent[i].length() - 4) != null) {
                        map.get(indent[i].length() - 4).addChild(child);
                    } else if (root == null)
                        return child;
                    break;
                }
            }
        }

        return root;
    }

    private static AbstractTreeNode<String> importStringMutableTree() {

        Map<Integer, MutableParentNode<String>> map = new HashMap<>();
        MutableParentNode<String> root = null;

        for (i = 0; i != indent.length; i++) {
            String num = name[i];
            switch (className[i]) {
                case "MutableParentNode": {
                    MutableParentNode<String> parent = new MutableParentNode<>(num);

                    if (root == null)
                        root = parent;

                    if (map.get(indent[i].length()) != null)
                        map.remove(indent[i].length());

                    map.put(indent[i].length(), parent);

                    if (indent[i].length() != 0 && map.get(indent[i].length() - 4) != null) {
                        parent.setParent(map.get(indent[i].length() - 4));
                        map.get(indent[i].length() - 4).addChild(parent);
                    }
                    break;
                }
                case "MutableChildNode": {
                    MutableChildNode<String> child = new MutableChildNode<>(num);

                    if (indent[i].length() != 0 && map.get(indent[i].length() - 4) != null) {
                        map.get(indent[i].length() - 4).addChild(child);
                    } else if (root == null)
                        return child;
                    break;
                }
            }
        }

        return root;
    }

    private static boolean isNumberInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //проверка, является ли строка типа float
    private static boolean isNumberDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
