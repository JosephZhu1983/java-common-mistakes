package javaprogramming.commonmistakes.oom.impropermaxheadersize;

import javaprogramming.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplicationBad {
    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplicationBad.class, "bad.properties");
        SpringApplication.run(CommonMistakesApplicationBad.class, args);
    }
}

