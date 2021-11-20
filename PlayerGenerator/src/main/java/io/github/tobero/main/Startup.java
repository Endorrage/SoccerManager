package io.github.tobero.main;


import io.github.tobero.nameGen.NameGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Startup {

    public static void main(String[] args) {
        int i = Integer.parseInt("d");
        List<String> names = NameGenerator.getProcedualNames(10, 0, 10, 1000);
        for (String name : names) {
            System.out.println(name);
        }
    }
}
