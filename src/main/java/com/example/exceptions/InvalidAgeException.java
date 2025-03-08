package com.example.exceptions;

public class InvalidAgeException extends RuntimeException {

    private int age;

    public InvalidAgeException(String errorMessageIn, int ageIn) {
        super(errorMessageIn);
        this.age = ageIn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int ageIn) {
        this.age = ageIn;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        msg += " " + age;
        return msg;
    }

}
