package helpers;


import java.util.Random;

public class PhoneNumberGenerator {
    public static void main(String[] args) {
        String string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());string = generatePhoneNumber();
        System.out.println(string.length());
    }

    private static final Integer MIN_LENGTH =10;
    private static final Integer MAX_LENGTH =15;

    public static String generatePhoneNumber(){
        Random random = new Random();
        int lenght = random.nextInt(MAX_LENGTH-MIN_LENGTH)+MIN_LENGTH;
        StringBuilder phoneNumber = new StringBuilder();
        for (int i=0;i<lenght;i++){
            if(i==0){
                phoneNumber.append(random.nextInt(9)+1);
            }
            phoneNumber.append(random.nextInt(9));
        }
        return phoneNumber.toString();
    }

}
