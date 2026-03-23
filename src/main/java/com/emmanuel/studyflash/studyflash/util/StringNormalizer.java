package com.emmanuel.studyflash.studyflash.util;

import org.apache.commons.lang3.StringUtils;

public class StringNormalizer {

   private StringNormalizer (){}
    public static String normalize(String value){
        if (value == null) return null;

        return StringUtils.stripAccents(value)
                .trim().toLowerCase();

    }
}
