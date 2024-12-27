package com.quiz;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class DataBaseManager {
    static void userinput(int i) throws SQLException, IOException, ClassNotFoundException {
        if(i==1) {
            System.out.println("press 1 to create a  table  in only once in " +
                    "a  data base to store exam paper details");
            System.out.println("press 2 to insert questions");
            System.out.println("press 3 to delete a question");
            System.out.println("press 4 to update a question");
            Scanner  sc=new Scanner(System.in);
            int x=sc.nextInt();
            if(x==1) DataBaseManager.createTable();
            else if(x==2)
            {
                System.out.println("Enter no.of questions you want to Insert");
                int n=sc.nextInt();
                for(int j=0;j<n;j++) {
                    System.out.print("Enter Question number : ");
                    insertquestion();
                }
            }
            else   if(x==3)
            {
                System.out.println("please enter the question number you want to delete");

                int y=sc.nextInt();
                deleteQuestion(y);
            }
            else  if(x==4)
            {
                System.out.println("please enter the question number you want to update");

                int z=sc.nextInt();
                updateQuestion(z);
            }
        }
        else if(i==2)
        {
            ConductQuiz.QuestionPaper();
        }


    }
    static void createTable() throws SQLException, IOException, ClassNotFoundException {
        Connection con=Jdbc.jdbcconnection();
        Statement st=con.createStatement();
        st.execute("use DhanushDb");
        String query="create table Quiz (qid int, qd varchar(5000),Options varchar(5000)," +
                " correctoption varchar(1))";
        st.executeUpdate(query);
        System.out.println("Table created successfuly");
        con.close();
    }
    static void insertquestion() throws SQLException, IOException, ClassNotFoundException {
        Connection con = Jdbc.jdbcconnection();
        Statement st = con.createStatement();
        st.execute("use DhanushDb");
        String query = "insert into Quiz values (?, ?, ?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        Scanner sc = new Scanner(System.in);

        int qid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Question description: ");
        String qd = sc.nextLine();

        int c = 97;
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            System.out.println("Enter option " + (char) c + ":");
            String option = sc.nextLine();
            r.append((char) c).append(".").append(option);
            if (i < 3) {
                r.append(",");
            }
            c++;
        }
        System.out.println("Enter correct option:");
        String co = sc.next();
        ps.setInt(1, qid);
        ps.setString(2, qd);
        ps.setString(3, String.valueOf(r));
        ps.setString(4, co);
        ps.executeUpdate();
        System.out.println("Question inserted successfully");
    }

    static void deleteQuestion(int x) throws SQLException, IOException, ClassNotFoundException {

        Connection con=Jdbc.jdbcconnection();
        Statement st= con.createStatement();
        st.execute("use DhanushDb");
        String query="delete from Quiz where qid="+x;
        st.execute(query);
        System.out.println("Question successfully Deleted ");
        con.close();
    }
    static void updateQuestion(int x) throws SQLException, IOException, ClassNotFoundException {
        Connection con = Jdbc.jdbcconnection();
        Statement st = con.createStatement();
        st.execute("use DhanushDb");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new Question Description:");
        String qd = sc.nextLine();
        int c = 97; // ASCII for 'a'
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            System.out.println("Enter option " + (char) c + ":");
            String option = sc.nextLine();
            r.append((char) c).append(".").append(option);
            if (i < 3) {
                r.append(",");
            }
            c++;
        }
        System.out.println("Enter correct option ( a, b, c, or d):");
        String co = sc.next();
        String query = "UPDATE Quiz SET qd = ?, options = ?, correctoption = ? WHERE qid ="+x;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, qd);
        ps.setString(2, String.valueOf(r));
        ps.setString(3, co);
        ps.executeUpdate();
        System.out.println("Updated sucessfully");
        con.close();
    }
}
