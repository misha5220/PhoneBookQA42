package experiments;

import java.io.*;

public class SerializationExample {

    public static void main(String[] args) {
        Persons person = new Persons("Joey",33);
        try{
            FileOutputStream fileOut = new FileOutputStream("person.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(person);
            fileOut.close();
            out.close();
            System.out.println("Serialized");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            FileInputStream fileIn = new FileInputStream("person.ser");
            ObjectInputStream in= new ObjectInputStream(fileIn);
            Persons newPerson = (Persons) in.readObject();
            fileIn.close();
            in.close();
            System.out.println("name= "+newPerson.getName()+"; age = " + newPerson.getAge());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


    }
}
