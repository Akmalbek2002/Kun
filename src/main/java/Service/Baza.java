package Service;

import Model.Vazifa;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Baza {
    String url = "jdbc:postgresql://localhost:5432/kun";
    Scanner in = new Scanner(System.in);
    private String username = "postgres";
    private String password = "akmal625";

    public boolean tekshirish_login(String login, String parol) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        boolean b = false;
        String query = "select count(*) from foydalanuvchi where login='" + login + "' and " + "parol='" + parol + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count == 1) {
                b = true;
            }
        }
        return b;
    }

    public void foydalanuvchi_joylash() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement();
        String ism, familiya, manzili, tel_raqam, login, parol;
        in = new Scanner(System.in);
        System.out.print("\t\t\t\tIsm: ");
        ism = in.nextLine();
        System.out.print("\t\t\t\tFamiliya: ");
        familiya = in.nextLine();
        System.out.print("\t\t\t\tManzili: ");
        manzili = in.nextLine();
        System.out.print("\t\t\t\tTelefon raqam: ");
        tel_raqam = in.nextLine();
        System.out.print("\t\t\t\tLogin: ");
        login = in.nextLine();
        System.out.print("\t\t\t\tParol: ");
        parol = in.nextLine();
        String query = "select count(*) from foydalanuvchi where tel_raqam='" + tel_raqam + "'";
        String query1 = "select count(*) from foydalanuvchi where login='" + login + "'";
        ResultSet resultSet = statement.executeQuery(query);
        ResultSet resultSet1 = statement1.executeQuery(query1);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count != 0) {
                System.out.println("\t\t\t\tBunday telefon raqamli foydalanuvchi mavjud");
                resultSet.close();
            } else {
                if (resultSet1.next()) {
                    int count1 = resultSet1.getInt(1);
                    if (count1 != 0) {
                        System.out.println("\t\t\t\tBunday login bazada mavjud");
                        resultSet1.close();
                    } else {
                        String query2 = "insert into foydalanuvchi(ism,familiya,tel_raqam,manzili,login,parol)" +
                                " values(?,?,?,?,?,?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(query2);
                        preparedStatement.setString(1, ism);
                        preparedStatement.setString(2, familiya);
                        preparedStatement.setString(3, tel_raqam);
                        preparedStatement.setString(4, manzili);
                        preparedStatement.setString(5, login);
                        preparedStatement.setString(6, parol);
                        preparedStatement.executeUpdate();
                        System.out.println("\t\t\t\tMuvaffaqiyatli ro'yhatdan o'tdingiz!");
                    }
                }
            }
        }
    }

    public Integer kirish_foydalanuvchi(String login, String parol) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String query = "select id from foydalanuvchi where login='" + login + "' and parol='" + parol + "'";
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String string = resultSet.getString(1);
            int i = Integer.parseInt(string);
            return i;
        } else {
            return 0;
        }

    }
     public void hisobot_yuklash(Integer foydalanuvchi_id,LocalDate localDate) throws ClassNotFoundException, SQLException {
         Class.forName("org.postgresql.Driver");
         Connection connection = DriverManager.getConnection(url, username, password);
         Statement statement=connection.createStatement();
         Statement statement1=connection.createStatement();
         String query1="select count(*) from hisobot where foydalanuvchi_id="+foydalanuvchi_id+" and sana='"+localDate+"'";
         String bonus_query="select sum(bonus) from vazifalar where foydalanuvchi_id="+foydalanuvchi_id+" and sana1='"+localDate+"' and status="+true;
         ResultSet resultSet1=statement1.executeQuery(bonus_query);
         int bonus=0;
         if(resultSet1.next()){
              bonus=resultSet1.getInt(1);
         }

         ResultSet resultSet=statement.executeQuery(query1);
         if(resultSet.next()) {
             int i=resultSet.getInt(1);
             if(i==0) {
                 String query2 = "insert into hisobot(foydalanuvchi_id,bonus,sana) " + "values(?,?,?)";
                 PreparedStatement preparedStatement = connection.prepareStatement(query2);
                 preparedStatement.setInt(1, foydalanuvchi_id);
                 preparedStatement.setInt(2,bonus);
                 preparedStatement.setDate(3, Date.valueOf(localDate));
                 preparedStatement.executeUpdate();
             }
         }
     }
    public void vazifa_joylash(Integer foydalanuvchi_id, LocalDate localDate) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String daraja = null;
        int bonus = 0;
        Random random=new Random();
        String vazifa_nomi;
        in = new Scanner(System.in);
        System.out.print("\t\t\t\tVazifa nomi: ");
        vazifa_nomi = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("\t\t--------------------------------------------------------------");
        System.out.println("\t\t\t\tQiyinlik darajasi: ");
        System.out.println("\t\t\t1.Oddiy");
        System.out.println("\t\t\t2.O'rtacha");
        System.out.println("\t\t\t3.Qiyin");
        System.out.println("\t\t--------------------------------------------------------------");
        System.out.print("\t\t\t\tMenyudan raqam tanlang: ");
        int tanlov=in.nextInt();
        switch (tanlov){
            case 1:daraja="Oddiy";bonus=random.nextInt(901)+100;break;
            case 2:daraja="O'rtacha";bonus=random.nextInt(4001)+1000;break;
            case 3:daraja="Qiyin";bonus=random.nextInt(5001)+5000;break;
            default:break;
        }
        String query = "select count(*) from vazifalar where nomi='" + vazifa_nomi + "' and foydalanuvchi_id=" + foydalanuvchi_id+" and sana1='"+localDate+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count != 0) {
                System.out.println("\t\t\t\tBu vazifani  kiritgansiz");
            } else {
                String query1 = "insert into vazifalar(nomi,foydalanuvchi_id,status,sana1,qiyinlik_darajasi,bonus) " + "values(?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1, vazifa_nomi);
                preparedStatement.setInt(2, foydalanuvchi_id);
                preparedStatement.setBoolean(3, false);
                preparedStatement.setDate(4, Date.valueOf(localDate));
                preparedStatement.setString(5,daraja);
                preparedStatement.setInt(6,bonus);

                preparedStatement.executeUpdate();
                System.out.println("\t\t\t\tMuvaffaqiyatli qo'shildi!");
            }
        }
    }

    public void vazifalar_chiqarish(Integer foydalanuvchi_id, LocalDate localDate) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement();
        in = new Scanner(System.in);
        String query = "select * from vazifalar where foydalanuvchi_id=" + foydalanuvchi_id + " and sana1='" + localDate + "'";
        ResultSet resultSet = statement.executeQuery(query);
        List<Vazifa> vazifaList = new ArrayList<>();
        boolean tekshirish = false;
        int i = 0;
        while (resultSet.next()) {
            tekshirish = true;
            int id = resultSet.getInt(1);
            String nomi = resultSet.getString(2);
            boolean status = resultSet.getBoolean(4);
            LocalDate localDate1 = resultSet.getDate(5).toLocalDate();
            LocalTime localTime= resultSet.getTime(6).toLocalTime();
            String daraja=resultSet.getString(7);
            int bonus=resultSet.getInt(8);
            vazifaList.add(i, new Vazifa(id, nomi, foydalanuvchi_id, status, localDate1,localTime,daraja,bonus));
            i++;
        }
        if (!tekshirish) {
            System.out.println("\t\t\t\tVazifalar mavjud emas");
        } else {
            boolean holat=true;
            while (holat) {
                int index=1;
                System.out.println("\t\t|--------------------------------------------------------------|");
                System.out.println("\t\t|\t\tBugungi vazifalar jadvali");
                System.out.println("\t\t|--------------------------------------------------------------|");
                System.out.println("\t\t| T/R | \tVazifa Nomi\t | \t Bonus \t | \t Status ");
                for (Vazifa vazifa1 : vazifaList) {
                    System.out.print("\t\t| "+index + " |\t" + vazifa1.getNomi() + "\t|\t" + vazifa1.getBonus() + " |");
                    if (!vazifa1.isStatus()) {
                        System.out.print("\tBajarilmadi\n");
                    } else {
                        System.out.print("\tBajarildi\n");
                    }
                    index++;
                }
                System.out.println("\t\t| "+index+" |\tChiqish");
                System.out.println("---------------------------------------------");
                System.out.print("\t\t\t\tVazifani bajarish: ");
                int tanlov = in.nextInt();
                if (tanlov != index) {
                    System.out.println("\t\t\t\t\t\tVazifani bajardingizmi?");
                    System.out.println("\t\t\t\t\t1.Ha");
                    System.out.println("\t\t\t\t\t2.Yo'q");
                    System.out.print("\t\t\t\tMenyudan raqam tanglang: ");
                    int tanlov1 = in.nextInt();
                    if (tanlov1 == 1) {
                        boolean check = false;
                        int p = 1;
                        for (Vazifa vazifa1 : vazifaList) {
                            if (tanlov == p && !vazifa1.isStatus()) {
                                check = true;
                                vazifa1.setStatus(true);
                                String query1 = "update vazifalar set status=true where id=" + vazifa1.getId() + " and foydalanuvchi_id=" + foydalanuvchi_id;
                                statement1.execute(query1);
                            }
                            p++;
                        }
                        if (!check) {
                            System.out.println("---------------------------------------------");
                            System.out.println("\t\t\t\t\tVazifa bajarilgan");
                            System.out.println("---------------------------------------------");
                        }
                    }
                }
                else{
                    holat=false;
                }
            }

        }
    }
    public void hisobot(Integer foydalanuvchi_id,LocalDate localDate) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement();
        Statement statement2 = connection.createStatement();
        Statement statement3 = connection.createStatement();
        String query3="select sum(bonus) from vazifalar where foydalanuvchi_id="+foydalanuvchi_id+" and status=true "+
                "and sana1='"+localDate+"'";
        ResultSet resultSet2=statement3.executeQuery(query3);
        if(resultSet2.next()) {
            String s= resultSet2.getString(1);
            if(s != null) {
                String query1 = "update hisobot set bonus=" + "(select sum(bonus) from vazifalar where foydalanuvchi_id=" +
                        foydalanuvchi_id + " and status=true and sana1='" + localDate + "')" + " where foydalanuvchi_id=" + foydalanuvchi_id + " and sana='" + localDate + "'";
                statement1.execute(query1);
            }
        }
        String query2="select ism,familiya from foydalanuvchi where id="+foydalanuvchi_id;
        String query="select * from hisobot where foydalanuvchi_id="+foydalanuvchi_id+" and sana='"+localDate+"'";
        ResultSet resultSet1=statement2.executeQuery(query2);
        ResultSet resultSet=statement.executeQuery(query);
        if(resultSet.next()){
            double bonus=resultSet.getDouble(3);
            LocalDate localDate1= resultSet.getDate(4).toLocalDate();
            if(resultSet1.next()) {
                String ism = resultSet1.getString(1);
                String familiya = resultSet1.getString(2);
                System.out.println("\t\t--------------------------------------------------------------");
                System.out.println("\t\t\t\t\tFISH\t\tBonus\t\tSana\t\t");
                System.out.println("\t\t\t\t"+ism + " " + familiya + "\t\t" + bonus + "\t\t" + localDate1);
                System.out.println("\t\t--------------------------------------------------------------");
            }
        }
        else{
            System.out.println("\t\t\t\tSiz bugunni rejalashtirmagansiz");
        }

    }

    public void hisobot_oylik(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        Statement statement1=connection.createStatement();
        String query="select * from hisobot where foydalanuvchi_id="+id;
        String query1="select ism,familiya from foydalanuvchi where id="+id;
        ResultSet resultSet=statement.executeQuery(query);
        ResultSet resultSet1=statement1.executeQuery(query1);
        int i=1;
        System.out.println("\t\t--------------------------------------------------------------");
        System.out.println("\t\t\t\t\tFISH\t\tBonus\t\tSana\t\t");
        while (resultSet.next() && i!=30){
            double bonus=resultSet.getDouble(3);
            LocalDate localDate1= resultSet.getDate(4).toLocalDate();
            if(resultSet1.next()) {
                String ism = resultSet1.getString(1);
                String familiya = resultSet1.getString(2);
                System.out.println("\t\t\t\t"+ism + " " + familiya + "\t\t" + bonus + "\t\t" + localDate1);
            }
            i++;
        }
        System.out.println("\t\t--------------------------------------------------------------");

    }
}



