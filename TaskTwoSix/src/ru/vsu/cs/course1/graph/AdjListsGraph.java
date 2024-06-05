package ru.vsu.cs.course1.graph;

import java.util.*;

/**
 * Реализация графа на основе списков смежности
 */
public class AdjListsGraph implements Graph {
    private List<List<Integer>> vEdjLists = new ArrayList<>();
    private List<List<Integer>> communications = new ArrayList<>();
    private int vCount = 0;
    private int eCount = 0;
    private double X;
    private double Y;

    private static Iterable<Integer> nullIterable = new Iterable<Integer>() {
        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Integer next() {
                    return null;
                }
            };
        }
    };

    @Override
    public int vertexCount() {
        return vCount;
    }

    @Override
    public int edgeCount() {
        return eCount;
    }

    @Override
    public void addAdge(int v1, int v2) {
        int maxV = Math.max(v1, v2);
        for (; vCount <= maxV; vCount++) {
            vEdjLists.add(null);
            communications.add(new LinkedList<>());
        }
        if (!isAdj(v1, v2)) {
            if (vEdjLists.get(v1) == null) {
                vEdjLists.set(v1, new LinkedList<>());
            }
            vEdjLists.get(v1).add(v2);
            addToCommunications(v1, v2);
            eCount++;
        }
    }

    private void addToCommunications(int v1, int v2) {
        communications.get(v1).add(v2);
        communications.get(v2).add(v1);
    }

    private int countingRemove(List<Integer> list, int v) {
        int count = 0;
        if (list != null) {
            for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
                if (it.next().equals(v)) {
                    it.remove();
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void removeAdge(int v1, int v2) {
        eCount -= countingRemove(vEdjLists.get(v1), v2);
    }

    @Override
    public Iterable<Integer> adges(int v) {
        return vEdjLists.get(v) == null ? nullIterable : vEdjLists.get(v);
    }

    private class Friend {
        int v;
        double friendCoefficient;

        public Friend(int v, double friendCoefficient) {
            this.v = v;
            this.friendCoefficient = friendCoefficient * 0.9;
        }

        public Friend(int v) {
            this.v = v;
            this.friendCoefficient = 1;
        }

        public int loyalty(Friend person) {
            if (Math.abs(friendCoefficient - person.friendCoefficient) > Y) {
                if (friendCoefficient > person.friendCoefficient) {
                    return 2;
                } else {
                    return 1;
                }
            }
            return 0;
        }

        public boolean isNeutral() {
            return friendCoefficient <= X;
        }

        public boolean equals(Friend friend) {
            return friend.v == v;
        }
    }

    private List<Friend> addNeighbours(Friend person, List<Integer> visited) {
        List<Friend> newlist = new ArrayList<>();
        for (Integer el: communications.get(person.v)) {
            if (visited.get(el) != null) {
                continue;
            }
            newlist.add(new Friend(el, person.friendCoefficient));
            visited.set(el, 1);
        }
        return newlist;
    }

    @Override
    public String findAllies(int v1, int v2, double X, double Y) {
        List<Friend> friendsForV1 = new ArrayList<>();
        List<Friend> friendsForV2 = new ArrayList<>();
        List<Friend> neutralList = new ArrayList<>();
        List<Integer> visited1 = new ArrayList<>();
        List<Integer> visited2 = new ArrayList<>();
        for (int i = 0; i < vCount; i++) {
            visited1.add(null);
            visited2.add(null);
        }
        visited1.set(v1, 1);
        visited1.set(v2, 1);
        visited2.set(v1, 1);
        visited2.set(v2, 1);
        this.X = X;
        this.Y = Y;
        friendsForV1.addAll(addNeighbours(new Friend(v1), visited1));
        friendsForV2.addAll(addNeighbours(new Friend(v2), visited2));
        defineAllies(friendsForV1, friendsForV2, neutralList);

        int flag = 0;
        List<Friend> newlist = new ArrayList<>();
        while (true) {
            newlist.clear();
            for (Friend friend: friendsForV1) {
                newlist.addAll(addNeighbours(friend, visited1));
            }
            if (newlist.size() == 0) {
                flag++;
            }else {
                friendsForV1.addAll(newlist);
            }

            newlist.clear();
            for (Friend friend: friendsForV2) {
                newlist.addAll(addNeighbours(friend, visited2));
            }
            if (newlist.size() == 0) {
                flag++;
            } else {
                friendsForV2.addAll(newlist);
            }

            if (flag == 2) {
                break;
            }
            flag = 0;
            defineAllies(friendsForV1, friendsForV2, neutralList);
        }

        checkAlliesNeighbour(friendsForV1, v1, neutralList);
        checkAlliesNeighbour(friendsForV2, v2, neutralList);
        addToNeutralOthersV(neutralList, visited1, visited2);
        return "Союзники для " + v1 +" :" + toString(friendsForV1) +
                "Союзники для " + v2 +" :" + toString(friendsForV2) +
                "Нейтральные :" + toString(neutralList.stream().toList());
    }

    private void addToNeutralOthersV(List<Friend> neutral, List<Integer> visited1, List<Integer> visited2) {
        for (int j = 0; j < visited1.size(); j++) {
            if (visited1.get(j) == null && visited2.get(j) == null) {
                addToList(new Friend(j), neutral);
            }
        }

    }

    private void checkAlliesNeighbour(List<Friend> friendList, int startPerson, List<Friend> neutral) {
        List<Integer> checkedV = new ArrayList<>();
        for (Friend fr: friendList) {
            checkedV.add(fr.v);
        }
        checkedV.add(startPerson);
        int count = 0;
        boolean flag;
        while (count < friendList.size()) {
            flag = false;
            for (Integer el: communications.get(friendList.get(count).v)) {
                if (checkedV.contains(el)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                count++;
            } else {
                addToList(friendList.get(count), neutral);
                friendList.remove(count);
            }
        }

    }

    private String toString(List<Friend> list) {
        String string = "";
        for (Friend friend: list) {
            if (friend == null) {
                continue;
            }
            string = string + " " + friend.v;
        }
        string = string + "\n";
        return string;
    }

    private void defineAllies(List<Friend> friends1, List<Friend> friends2, List<Friend> neutral) {
        List<Friend> addList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int count = 0;
            List<Friend> fr = i == 0 ? friends1 : friends2;
            while (count < fr.size()) {
                if (fr.get(count).isNeutral()) {
                    addToList(fr.get(count), neutral);
                    fr.remove(fr.get(count));
                } else {
                    if (!addToList(fr.get(count), addList)) {
                        int loy = fr.get(count).loyalty(addList.get(fr.get(count).v));
                        if (loy == 0) {
                            addToList(fr.get(count), neutral);
                            friends1.remove(addList.get(fr.get(count).v));
                            friends2.remove(fr.get(count));
                        } else if (loy == 1){
                            friends2.remove(fr.get(count));
                        } else if (loy == 2){
                            friends1.remove(addList.get(fr.get(count).v));
                            count++;
                        }
                    } else {
                        count++;
                    }
                }
            }
        }
    }

    private boolean addToList(Friend friend, List<Friend> friendsList) {
        for(;friendsList.size() <= friend.v;) {
            friendsList.add(null);
        }
        if (friendsList.get(friend.v) == null) {
            friendsList.set(friend.v, friend);
            return true;
        } else {
            return false;
        }
    }
}
