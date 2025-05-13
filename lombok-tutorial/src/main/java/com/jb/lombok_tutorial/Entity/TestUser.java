package com.jb.lombok_tutorial.Entity;
public class TestUser {
    public static void main(String[] args) {
        User user1 = new User(11, "Jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        User user2 = new User(14, "jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        User user3 = User.builder()
                            .id(15)
                            .firstName("Jayabalajee")
                            .lastName("S K")
                            .email("john@example.com")
                            .phoneNumber("8965438924")
                            .age(30)
                            .build();
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
    }
}
// User(id=11, firstName=Jaya, lastName=Bala, email=jaya@gmail.com, phoneNumber=6383892010, age=22)
// User(id=14, firstName=jaya, lastName=Bala, email=jaya@gmail.com, phoneNumber=6383892010, age=22)
// User(id=15, firstName=Jayabalajee, lastName=S K, email=john@example.com, phoneNumber=8965438924, age=30)