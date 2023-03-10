package com.avaliacao.fisica.avp.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexChecker {

    private RegexChecker() {
    }
    public static boolean isValidCPF(String cpf){
        Pattern pattern = Pattern.compile("^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$");
        Matcher matcher = pattern.matcher(cpf);

        return matcher.find();
    }
}
