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
// User(id=0, firstName=null, lastName=null, email=null, phoneNumber=null, age=0)
// User(id=0, firstName=Jayabalajee, lastName=S K, email=null, phoneNumber=null, age=0)
// User(id=1, firstName=Jaya, lastName=Bala, email=jaya@gmail.com, phoneNumber=6383892010, age=22)

// @ToString(includeFieldNames = false)
// User(0, null, null, null, null, 0)
// User(0, Jayabalajee, S K, null, null, 0)
// User(1, Jaya, Bala, jaya@gmail.com, 6383892010, 22)

// @ToString.Exclude
// User(0, null, null, null, 0)
// User(0, Jayabalajee, S K, null, 0)
// User(1, Jaya, Bala, 6383892010, 22)