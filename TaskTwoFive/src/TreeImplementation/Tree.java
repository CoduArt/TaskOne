package TreeImplementation;

import java.util.ArrayList;
import java.util.function.Function;

public class Tree<T> {

    protected class SimpleTreeNode {
        public T value;
        public SimpleTreeNode left;
        public SimpleTreeNode right;
        private int gColor = -1;

        public SimpleTreeNode(T value, SimpleTreeNode left, SimpleTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public SimpleTreeNode(T value) {
            this(value, null, null);
        }

        public T getValue() {
            return value;
        }

        public SimpleTreeNode getLeft() {
            return left;
        }

        public SimpleTreeNode getRight() {
            return right;
        }

        public boolean hasRight() {return right != null;}

        public boolean hasLeft() {return left != null;}

        public void setGColor(int color) {
            gColor = color;
        }

        public int getGColor() {
            return gColor;
        }
    }

    protected SimpleTreeNode root = null;

    protected Function<String, T> fromStrFunc;
    protected Function<T, String> toStrFunc;

    public Tree(Function<String, T> fromStrFunc, Function<T, String> toStrFunc) {
        this.fromStrFunc = fromStrFunc;
        this.toStrFunc = toStrFunc;
    }

    public Tree(Function<String, T> fromStrFunc) {
        this(fromStrFunc, x -> x.toString());
    }

    public Tree() {
        this(null);
    }
    
    public SimpleTreeNode getRoot() {
        return root;
    }

    private T fromStr(String s) throws Exception {
        s = s.trim();
        if (s.length() > 0 && s.charAt(0) == '"') {
            s = s.substring(1);
        }
        if (s.length() > 0 && s.charAt(s.length() - 1) == '"') {
            s = s.substring(0, s.length() - 1);
        }
        if (fromStrFunc == null) {
            throw new Exception("Не определена функция конвертации строки в T");
        }
        return fromStrFunc.apply(s);
    }

    private class IndexWrapper {
        public int index = 0;
    }

    private void skipSpaces(String bracketStr, IndexWrapper iw) {
        while (iw.index < bracketStr.length() && Character.isWhitespace(bracketStr.charAt(iw.index))) {
            iw.index++;
        }
    }

    private T readValue(String bracketStr, IndexWrapper iw) throws Exception {
        skipSpaces(bracketStr, iw);
        if (iw.index >= bracketStr.length()) {
            return null;
        }
        int from = iw.index;
        boolean quote = bracketStr.charAt(iw.index) == '"';
        if (quote) {
            iw.index++;
        }
        while (iw.index < bracketStr.length() && (
                    quote && bracketStr.charAt(iw.index) != '"' ||
                    !quote && !Character.isWhitespace(bracketStr.charAt(iw.index)) && "(),".indexOf(bracketStr.charAt(iw.index)) < 0
               )) {
            iw.index++;
        }
        if (quote && bracketStr.charAt(iw.index) == '"') {
            iw.index++;
        }
        String valueStr = bracketStr.substring(from, iw.index);
        T value = fromStr(valueStr);
        skipSpaces(bracketStr, iw);
        return value;
    }

    private SimpleTreeNode fromBracketStr(String bracketStr, IndexWrapper iw) throws Exception {
        T parentValue = readValue(bracketStr, iw);
        SimpleTreeNode parentNode = new SimpleTreeNode(parentValue);
        if (bracketStr.charAt(iw.index) == '(') {
            iw.index++;
            skipSpaces(bracketStr, iw);
            if (bracketStr.charAt(iw.index) != ',') {
                SimpleTreeNode leftNode = fromBracketStr(bracketStr, iw);
                parentNode.left = leftNode;
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) == ',') {
                iw.index++;
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) != ')') {
                SimpleTreeNode rightNode = fromBracketStr(bracketStr, iw);
                parentNode.right = rightNode;
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) != ')') {
                throw new Exception(String.format("Ожидалось ')' [%d]", iw.index));
            }
            iw.index++;
        }

        return parentNode;
    }

    public void fromBracketNotation(String bracketStr) throws Exception {
        IndexWrapper iw = new IndexWrapper();
        SimpleTreeNode root = fromBracketStr(bracketStr, iw);
        if (iw.index < bracketStr.length()) {
            throw new Exception(String.format("Ожидался конец строки [%d]", iw.index));
        }
        this.root = root;
    }

    public String findDuplicates() {
        ArrayList<SimpleTreeNode> TreesList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        if (root.hasLeft()) {
            TreesList.add(0, root.left);
        }
        if (root.hasRight()) {
            TreesList.add(0, root.right);
        }
        while (!TreesList.isEmpty()) {
            searchingDuplicates(TreesList, stringBuilder);
        }
        return stringBuilder.toString();
    }

    private void searchingDuplicates(ArrayList<SimpleTreeNode> TreesList, StringBuilder stringBuilder) {
        if (TreesList.size() < 2) {
            TreesList.clear();
            return;
        }
        SimpleTreeNode node = TreesList.get(0);
        TreesList.remove(0);
        for (int i = 0; i < TreesList.size(); i++) {
            if (similarityForEach(node, TreesList.get(i))) {
                fromTreeToString(node, stringBuilder);
            }
        }
        if (node.hasLeft()) {
            TreesList.add(0, node.left);
        }
        if (node.hasRight()) {
            TreesList.add(0, node.right);
        }
    }

    private boolean similarityForEach(SimpleTreeNode node1, SimpleTreeNode node2) {
        ArrayList<SimpleTreeNode> crushingList = new ArrayList<>();
        crushingList.add(node2);
        while (!crushingList.isEmpty()) {
            SimpleTreeNode compareNode = crushingList.get(0);
            if (similarity(node1, compareNode)) {
                return true;
            }
            if (compareNode.hasLeft()) {
                crushingList.add(compareNode.left);
            }
            if (compareNode.hasRight()) {
                crushingList.add(compareNode.right);
            }
            crushingList.remove(0);
        }
        return false;
    }

    private boolean similarity(SimpleTreeNode node1, SimpleTreeNode node2) {
        if (!node1.value.equals(node2.value)) {
            return false;
        }
        if (node1.hasLeft()) {
            if (node2.hasLeft()) {
                if (!similarity(node1.left, node2.left)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (node2.hasLeft()) {
            if (node1.hasLeft()) {
                if (!similarity(node1.left, node2.left)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        if (node1.hasRight()) {
            if (node2.hasRight()) {
                if (!similarity(node1.right, node2.right)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (node2.hasRight()) {
            if (node1.hasRight()) {
                if (!similarity(node1.right, node2.right)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public StringBuilder fromTreeToString(SimpleTreeNode node, StringBuilder stringBuilder) {
        recursionToString(stringBuilder, node);
        stringBuilder.append("\n");
        return stringBuilder;
    }

    private void recursionToString(StringBuilder stringBuilder, SimpleTreeNode node) {
        stringBuilder.append(node.value.toString());
        if (node.hasLeft() || node.hasRight()) {
            stringBuilder.append(" ");
            stringBuilder.append("(");
            if (node.hasLeft()) {
                recursionToString(stringBuilder, node.left);
            }
            if (node.hasRight()) {
                stringBuilder.append(",");
                stringBuilder.append(" ");
                recursionToString(stringBuilder, node.right);
            }
            stringBuilder.append(")");
        }
    }
}
