package com.jb.lombok_tutorial.Entity;
public class TestUser {
    public static void main(String[] args) {
        User user = new User();
        User user1 = new User("Jayabalajee", "S K");
        User user2 = new User(1, "Jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        System.out.println(user);
        System.out.println(user1);
        System.out.println(user2);
    }
}
// com.jb.lombok_tutorial.Entity.User@59690aa4
// com.jb.lombok_tutorial.Entity.User@5ecddf8f
// com.jb.lombok_tutorial.Entity.User@3f102e87