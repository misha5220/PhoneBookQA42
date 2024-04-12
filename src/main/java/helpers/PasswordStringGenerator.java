package helpers;

import java.util.Random;

public class PasswordStringGenerator {


    public static String generatePassword (){
        StringBuilder password = new StringBuilder();
        for (int i=0;i<4;i++){
            char charUpperCase = (char) ('A' + Math.random()*('Z'-'A')+1);
            password.append(charUpperCase);
        }

        for (int i=0;i<4;i++){
            char charLowerCase = (char) ('a' + Math.random()*('z'-'a')+1);
            password.append(charLowerCase);
        }
        Random random = new Random();
        for (int i=0;i<3;i++){
            int digit= random.nextInt(10);
            password.append(digit);
        }
        String specialChar ="[]!$_-";
        int specialCharacterCount = 1 + random.nextInt(3);
        for(int i=0; i <specialCharacterCount;i++){
            int index = random.nextInt(specialChar.length());
            password.append(specialChar.charAt(index));
        }
        return password.toString();
    }


    private static char randomChar(){
        return (char) ('a' + Math.random()*('z'-'a')+1);
    }
}
