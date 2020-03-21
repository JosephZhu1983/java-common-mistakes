package org.geekbang.time.commonmistakes.equals.compareto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("compareto")
public class CompareToController {

    @GetMapping("wrong")
    public void wrong() {

        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "zhang"));
        list.add(new Student(2, "wang"));
        Student student = new Student(2, "li");

        log.info("ArrayList.indexOf");
        int index1 = list.indexOf(student);
        Collections.sort(list);
        log.info("Collections.binarySearch");
        int index2 = Collections.binarySearch(list, student);

        log.info("index1 = " + index1);
        log.info("index2 = " + index2);
    }

    @GetMapping("right")
    public void right() {

        List<StudentRight> list = new ArrayList<>();
        list.add(new StudentRight(1, "zhang"));
        list.add(new StudentRight(2, "wang"));
        StudentRight student = new StudentRight(2, "li");

        log.info("ArrayList.indexOf");
        int index1 = list.indexOf(student);
        Collections.sort(list);
        log.info("Collections.binarySearch");
        int index2 = Collections.binarySearch(list, student);

        log.info("index1 = " + index1);
        log.info("index2 = " + index2);
    }


    @Data
    @AllArgsConstructor
    class Student implements Comparable<Student> {
        private int id;
        private String name;

        @Override
        public int compareTo(Student other) {
            int result = Integer.compare(other.id, id);
            if (result == 0)
                log.info("this {} == other {}", this, other);
            return result;
        }
    }

    @Data
    @AllArgsConstructor
    class StudentRight implements Comparable<StudentRight> {
        private int id;
        private String name;

        @Override
        public int compareTo(StudentRight other) {
            return Comparator.comparing(StudentRight::getName)
                    .thenComparingInt(StudentRight::getId)
                    .compare(this, other);
        }
    }
}
