package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        List<String> listOfStrings = new ArrayList<>(Arrays.asList("one", "two"));
    }

}
