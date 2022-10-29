package com.studyxuexi.messageencoderweb.service.impl;

import com.studyxuexi.messageencoderweb.service.EncoderService;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class EncoderServiceImpl implements EncoderService {
    private final Random random;
    private final List<Character> characters;

    public EncoderServiceImpl() {
        this.random = new Random();
        this.characters = new ArrayList<>();
        for (int i = 33; i < 127; i++) {
            if (!Character.isDigit((char) i)) {
                this.characters.add((char) i);
            }
        }
    }

    private String encode(String text) {
        String base64encodedString = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        char[] chars = base64encodedString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= i;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            String numberString = String.format("%03d", (int) c);
            int i = 0;
            while (i < 3 && numberString.charAt(i) == '0') {
                sb.append(this.characters.get(this.random.nextInt(this.characters.size())));
                i++;
            }
            for (; i < 3; i++) {
                sb.append(numberString.charAt(i));
            }
        }
        return sb.toString();
    }

    private String decode(String text) {
        char[] chars = text.toCharArray();
        int n = chars.length;
        StringBuilder sb = new StringBuilder();
        String result;
        try {
            for (int i = 0, num = 0; i < n; i++) {
                if (Character.isDigit(chars[i])) {
                    num = num * 10 + (chars[i] - '0');
                }
                if (i % 3 == 2) {
                    sb.append((char) num);
                    num = 0;
                }
            }
            chars = sb.toString().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] ^= i;
            }
            result = new String(Base64.getDecoder().decode(new String(chars)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            result = "Invalid input.";
        }
        return result;
    }

    @Override
    public String process(String text) {
        int n = text.length();
        if (n > 6 && text.startsWith("$$$") && text.endsWith("$$$")) {
            return decode(text.substring(3, n - 3));
        } else {
            String s = encode(text);
            return "$$$" + s + "$$$";
        }
    }
}
