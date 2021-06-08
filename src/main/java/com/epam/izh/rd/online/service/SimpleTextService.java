package com.epam.izh.rd.online.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTextService implements TextService {

    /**
     * Реализовать функционал удаления строки из другой строки.
     *
     * Например для базовой строки "Hello, hello, hello, how low?" и строки для удаления ", he"
     * метод вернет "Hellollollo, how low?"
     *
     * @param base - базовая строка с текстом
     * @param remove - строка которую необходимо удалить
     */
    @Override
    public String removeString(String base, String remove) {

        Pattern pattern = Pattern.compile(remove);
        Matcher matcher = pattern.matcher(base);
        String removeBase;
        if (matcher.find()) {
            removeBase = matcher.replaceAll("");
        } else {
            return base;
        }
        return removeBase;
    }

    /**
     * Реализовать функционал проверки на то, что строка заканчивается знаком вопроса.
     *
     * Например для строки "Hello, hello, hello, how low?" метод вернет true
     * Например для строки "Hello, hello, hello!" метод вернет false
     */
    @Override
    public boolean isQuestionString(String text) {

        Pattern pattern = Pattern.compile(".$");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group().equals("?");
        }
        return false;
    }

    /**
     * Реализовать функционал соединения переданных строк.
     *
     * Например для параметров {"Smells", " ", "Like", " ", "Teen", " ", "Spirit"}
     * метод вернет "Smells Like Teen Spirit"
     */
    @Override
    public String concatenate(String... elements) {

        StringBuilder builder = new StringBuilder();
        for (String element : elements) {
            builder.append(element);
        }
        return String.valueOf(builder);
    }

    /**
     * Реализовать функционал изменения регистра в вид лесенки.
     * Возвращаемый текст должен начинаться с прописного регистра.
     *
     * Например для строки "Load Up On Guns And Bring Your Friends"
     * метод вернет "lOaD Up oN GuNs aNd bRiNg yOuR FrIeNdS".
     */
    @Override
    public String toJumpCase(String text) {

        String textToLowerCase = text.toLowerCase();
        String textToUpperCase = text.toUpperCase();
        char[] charsLower = textToLowerCase.toCharArray();
        char[] charsUpper = textToUpperCase.toCharArray();
        char[] charsArray = new char[charsLower.length];
        for (int i = 0; i < charsLower.length; i++) {
            if (i % 2 == 0) {
                charsArray[i] = charsLower[i];
            } else {
                charsArray[i] = charsUpper[i];
            }
        }
        return String.valueOf(charsArray);
    }

    /**
     * Метод определяет, является ли строка палиндромом.
     *
     * Палиндром - строка, которая одинаково читается слева направо и справа налево.
     *
     * Например для строки "а роза упала на лапу Азора" вернется true, а для "я не палиндром" false
     */
    @Override
    public boolean isPalindrome(String string) {

        String palindrome = string.replace(" ", "").toLowerCase();
        StringBuilder palindromeBuilder = new StringBuilder(palindrome);
        StringBuilder palindromeBuilderReverse = palindromeBuilder.reverse();
        String palindromeReverse = String.valueOf(palindromeBuilderReverse);
        if (!string.equals("")) {
            return palindrome.equals(palindromeReverse);
        } else {
            return false;
        }
    }
}
