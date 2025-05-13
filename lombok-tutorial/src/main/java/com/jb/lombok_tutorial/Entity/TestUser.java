package com.jb.lombok_tutorial.Entity;
public class TestUser {
    public static void main(String[] args) {
        User user1 = new User(11, "Jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        User user2 = new User(14, "Jaya", "Bala", "jaya@gmail.com", "6383892010", 22);

        User user3 = new User();
        user3.setId(11);
        user3.setFirstName("Jaya");
        user3.setLastName("Bala");
        user3.setEmail("jaya@gmail.com");
        user3.setPhoneNumber("6383892010");
        user3.setAge(22);
        User user4 = new User();
        user4.setId(14);
        user4.setFirstName("Jaya");
        user4.setLastName("Bala");
        user4.setEmail("jaya@gmail.com");
        user4.setPhoneNumber("6383892010");
        user4.setAge(22);

        System.out.println(user1.getId()); //11

        System.out.println(user2); //User(id=12, firstName=Jaya, lastName=Bala, email=jaya@gmail.com, phoneNumber=6383892010, age=22)

        System.out.println(user1.hashCode() == user3.hashCode()); //True

        System.out.println(user2.equals(user4)); //True
    }
}