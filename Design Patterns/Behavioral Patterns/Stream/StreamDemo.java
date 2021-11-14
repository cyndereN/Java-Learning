package uk.ac.ucl.comp0010.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class StreamDemo {


    public static List<String> good(List<String> list) {
        return list.stream()
                    .filter(s -> s.startsWith("c"))
                    .map(String::toUpperCase)
                    .sorted()
                    .collect(Collectors.toList());
    }

    public static List<String> bad(List<String> list) {
        ArrayList<String> l1 = new ArrayList<String>();
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).length() > 0 && list.get(i).charAt(0) == 'c') {
                l1.add(list.get(i));
            }
        }
        ArrayList<String> l2 = new ArrayList<String>();
        for (int i=0; i<l1.size(); i++) {
            String s = "";
            for (int j=0; j<l1.get(i).length(); j++) {
                if (l1.get(i).charAt(j) < 'A') {
                    s += (char) (l1.get(i).charAt(j) - 'a' + 'A');
                } else {
                    s += l1.get(i).charAt(j);
                }
            }
            l2.add(s);
        }

        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < l2.size()-1; i++) {
                if (l2.get(i).compareTo(l2.get(i + 1)) > 0) {
                    String temp = l2.get(i);
                    l2.set(i, l2.get(i + 1));
                    l2.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
    
        return l2;
    }

}