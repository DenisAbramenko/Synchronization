package org.example;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        String[] routs = new String[1000];
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < routs.length; i++) {
            routs[i] = generateRoute("RLRFR", 100);
        }

        for (String rout : routs) {
            Thread thread = new Thread(() -> {
                Integer amount = 0;
                for (int i = 0; i < rout.length(); i++) {
                    if (rout.charAt(i) == 'R') {
                        amount++;
                    }
                }
                System.out.println(rout.substring(0, 100) + " -> " + amount);

                synchronized (amount) {
                    if (sizeToFreq.containsKey(amount)) {
                        sizeToFreq.put(amount, sizeToFreq.get(amount) + 1);
                    } else {
                        sizeToFreq.put(amount, 1);
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int quantity = 0;
        Integer maxNumberRepetitions = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > quantity) {
                quantity = sizeToFreq.get(key);
                maxNumberRepetitions = key;
            }
        }
        System.out.println();
        System.out.println("Самое частое количество повторений " + maxNumberRepetitions +
                " (встретилось " + quantity + " раз)");
        sizeToFreq.remove(maxNumberRepetitions);

        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            int value = sizeToFreq.get(key);
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}