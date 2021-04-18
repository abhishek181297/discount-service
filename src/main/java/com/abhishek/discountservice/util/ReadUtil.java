package com.abhishek.discountservice.util;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadUtil {

    private static Path resourcePath(String name) {
        URL resource = ReadUtil.class.getClassLoader().getResource(name);
        if (resource == null) {
            throw new IllegalArgumentException("Invalid resource file " + name);
        }
        return new File(resource.getFile()).toPath();
    }

    public static byte[] resourceBytes(String name) throws IOException {
        return Files.readAllBytes(resourcePath(name));
    }
}

