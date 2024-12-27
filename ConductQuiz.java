package com.quiz;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ConductQuiz {
    static ArrayList<String> useroptions=new ArrayList<>();
    static void QuestionPaper() throws SQLException, IOException, ClassNotFoundException {
        Connection con=Jdbc.jdbcconnection();
        Statement st= con.createStatement();
        st.execute("use DhanushDb");
        ResultSet rs =st.executeQuery("select * from Quiz order by qid");
           if( rs.getFetchSize()>0){
            System.out.println("Instructions:");
            System.out.println(" 1.Each question will be displayed only once ");
            System.out.println(" 2.Once question is displayed you have to enter an either  a or b or c or d");
            System.out.println(" 3.You cannot revist the previous question");
        }
        String arr[]=null;
        int totalquestions=0;
        while (rs.next())
        {
            System.out.println(rs.getInt(1)+"."+rs.getString(2));
            arr=rs.getString(3).split(",");
            for(String i:arr) System.out.println(i);
            System.out.println();
            System.out.println("Enter your Option ");
            Scanner sc=new Scanner(System.in);
            String op=sc.next();
            useroptions.add(op);
            System.out.println();
            totalquestions++;
        }


        if(arr==null) {
            System.out.println("Exam paper Not prepared Yet Please wait for some time... " +
                    "otherwise...Please consult your Admin");
            System.out.println();
        }
        else System.out.println("Your Result is "+score()+ " out of "+totalquestions);

    }

    static int score() throws SQLException, IOException, ClassNotFoundException {
        Connection con=Jdbc.jdbcconnection();
        Statement st= con.createStatement();
        st.execute("use DhanushDb");
        ResultSet rs=st.executeQuery("select * from quiz");
        int result=0;

        int i=0;
        while(rs.next())
        {
            if(rs.getString(4).equals(useroptions.get(i)))
            {
                result++;
            }
            i++;
        }
        return result;

    }
}
