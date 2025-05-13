package com.jb.lombok_tutorial.Entity;
public class TestUser {
    public static void main(String[] args) {
        User user1 = new User(1, "Jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        User user2 = new User(1, "Jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        System.out.println(user1.equals(user2)); //True

        User user3 = new User();
        user3.setId(1);
        user3.setFirstName("Jaya");
        user3.setLastName("Bala");
        user3.setEmail("jaya@gmail.com");
        user3.setPhoneNumber("6383892010");
        user3.setAge(22);
        User user4 = new User();
        user4.setId(1);
        user4.setFirstName("Jaya");
        user4.setLastName("Bala");
        user4.setEmail("jaya@gmail.com");
        user4.setPhoneNumber("6383892010");
        user4.setAge(22);
        System.out.println(user3.equals(user4)); //True

        System.out.println(user1.equals(user3)); //True
        System.out.println(user1.equals(user4)); //True
        
        System.out.println(user2.equals(user3)); //True
        System.out.println(user2.equals(user4)); //True
    }
}