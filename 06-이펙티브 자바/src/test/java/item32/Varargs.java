package item32;

import org.junit.jupiter.api.Test;

class Varargs {


    @Test
    void 가변인수를_담기_위해_컴파일러가_배열을_생성한다() {
        varargs("헤나", "하이에나", "", "");
    }

    void varargs(String... args) {
    }

    void varargs(String arg1) {
    }
    void varargs(String arg1, String arg2) {
    }
    void varargs(String arg1, String arg2, String arg3) {
    }



    @Test
    void 오버로딩과_가변인수() {
        say("안녕1", "안녕2", "안녕3", "안녕4", "안녕5", "안녕6", "안녕7", "안녕8", "안녕9");
    }

    void say(String s1) {
    }

    void say(String s1, String s2) {
    }

    void say(String s1, String s2, String s3) {
    }

    void say(String... args) {
        for (final String arg : args) {
            System.out.println(arg);
        }
    }
}
