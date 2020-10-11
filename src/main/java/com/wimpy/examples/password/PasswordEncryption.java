package com.wimpy.examples.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;
import java.time.LocalDateTime;

public class PasswordEncryption {

    public static void main(String[] args) {
        new PasswordEncryption();
    }


    public PasswordEncryption() {


        String rawPassword = "ThisIsRawPasswordEncryption1234!";

        System.out.println("rawPassword = " + rawPassword);


        for (int i = 5; i < 15; i++) {
            howLongWillItTake(new BCryptPasswordEncoder(i), i, 10, rawPassword);
        }


    }

    private void howLongWillItTake(BCryptPasswordEncoder passwordEncoder, int strength, int times, String rawPassword) {
        String encode = "";
        LocalDateTime before;
        before = LocalDateTime.now();

        for (int i = 0; i < times; i++) {
            encode = passwordEncoder.encode(rawPassword);
        }
        System.out.printf("strength=%d;bcryptedPassword=%s;time=%sms \n", strength, encode, getDuration(before));
    }

    private String getDuration(LocalDateTime before) {
        return Duration.between(before, LocalDateTime.now()).toMillis() + "";
    }
}
