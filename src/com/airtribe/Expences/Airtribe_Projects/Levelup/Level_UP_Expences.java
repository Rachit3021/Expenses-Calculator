package com.airtribe.Expences.Airtribe_Projects.Levelup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

class Expenses2 {
    int amt;
    String cat;
    String note;
    String  date;

    Expenses2(int amt,String cat,String note,String date){
        this.amt=amt;
        this.cat=cat;
        this.note=note;
        this.date=date;
    }
    public String toString(){
        return amt+" "+cat+" "+note+" "+date;
    }// when ever you are printing the object it will give hexa decimal code to read that we use to string finction by overriding

    public int getAmt() {
        return amt;
    }
}
class ExpenseTracker2<T extends Expenses2> {//It can work with different types object which expenses2 class have
    ArrayList<T> al = new ArrayList<>(); // making it generic Create an empty ArrayList that will store objects of type T
    Scanner sc = new Scanner(System.in);

    void setup() {
        loadData();
        System.out.println("=======================WELCOME TO EXPENCES TRACKER===========================");
        System.out.println("==========================KINDLY SELECT THE OPTION============================");
        while (true) {
            System.out.println("1.ADD EXPENCES");
            System.out.println("2.VIEW ALL EXPENCES");
            System.out.println("3.SHOW TOTAL");
            System.out.println("4.SAVE AND EXIT");
            System.out.println("5.APPLY THE FILTER ");
            System.out.println("6.APPLY THE FILTER CATEGORY ");
            System.out.println("================================================================");

            String choice=sc.nextLine();

            if(choice.equals("1"))
                addExpence();
            else if(choice.equals("2"))
                viewExpence();
            else if(choice.equals("3"))
                showtotal();

            else if(choice.equals("4")){
                saveData();
                break;  // coming out of the process
            }
            else if(choice.equals("5"))
                applyFilter();          // apply the filter to the programe using the predicator

            else if(choice.equals("6"))
            applyFilterCategory();
            else
                System.out.println("INVALID CHOICE MADE");
        }

    }

    void addExpence(){

        System.out.println("enter the amount ");
        // int amt = sc.nextInt();
        int amt = Integer.parseInt(sc.nextLine()); // sc.nextLine() reads the whole line (including Enter)//Integer.parseInt() converts the string into integer
        System.out.println("enter the catogery");
        String cat = sc.nextLine();
        System.out.println("enter the note");
        String note = sc.nextLine();
        System.out.println("enter the date");
        String date =sc.nextLine();
         @SuppressWarnings("Unchecked")  //  the next line code give the worning not error that is why using @SuppressWarnings
        T e = (T)new Expenses2(amt,cat,note,date); // (T)new Expenses2 =Java cannot verify if that cast is safe. that is why using suppress
        al.add(e); // adding the object into the arraylist

    }
    void viewExpence(){  // printing the expences
        if(al.size()==0){
            System.out.println("NOT EXPENCE YET");
            return ;
        }

        else{
            System.out.println("==============ALL EXPENCES===============");
            System.out.println("AMOUNT     CATEGORY      NOTE      DATE");
            for(int i=0;i<al.size();i++){
                System.out.println(al.get(i));
            }
            System.out.println("==================================================");
        }

    }

    void showtotal(){
        int total = 0;
        for(int i=0;i<al.size();i++){
            total=total+al.get(i).getAmt();
        }

        System.out.println("TOTAL EXPENCES:"+total);
        System.out.println("NUMBER OF EXPENCES"+al.size());
    }

    void saveData() { // creating the file and saving the content
        try (FileWriter writer = new FileWriter("Expences2.txt")) { // true usage->> append to store the previous content
            for(int i=0;i<al.size();i++) {
                String content=al.get(i).toString(); // storing the content
                writer.write(content);         // writing the content in the file
                writer.write("\n");       // after printing one statement go add the next line
            }System.out.println("WRITING IN FILE DONE");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    void applyFilter()
    {
        System.out.println("ENTER THE AMOUNT TO APPLY THE FILTER ");
        int amt=Integer.parseInt(sc.nextLine());  // inputing the amount for which we are applying the filter
        List<T>filterans=filter(amt);           // Call filter method filter(amt)
        for(int i =0;i< filterans.size();i++){ //It prints whatever filter method returned.
            System.out.println(filterans.get(i));
        }
    }
      // filter method
       /*:
      👉 Checks all expenses
       👉 Finds expenses greater than given amount
       👉 Returns them
    */
    List<T> filter (int amount){     //  filter(int amount) method ->the value of line number  131 which is the amount value for which filter is passing
        Predicate<T> obj5=(T t)->{
            if(t.amt>amount)// checking the amt in the expenses with the amount for which filter is given
                return true;
            else
                return false;
        };

        List<T>ans=new ArrayList<>(); //This will store matching expenses.
        for (T e:al) {
            if(obj5.test(e)==true) // this condition internally  doing -> return e.amt > amount;
                ans.add(e);
        }
        return ans;
    }

    void applyFilterCategory()
    {
        System.out.println("ENTER THE AMOUNT TO APPLY THE FILTER catogary");
       String cate=sc.nextLine();  // inputing the amount for which we are applying the filter
        List<T>filterans=filter(cate);
        for(int i =0;i< filterans.size();i++){
            System.out.println(filterans.get(i));
        }
        System.out.println("NO CATOGERY FOUND");
    }

    List<T> filter (String category){
        Predicate<T> obj5=(T t)->{
            if(t.cat.equals(category))
                return true;
            else
                return false;
        };

        List<T>ans=new ArrayList<>();
        for (T e:al) {
            if(obj5.test(e)==true)
                ans.add(e);
        }
        return ans;
    }


    void loadData(){// loading the data in the terminal which is stored previously in the expence file
        try{
            Scanner Filescanner = new Scanner(new File("Expences2.txt")); // this reading the file which we are giving inside the file
            while(Filescanner.hasNextLine()){      // checking that content is there or not
                String line= Filescanner.nextLine();//It reads one full line from the file and stores it inside a String variable named line.
                String []parts =line.split(" "); //It breaks one line of text into small pieces using space as separator.
                int amt=Integer.parseInt(parts[0]);
                String cat=parts[1];
                String note=parts[2];
                String date=parts[3];
                @SuppressWarnings("Unchecked")
                T e= (T)new Expenses2(amt,cat,note,date);
                al.add(e);
            }

        }
        catch (Exception e){
            System.out.println("ERROR WHILE LOADING DATA"+e);
        }
    }

}


class Level_UP_Expences {

    public static void main(String[] args) {
        ExpenseTracker2 et= new ExpenseTracker2();
        et.setup();



    }
}

