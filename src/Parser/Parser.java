package Parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class Parser {
    private ArrayList<Lesson> lesson = new ArrayList<>();

    public void start() throws IOException {
        // 1. ПОЛУЧАЕМ запрос на форму входа, скрытые поля и куки
        Connection.Response loginForm = Jsoup.connect("https://nehai.by/ej/templates/login_parent.php")
                .method(Connection.Method.GET)
                .execute();
        // в итоге получаем скрытые поля, динамически генерируемые сервером
        Document doc = loginForm.parse();
        String S_Code = doc.getElementsByAttributeValue("id","S_Code").attr("value");


        //2. POST-аутентификация
        Connection.Response postAuth = Jsoup.connect("https://nehai.by/ej/ajax.php")
                .userAgent("Firefox ...")
                .referrer("https://nehai.by/ej/templates/login_parent.php")
                .data("action", "login_parent")
                .data("student_name", "-")
                .data("group_id", "-")
                .data("birth_day", "-")
                .data("S_Code", S_Code)
                .cookies(loginForm.cookies()) // важно!
                .method(Connection.Method.POST)
                .execute();

        //3. Вход в кабинет
        Connection.Response auth = Jsoup.connect("https://nehai.by/ej/templates/parent_journal.php")
                .cookies(loginForm.cookies()) // важно!
                .method(Connection.Method.GET)
                .execute();

        Document document = auth.parse();
        Elements subjects = document.select("div.leftColumn").select("tr");
        Elements marks = document.select("div.rightColumn").select("tr");


        for(int i = 2; i<subjects.size()-1; i++)
            lesson.add(new Lesson(subjects.eq(i).text(), marks.eq(i).select("span.mar").text()));
    }

    public ArrayList<Lesson> getLesson() {
        return lesson;
    }
}