package Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        parser.start();
        ArrayList<Lesson> lessons = parser.getLesson();
        for (Lesson a: lessons) {
            System.out.println(a.getThing() + ": " + Arrays.toString(a.getAssessment()));
        }


    }
}
