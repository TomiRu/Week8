package com.example.week8;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BottleDispenser extends MainActivity {

    private static BottleDispenser instance = null;
    private int bottles;
    private double money;
    private ArrayList<Bottle> bottle_list;
    private String r;
    Bottle b;

    protected BottleDispenser() {

        money = 0;

        bottle_list = new ArrayList<Bottle>();

        bottle_list.add(new Bottle());
        bottle_list.add(new Bottle("Pepsi Max", "1.5", 2.2));
        bottle_list.add(new Bottle("Coca-Cola Zero", "0.5", 2.0));
        bottle_list.add(new Bottle("Coca-Cola Zero", "1.5", 2.5));
        bottle_list.add(new Bottle("Fanta Zero", "0.5", 1.95));


        bottles = bottle_list.size();

    }

    public static BottleDispenser getInstance() {
        if (instance == null) {
            instance = new BottleDispenser();
        } else {
            System.out.println("Instance already exists.");
        }
        return instance;
    }

    public void addMoney(EditText textLine, int amount) {
        money += amount;
        String a;
        if (amount == 0){
            a = "\nNo money added!";
        } else {
            a = "\nKlink! Added " + amount + "€ more money!";
        }

        textLine.setText(a);

    }

    public void buyBottle(EditText textLine, String choice, Context context) {
        if (bottles <= 0) {
            r = "\nError! The bottle dispenser \nis out of bottles!";
        } else {
            for (int i = 0; i < bottles; i++) {
                if (choice.contains(bottle_list.get(i).getName()) && choice.contains(bottle_list.get(i).getSize())) {
                    b = bottle_list.get(i);

                    if (money < b.getPrice()) {
                        r = "\nAdd money first!";

                    } else {
                        r = "\nKACHUNK! " + b.getName() + " came out of the dispenser!";
                        deleteBottle(b);
                        bottles = bottle_list.size();
                        money -= b.getPrice();
                        this.printReceipt(context);
                    }
                    break;
                } else {
                    r = "\nSorry but we are out of \n" + choice;
                }


            }

            }

            textLine.setText(r);
        }

    public void returnMoney(EditText textLine) {
        DecimalFormat df = new DecimalFormat("0.00");

        String r = "\nKlink klink. Money came out! \nYou got " + df.format(money) + "€ back";
        textLine.setText(r);
        money = 0;
    }
/*
    public void listBottles() {
        for (int i = 0; i < bottles; i++) {
            System.out.println(i+1 + ". Name: " + bottle_list.get(i).getName());
            System.out.println("	Size: " + bottle_list.get(i).getSize() + "	Price: " + bottle_list.get(i).getPrice());
        }
    }
/*
    public void menu() {
        System.out.println("\n*** BOTTLE DISPENSER ***");
        System.out.println("1) Add money to the machine");
        System.out.println("2) Buy a bottle");
        System.out.println("3) Take money out");
        System.out.println("4) List bottles in the dispenser");
        System.out.println("0) End");
    }
    */


    private void deleteBottle(Bottle b) {
        bottle_list.remove(b);
    }



     public ArrayList getList(){
        ArrayList<String> list = new ArrayList<String>();


         for (int i = 0 ; i<bottles ; i++){
             list.add(bottle_list.get(i).getName() + " " + bottle_list.get(i).getSize() + "l");
         }


         return list;
     }

     public void printReceipt(Context context){
        try {
            OutputStreamWriter osw = new OutputStreamWriter((context.openFileOutput("Receipt.txt", Context.MODE_PRIVATE)));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
            String t = dateFormat.format(new Date());

            String product = b.getName().toString() + " " + b.getSize().toString();

            double price = b.getPrice();

            osw.write("**** RECEIPT ****\n" +
                    "Time:    " + t + "\n" +
                    "Product: " + product + "\n" +
                    "Price:   " + price + "\n\n" +
                    "Thank you, \nWelcome again!\n" +
                    "*****************");

            osw.close();
        } catch (IOException e){
            Log.e("IOException", "Virhe syötteessä");
         } finally {
            System.out.println("Kuitti kirjoitettu.");
        }

     }
}
