package com.jb.lombok_tutorial.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
@Builder
public class User {


    private int id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    // @ToString.Exclude
    @NonNull
    private String email;
    private String phoneNumber;
    private int age;

    public void updateEmail(@NonNull String email)
    {
        this.email=email;
    }
    public static void main(String[] args) {
        
    }
}