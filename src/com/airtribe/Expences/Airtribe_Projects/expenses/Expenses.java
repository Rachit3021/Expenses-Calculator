package com.airtribe.Expences.Airtribe_Projects.expenses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


 class Expenses {
    int amt;
    String cat;
    String note;
    String  date;

    Expenses(int amt,String cat,String note,String date){
        this.amt=amt;
        this.cat=cat;
        this.note=note;
        this.date=date;
    }
    public String toString(){
        return amt+" "+cat+" "+note+" "+date;
    }// when ever you are printing the object it will give hexa decimal code to read that we use to string finction by overriding
}
class ExpenseTracker {
    ArrayList<Expenses> al = new ArrayList<>();
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
                break;
            }
            else
                System.out.println("INVALID CHOICE MADE");
        }

    }

    void addExpence(){

        System.out.println("enter the amount ");
       // int amt = sc.nextInt();
        int amt = Integer.parseInt(sc.nextLine()); // doing type casting to read the full line
        System.out.println("enter the catogery");
        String cat = sc.nextLine();
        System.out.println("enter the note");
        String note = sc.nextLine();
        System.out.println("enter the date");
        String date =sc.nextLine();
        Expenses e = new Expenses(amt,cat,note,date); // e is a variable which store address in the stack and object expence store values in heap
        al.add(e); // e is providing the address of the expences like index value only and store it into the arraylist

    }
    void viewExpence(){
        if(al.size()==0){
            System.out.println("NOT EXPENCE YET");
            return ;
        }

        else{
            System.out.println("==============ALL EXPENCES===============");
            System.out.println("AMOUNT     CATEGORY      NOTE      DATE");
            for(int i=0;i<al.size();i++){
                System.out.println(al.get(i)); // printing the element in the array list
            }
            System.out.println("==================================================");
        }

    }

    void showtotal(){
        int total = 0;
        for(int i=0;i<al.size();i++){
            total=total+al.get(i).amt;
        }

        System.out.println("TOTAL EXPENCES:"+total);
        System.out.println("NUMBER OF EXPENCES"+al.size());
    }

    void saveData() { // creating the file and saving the content
            try (FileWriter writer = new FileWriter("Expences.txt")) { // true usage->> append to store the previous content
                for(int i=0;i<al.size();i++) {
                    String content=al.get(i).toString(); // storing the content
                    writer.write(content);         // writing the content in the file
                    writer.write("\n");       // after printing one statement go add the next line
                }System.out.println("WRITING IN FILE DONE");
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }

        void loadData(){// loading the data in the terminal which is stored previously in the expence file
        try{
            Scanner Filescanner = new Scanner(new File("Expences.txt")); // this reading the file which we are giving inside the file
            while(Filescanner.hasNextLine()){      // checking that content is there or not
                String line= Filescanner.nextLine();//It reads one full line from the file and stores it inside a String variable named line.
                String []parts =line.split(" "); //It breaks one line of text into small pieces using space as separator.
                int amt=Integer.parseInt(parts[0]);
                String cat=parts[1];
                String note=parts[2];
                String date=parts[3];
                Expenses e= new Expenses(amt,cat,note,date);
                al.add(e);
            }

        }
        catch (Exception e){
            System.out.println("ERROR WHILE LOADING DATA"+e);
        }
        }

    }


     class Activity {

         public static void main(String[] args) {
             ExpenseTracker et= new ExpenseTracker();
             et.setup();



         }
}

