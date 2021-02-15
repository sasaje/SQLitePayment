/*
 *
 * Developed by Sara Sandager (sara590x@edu.easj.dk)
 * Licensed under the MIT License
 * 14/02/2021
 *
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Select {

    private Connection connect() {
        //SQLite connection string
        String url = "jdbc:sqlite:db/Paymentdb";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void selectAll(){
        String sql = "SELECT * FROM User";

        try(Connection connection = this.connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            //loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + ". " +
                        rs.getInt("mobileNumber") + ". " +
                        rs.getString("name") + "\n" +
                        rs.getString("address") + "\n" +
                        rs.getInt("cityId") + "\n" +
                        rs.getInt("countryId") + "\n" +
                        rs.getInt("cardNumber") + "\n" +
                        //rs.getDate("registrationDateTime") + "\n" +
                        rs.getString("password") + "\n" +
                        rs.getInt("paymentTransactionId") + "\n\n"
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void selectOne(){
        String userInput;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indtast et søgeord: ");
        userInput = scanner.nextLine();

        String sql = "SELECT * FROM User WHERE name LIKE '%" + userInput + "%'";

        try(Connection connection = this.connect()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                System.out.println("name");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Select app = new Select();
        app.selectAll();
        app.selectOne();
    }
}
