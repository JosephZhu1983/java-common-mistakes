package org.geekbang.time.commonmistakes.equals.equalitymethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("equalitymethod")
public class EqualityMethodController {

    @GetMapping("wrong")
    public void wrong() {
        Point p1 = new Point(1, 2, "a");
        Point p2 = new Point(1, 2, "b");
        Point p3 = new Point(1, 2, "a");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));
        log.info("p1.equals(p3) ? {}", p1.equals(p3));

    }

    @GetMapping("wrong2")
    public void wrong2() {
        PointWrong p1 = new PointWrong(1, 2, "a");
        try {
            log.info("p1.equals(null) ? {}", p1.equals(null));
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        Object o = new Object();
        try {
            log.info("p1.equals(expression) ? {}", p1.equals(o));
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        PointWrong p2 = new PointWrong(1, 2, "b");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));

        HashSet<PointWrong> points = new HashSet<>();
        points.add(p1);
        log.info("points.contains(p2) ? {}", points.contains(p2));
    }

    @GetMapping("right")
    public void right() {
        PointRight p1 = new PointRight(1, 2, "a");
        try {
            log.info("p1.equals(null) ? {}", p1.equals(null));
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        Object o = new Object();
        try {
            log.info("p1.equals(expression) ? {}", p1.equals(o));
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        PointRight p2 = new PointRight(1, 2, "b");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));

        HashSet<PointRight> points = new HashSet<>();
        points.add(p1);
        log.info("points.contains(p2) ? {}", points.contains(p2));
    }


    class Point {
        private final String desc;
        private int x;
        private int y;

        public Point(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }
    }

    class PointWrong {
        private final String desc;
        private int x;
        private int y;

        public PointWrong(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }

        @Override
        public boolean equals(Object o) {
            PointWrong that = (PointWrong) o;
            return x == that.x && y == that.y;
        }
    }

    class PointRight {
        private final int x;
        private final int y;
        private final String desc;

        public PointRight(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointRight that = (PointRight) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
