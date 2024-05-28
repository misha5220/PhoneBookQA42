package helpers;

import interfaces.TestHelper;
import models.Contact;

public class RandomContactGenerator implements TestHelper {

    public static Contact contactGenerator(){
        Contact contact = new Contact(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(4,4,3),
                AddressGenerator.generateAddress(),"test description");
        return contact;
    }


}
