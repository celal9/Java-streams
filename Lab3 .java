import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Lab3 {
    // What was the most vital product type between and including 1980 and 1990? (You can
    //assume that the vital products are the ones bought on pay days,15th of every month.)
    public static void question1(Stream<String> crs){
        crs.skip(1)
                .filter(line -> {
                    String[] columns = line.split(",");
                    int year = Integer.parseInt(columns[1].split("-")[0]);
                    return (year <= 1990 && year>=1980) ;
                }).filter(line -> {
                    String[] columns = line.split(",");
                    int day = Integer.parseInt(columns[1].split("-")[2]);
                    return (day ==15);
                }).map(line -> {
                    String[] columns = line.split(",");
                    int[] count = new int[5];
                    for(int i = 2; i < columns.length; i++){
                        if(!columns[i].equals("")){
                            count[i-2]++;
                        }
                    }
                    return count;
                }).reduce((a, b) -> {
                    for(int i = 0; i < a.length; i++){
                        a[i] += b[i];
                    }
                    return a;
                }).ifPresent(count -> {
                    int maxIndex = 0;
                    for(int i = 1; i < count.length; i++){
                        if(count[i] > count[maxIndex]){
                            maxIndex = i;
                        }
                    }
                    System.out.println((char)('A' + maxIndex));
                });


    }

    // How many purchases with 3 or more products include A or D?
    public static void question2(Stream<String> crs){
        AtomicInteger count2= new AtomicInteger();
        crs.skip(1)
                .filter(line -> {
                    String[] columns = line.split(",");
                    String a;
                    String b;
                    String c;
                    String d;
                    String e;
                    int bcount00=0;

                    try {
                        a = columns[2];
                    } catch (Exception e0) {
                        a= "";
                    }
                    try {
                        b = columns[3];
                    } catch (Exception e1) {
                        b= "";
                    }
                    try {
                        c = columns[4];
                    } catch (Exception e2) {
                        c= "";
                    }
                    try {
                        d = columns[5];
                    } catch (Exception e3) {
                        d= "";
                    }
                    try {
                        e = columns[6];
                    } catch (Exception e4) {
                        e = "";
                    }
                    if(!a.equals("") ){
                        bcount00++;
                    }
                    if(!b.equals("") ){
                        bcount00++;
                    }
                    if(!c.equals("") ){
                        bcount00++;
                    }
                    if(!d.equals("") ){
                        bcount00++;
                    }
                    if(!e.equals("") ){
                        bcount00++;
                    }
                    return ((!a.equals("") || !d.equals("")) && bcount00>=3);
                }).map(line -> {
                    String[] columns = line.split(",");
                    int count=0;
                    for(int i = 2; i < columns.length; i++){
                        if(!columns[i].equals("")){
                            count++;
                        }
                    }

                    return count;

                }).forEach(line-> count2.addAndGet(1));
        System.out.println(count2.get());
    }
    //How many products are purchased by elder people (age 55 or above) after and including
    //1995?
    public static void question3(Stream<String> crs){
        AtomicInteger count2= new AtomicInteger();
        crs.skip(1)
                .filter(line -> {
                    String[] columns = line.split(",");
                    int year = Integer.parseInt(columns[1].split("-")[0]);
                    return (year >= 1995 ) ;
                }).filter(line -> {
                    String[] columns = line.split(",");
                    int age = Integer.parseInt(columns[0]);
                    return (age >=55);
                }).map(line -> {
                    String[] columns = line.split(",");
                    int count=0;
                    for(int i = 2; i < columns.length; i++){
                        if(!columns[i].equals("")){
                            count++;
                        }
                    }

                    return count;

                }).forEach(line-> count2.addAndGet(line));
        System.out.println(count2.get());

    }
    //What was the years that least and most B type products sold? Solve this question by
    //reading the stream only once. (Hint: Remember that the number of years in the dataset
    //is finite.)
    public static void question4(Stream<String> crs){



    }

    public static void answerQuestion(String filename, int question) throws IOException{
        try(Stream<String> lines = Files.lines(Paths.get(filename))){
            switch (question) {
                case 1:
                    question1(lines);
                    break;
                case 2:
                    question2(lines);
                    break;
                case 3:
                    question3(lines);
                    break;
                case 4:
                    question4(lines);
                    break;

            }
        }catch(IOException e){
            System.out.println("File not found");
            System.exit(0);
        }
    }
    public static void main(String[] args) throws IOException{
        // Take filename from command line
        if(args.length == 0){
            System.out.println("No filename given");
            System.exit(0);
        }
        String filename = args[0];

        // Take question number from command line
        if(args.length == 1){
            System.out.println("No question number given");
            System.exit(0);
        }
        int question = Integer.parseInt(args[1]);

        answerQuestion(filename, question);
    }


}