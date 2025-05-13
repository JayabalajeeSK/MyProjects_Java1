package com.jb.lombok_tutorial.Entity;
public class TestUser {
    public static void main(String[] args) {
        User user1 = new User(11, "Jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        User user2 = new User(14, "jaya", "Bala", "jaya@gmail.com", "6383892010", 22);
        System.out.println(user1);
        System.out.println(user2);
        user1.updateEmail(null);
    }
}
// User(id=11, firstName=Jaya, lastName=Bala, email=jaya@gmail.com, phoneNumber=6383892010, age=22)
// User(id=14, firstName=jaya, lastName=Bala, email=jaya@gmail.com, phoneNumber=6383892010, age=22)
// Exception in thread "main" java.lang.NullPointerException: email is marked non-null but is null
//         at com.jb.lombok_tutorial.Entity.User.updateEmail(User.java:37)
//         at com.jb.lombok_tutorial.Entity.TestUser.main(TestUser.java:8)