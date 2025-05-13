package com.jb.lombok_tutorial.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)

@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor

public class User {

    private int id;
    private final String firstName;
    private final String lastName;
    private String email;
    private String phoneNumber;
    private int age;
}