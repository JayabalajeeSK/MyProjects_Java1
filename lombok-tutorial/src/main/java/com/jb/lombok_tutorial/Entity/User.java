package com.jb.lombok_tutorial.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)

@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor

//@ToString
@ToString(includeFieldNames = false)

public class User {

    private int id;
    private final String firstName;
    private final String lastName;
    // @ToString.Exclude
    private String email;
    private String phoneNumber;
    private int age;
}