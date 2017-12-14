package ninja.cero.example.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Non-blocking Process + Multi Thread
 */
public class ReactorExample2 {
    public static void main(String[] args) {
        new ReactorExample2().run();
    }

    void run() {
        Mono<Map<Student, List<Score>>> monoMap = fetchStudents("sg")
                .log("fetchStudents")
                .flatMap(student -> fetchScores(2017, student.id)
                        .log("fetchScores")
                        .collectList()
                        .map(scores -> Tuples.of(student, scores))
                )
                .collectMap(Tuple2::getT1, Tuple2::getT2);

        Map<Student, List<Score>> map = monoMap.block();
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
                .zipWith(Flux.fromArray(students))
                .map(Tuple2::getT2);
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
                .zipWith(Flux.fromArray(scores))
                .map(Tuple2::getT2);
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