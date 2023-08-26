package org.example;


import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger perfectThree = new AtomicInteger(0);
    public static AtomicInteger perfectFour = new AtomicInteger(0);
    public static AtomicInteger perfectFive = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String str : texts) {
                if (isPalindrome(str)) {
                    perfectWordsCount(str);
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (String str : texts) {
                if (isSameLetter(str)) {
                    perfectWordsCount(str);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String str : texts) {
                if (toAlphabetize(str)) {
                    perfectWordsCount(str);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        displayResult();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String word) {
        StringBuilder stringBuilder = new StringBuilder(word);
        String reversed = String.valueOf(stringBuilder.reverse());
        if (word.equals(reversed)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSameLetter(String word) {
        String lowerCaseWorld = word.toLowerCase();
        char firstChar = lowerCaseWorld.charAt(0);
        for (int i = 0; i < lowerCaseWorld.length(); i++) {
            if (lowerCaseWorld.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean toAlphabetize(String world) {
        String lowerCaseWorld = world.toLowerCase();
        for (int i = 1; i < lowerCaseWorld.length(); i++) {
            char previous = lowerCaseWorld.charAt(i - 1);
            char current = lowerCaseWorld.charAt(i);
            if (current < previous) {
                return false;
            }
        }
        return true;
    }

    public static void perfectWordsCount(String str) {
        if (str.length() == 3) {
            perfectThree.getAndAdd(1);
        }
        if (str.length() == 4) {
            perfectFour.getAndAdd(1);
        }
        if (str.length() == 5) {
            perfectFive.getAndAdd(1);
        }
    }

    public static void displayResult() {
        System.out.println("Красивых слов с длиной 3: " + perfectThree);
        System.out.println("Красивых слов с длиной 4: " + perfectFour);
        System.out.println("Красивых слов с длиной 5: " + perfectFive);
    }
}
