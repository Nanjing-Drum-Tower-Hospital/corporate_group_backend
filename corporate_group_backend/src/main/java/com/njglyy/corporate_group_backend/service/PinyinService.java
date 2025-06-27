package com.njglyy.corporate_group_backend.service;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.stereotype.Service;

@Service
public class PinyinService {

    public String getPinyinInitials(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder initials = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null && pinyinArray.length > 0) {
                // Get the first pinyin string and take its first character as the initial
                initials.append(Character.toUpperCase(pinyinArray[0].charAt(0)));

            } else {
                // If character has no pinyin, just append the character itself
                initials.append(Character.toUpperCase(c));
            }
        }
        return initials.toString();
    }
}
