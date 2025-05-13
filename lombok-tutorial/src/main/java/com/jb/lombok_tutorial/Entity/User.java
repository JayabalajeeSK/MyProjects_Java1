package com.jb.lombok_tutorial.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Getter(AccessLevel.PROTECTED)
// @Setter(AccessLevel.PROTECTED)

// @NoArgsConstructor(force = true)
// @AllArgsConstructor
// //@RequiredArgsConstructor

// //@ToString
// @ToString(includeFieldNames = false)

// @EqualsAndHashCode

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String firstName;
    private String lastName;
    // @ToString.Exclude
    private String email;
    private String phoneNumber;
    private int age;
}