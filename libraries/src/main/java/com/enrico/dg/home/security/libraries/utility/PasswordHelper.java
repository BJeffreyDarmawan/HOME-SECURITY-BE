package com.enrico.dg.home.security.libraries.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.libraries.exception.InvalidPasswordException;
import org.passay.AlphabeticalSequenceRule;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.NumericalSequenceRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.QwertySequenceRule;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHelper {

    private PasswordHelper() {}

    public static String encryptPassword(String rawPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public static boolean matchPassword(String rawPassword, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(rawPassword, password);
    }

    public static boolean isPasswordValid(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new NumericalSequenceRule(3,false),
                new AlphabeticalSequenceRule(3,false),
                new QwertySequenceRule(3,false),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            Set<String> errorList = new HashSet<>();
            for (RuleResultDetail ruleResultDetail : result.getDetails()) {
                errorList.add(ruleResultDetail.getErrorCode());
            }
            throw new InvalidPasswordException(ResponseCode.INVALID_PASSWORD.getCode(), ResponseCode.INVALID_PASSWORD.getMessage(), new ArrayList<>(errorList));
        }
        return true;
    }
}
