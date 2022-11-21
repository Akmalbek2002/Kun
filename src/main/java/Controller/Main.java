package Controller;

import Service.Baza;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        boolean chiqish=true;
        Scanner in=new Scanner(System.in);
        LocalDate localDate=LocalDate.now();
        Baza baza=new Baza();
        while (chiqish){
            System.out.println("\t\t-----------------------------------------------");
            System.out.println("\t\t|\t\t\t\t1.Ro'yhatdan o'tish\t\t\t  |");
            System.out.println("\t\t|\t\t\t\t2.Kirish\t\t\t\t\t  |");
            System.out.println("\t\t|\t\t\t\t3.Yakunlash\t\t\t\t\t  |");
            System.out.println("\t\t-----------------------------------------------");
            System.out.print("\t\t\t\tMenyudan raqam tanlang: ");
            int tanlov=in.nextInt();
            switch (tanlov){
                case 1:baza.foydalanuvchi_joylash();break;
                case 2: String login,parol;
                    in=new Scanner(System.in);
                    System.out.print("\t\t\t\tLogin: ");
                    login=in.nextLine();
                    System.out.print("\t\t\t\tParol: ");
                    parol=in.nextLine();
                    if(baza.kirish_foydalanuvchi(login,parol)==0){
                    System.out.println("\t\t\t\tLogin yoki parol xato");
                }
                else{
                    boolean chiqish1=true;
                    int id= baza.kirish_foydalanuvchi(login,parol);
                        baza.hisobot_yuklash(id,localDate);
                    while (chiqish1) {
                        baza.hisobot_yuklash(id,localDate);
                        System.out.println("\t\t-----------------------------------------------");
                        System.out.println("\t\t|\t\t\t\t1.Vazifalar\t\t\t\t\t  |");
                        System.out.println("\t\t|\t\t\t\t2.Vazifa qo'shish\t\t\t  |");
                        System.out.println("\t\t|\t\t\t\t3.Hisobot\t\t\t\t\t  |");
                        System.out.println("\t\t|\t\t\t\t4.Chiqish\t\t\t\t\t  |");
                        System.out.println("\t\t-----------------------------------------------");
                        System.out.print("\t\t\t\tMenyudan raqam tanlang: ");
                        int tanlov1 = in.nextInt();
                        switch (tanlov1) {
                            case 1:
                                baza.vazifalar_chiqarish(id,localDate);
                                break;
                            case 2:
                                baza.vazifa_joylash(id,localDate);
                                break;
                            case 3:
                                boolean chiqish2=true;
                                while (chiqish2) {
                                    System.out.println("\t\t-----------------------------------------------");
                                    System.out.println("\t\t\t\t\tHisobot");
                                    System.out.println("\t\t\t1.Kunlik");
                                    System.out.println("\t\t\t2.Oylik");
                                    System.out.println("\t\t\t3.Chiqish");
                                    System.out.println("\t\t-----------------------------------------------");
                                    System.out.print("\t\t\t\tMenyudan raqam tanglang: ");
                                    int tanlov2 = in.nextInt();
                                    if (tanlov2 == 1) {
                                        baza.hisobot(id, localDate);
                                    }
                                    if (tanlov2 == 2) {
                                        baza.hisobot_oylik(id);
                                    }
                                    if(tanlov2==3){
                                        chiqish2=false;
                                    }
                                }
                                break;
                            case 4:chiqish1=false;break;
                        }
                    }

                }
                break;
                case 3:chiqish=false;break;
            }
        }

    }
}
