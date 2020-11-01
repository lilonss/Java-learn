package Parser;

public class Lesson {
    private String thing;
    private int[] assessment;

    public Lesson(String thing, String assessmentList){
        this.thing = thing;
        if(assessmentList.replaceAll("[^0-9\\.]","").trim().length() != 0) {
            String[] array = (assessmentList.replaceAll("\\D+", " ").trim()).split(" ");
            int[] a = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                a[i] = Integer.valueOf(array[i]);
            }
            this.assessment = a;
        }
    }

    public String getThing() {
        return thing;
    }

    public int[] getAssessment() {
        return assessment;
    }
}
