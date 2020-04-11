package org.geekbang.time.commonmistakes.serialization.serialversionissue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.*;

@RestController
@RequestMapping("serialversionissue")
@Slf4j
public class SerialVersionIssueController {
    private String file;

    @PostConstruct
    public void init() throws IOException {
        File f = new File("user.obj");
        if (!f.exists()) {
            f.createNewFile();
        }
        file = f.getAbsolutePath();
    }

    @GetMapping("write")
    public User write() throws IOException {
        User userSource = new User();
        userSource.setName("zhuye");
        writeObject(userSource);
        return userSource;
    }

    @GetMapping("read")
    public User read() throws IOException, ClassNotFoundException {
        return readObject();
    }

    private void writeObject(User user) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(user);
        }
    }

    private User readObject() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (User) ois.readObject();
        }
    }
}
