package Controllers;

import Models.*;

import java.util.Map;

public class Constraints {
    public static boolean validEmail(String email) {
//        String regex ="^[^\\\\s@]+@[^\\\\s@]+\\\\.[^\\\\s@]+$";
//        System.out.println("mail"+email.matches(regex));
//        return email.matches(regex);
        return true;
    }
    public static boolean validPassword(String password) {
        String regex ="^[a-zA-Z0-9]+$";
        System.out.println(password.matches(regex));
        return password.matches(regex) && password.length()>=4;
    }
    public static boolean validUsername(String username) {
        String regex ="^[a-zA-Z\\s]$";
        System.out.println(username.matches(regex));
        return username.matches(regex)&&(!username.equals(""));
    }
    public static boolean validPhone(String phone) {
        String regex = "^(03|05|07|08|09)\\d{8}$";
        System.out.println(phone.matches(regex));
        if(phone.length()!=10 || !phone.matches(regex)) return false;
        else return true;
    }
    public static boolean validAddress(String address) {
        String regex ="^[a-zA-Z0-9\\s,.-]+$";
        System.out.println(address.matches(regex));
        return address.matches(regex);
    }
    public static boolean validateTransfer(Map<String, String> userInput) {
        if (userInput == null || userInput.isEmpty()) return false;

        String money = userInput.get("money");
        String contents = userInput.get("contents");
        String bankName = userInput.get("bankName");

        if (money == null || money.trim().isEmpty() ||
                contents == null || contents.trim().isEmpty() ||
                bankName == null || bankName.trim().isEmpty()) {
            return false;
        }

        String moneyRegex = "^[0-9]+(\\.[0-9]{1,2})?$";
        if (!money.matches(moneyRegex)) {
            return false;
        }

        try {
            double moneyValue = Double.parseDouble(money.trim());
            if (moneyValue <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        if (!bankName.matches("^[a-zA-Z0-9\\s]+$") || contents.length() > 200) {
            return false;
        }

        return true;
    }
    public static boolean validateHandleMoney(Map<String, String> userInput) {
        if (userInput == null) return false;
        try {
            double money = Double.parseDouble(userInput.get("money"));
            return money > 0;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
    public static boolean validateCheckNumber(Map<String, String> accno) {
        if (accno == null) return false;
        String accountNumber = accno.get("accno");
        return accountNumber != null &&
                !accountNumber.isEmpty() &&
                accountNumber.matches("^\\d{8}$");
    }
}