package tech.limelight.limecash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.limelight.limecash.util.CryptoUtils;

import java.util.Scanner;

import static tech.limelight.limecash.util.Constants.*;

@SpringBootApplication
public class LimecashApplication {

    public static void main(String[] args) {
        SpringApplication.run(LimecashApplication.class, args);
    }

}
