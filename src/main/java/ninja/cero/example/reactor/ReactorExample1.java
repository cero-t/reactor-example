package ninja.cero.example.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reactor by dummies
 */
public class ReactorExample1 {
    public static void main(String[] args) {
        new ReactorExample1().run();
    }

    void run() {
        Map<Student, List<Score>> map = new HashMap<>();
        fetchStudents("sg")
                .log("fetchStudents")
                .subscribe(student -> {
                    fetchScores(2017, student.id)
                            .log("fetchScores")
                            .subscribe(score -> map.computeIfAbsent(student, s -> new ArrayList<>()).add(score));
                });

        // スリープしてる間に代入される。
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(map);
    }


    Flux<Student> fetchStudents(String className) {
        Student[] students = {
                new Student(1, "Muto"),
                new Student(2, "Miyoshi"),
                new Student(3, "Matsui"),
                new Student(4, "Nakamoto"),
                new Student(5, "Iida"),
                new Student(6, "Horiuchi"),
                new Student(7, "Sugisaki"),
                new Student(8, "Sato"),
                new Student(9, "Mizuno"),
                new Student(10, "Kikuchi"),
                new Student(11, "Notsu"),
                new Student(12, "Taguchi"),
                new Student(13, "Ooga"),
                new Student(14, "Sugimoto"),
                new Student(15, "Shiroi"),
                new Student(16, "Isono"),
                new Student(17, "Kurosawa"),
                new Student(18, "Kurashima"),
                new Student(19, "Okada"),
                new Student(20, "Yamaide"),
                new Student(21, "Okazaki"),
                new Student(22, "Shintani"),
                new Student(23, "Aso"),
                new Student(24, "Hidaka"),
                new Student(25, "Yoshida"),
                new Student(26, "Fujihira"),
                new Student(27, "Aritomo"),
                new Student(28, "Mori"),
                new Student(29, "Tanaka"),
                new Student(30, "Yagi"),
        };

        return Flux.interval(Duration.ofMillis(100))
                .map(i -> students[i.intValue()])
                .take(students.length);
    }

    Flux<Score> fetchScores(int year, int id) {
        final Score[] scores = {
                new Score(id, "国語", 80),
                new Score(id, "数学", 90),
                new Score(id, "英語", 85),
                new Score(id, "社会", 93),
                new Score(id, "理科", 72)
        };

        return Flux.interval(Duration.ofMillis(100))
                .map(i -> scores[i.intValue()])
                .take(scores.length);
    }

    class Student {
        int id;
        String name;

        Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.id + " " + this.name;
        }
    }

    class Score {
        int id;
        String type;
        int score;

        public Score(int id, String type, int score) {
            this.id = id;
            this.type = type;
            this.score = score;
        }

        @Override
        public String toString() {
            return this.id + " " + this.type + " " + this.score;
        }
    }
}