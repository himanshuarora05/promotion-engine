package com.assessment.password;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PasswordValidator {

    private static final String REGEX_CAP_A_Z = ".*[A-Z].*";
    private static final String REGEX_SMALL_A_Z = ".*[a-z].*";
    private static final String REGEX_NUMBER = ".*[0-9].*";

    public static void main(String[] args) {

    }

    public void feature1(String password) throws Exception {
        if (password == null) {
            throw new Exception("password should not be null");
        }
        if (password.length() <= 8) {
            throw new Exception("password should be larger than 8 chars");
        }
        if (!password.matches(REGEX_CAP_A_Z)) {
            throw new Exception("password should have one uppercase letter at least");
        }
        if (!password.matches(REGEX_SMALL_A_Z)) {
            throw new Exception("password should have one lowercase letter at least");
        }
        if (!password.matches(REGEX_NUMBER)) {
            throw new Exception("password should have one number at least");
        }
    }

    public void feature2(String password) throws Exception {
        if (password == null) {
            throw new Exception("Password cannot be empty");
        }
        int validationCount = 1;
        if (password.length() > 8) {
            validationCount++;
        }
        if (password.matches(REGEX_CAP_A_Z)) {
            validationCount++;
        }
        if (password.matches(REGEX_SMALL_A_Z)) {
            validationCount++;
        }
        if (password.matches(REGEX_NUMBER)) {
            validationCount++;
        }
        if(validationCount < 3) {
            throw new Exception("Password is OK if at least three of the previous conditions is true");
        }
    }

    public void feature3(String password) throws Exception {
        if (!password.matches(REGEX_SMALL_A_Z)) {
            throw new Exception("Password is OK if at least three of the previous conditions is true");
        }
    }

    public void feature4(String password) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Callable<Boolean>> rules = new ArrayList<>();
        rules.add(() -> password != null);
        rules.add(() -> password.length() > 8);
        rules.add(() -> password.matches(REGEX_CAP_A_Z));
        rules.add(() -> password.matches(REGEX_SMALL_A_Z));
        rules.add(() -> password.matches(REGEX_NUMBER));
        List<Future<Boolean>> results = executor.invokeAll(rules);
        int ruleCount = 0;
        for (Future<Boolean> result : results) {
            if (result.get()) {
                ruleCount++;
            }
        }
        executor.shutdown();
        if (ruleCount < 3) {
            throw new Exception("Password does not meet the minimum requirements");
        }

        feature3(password);
    }
}
