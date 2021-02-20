/*
 *
 * Developed by Sara Sandager (sara590x@edu.easj.dk)
 * Licensed under the MIT License
 * 18/02/2021
 *
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowUsersAndPaymentsTransactions {

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

        public void showAll(){

            String sqlAll = "SELECT id, mobileNumber, name, address, cityId, cardNumber, registraitionDateTime, password, User.paymentTransactionId, PaymentTransaction.paymentTransactionId, amount, senderMobileNumber, recieverMobileNumber, transactionDateTime, message, image FROM User INNER JOIN PaymentTransaction ON User.paymentTransactionId = PaymentTransaction.paymentTransactionId";

            try(Connection connection = this.connect();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlAll)) {

                while (rs.next()) {
                    System.out.println(
                        rs.getInt("id") + ". " +
                        rs.getInt("mobileNumber") + ", " +
                        rs.getString("name") + ", " +
                        rs.getString("address") + ", " +
                        rs.getInt("cityId") + ", " +
                        rs.getInt("cardNumber") + ", " +
                         // rs.getDate("registraitionDateTime") + ", " +
                        rs.getString("password") + ", " +
                        rs.getInt("paymentTransactionId") + ", " +
                        rs.getInt("amount") +
                        rs.getInt("senderMobileNumber") + ", " +
                        rs.getInt("recieverMobileNumber") + ", " +
                         //  rs.getDate("transactionDateTime") + "\n" +
                        rs.getString("message") + ", " +
                        rs.getString("image") + "."
                    );
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public void showAllUsers(){
            String sqlAllUsers = "SELECT * FROM User";

            try(Connection connection = this.connect();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlAllUsers)) {

                while (rs.next()) {
                    System.out.println(
                            rs.getInt("id") + ", " +
                            rs.getInt("mobileNumber") + ", " +
                            rs.getString("name") + ", " +
                            rs.getString("address") + ", " +
                            rs.getInt("cityId") + ", " +
                            rs.getInt("cardNumber") + ", " +
/*                            rs.getDate("registraitionDateTime") + ", " +*/
                            rs.getString("password") + ", " +
                            rs.getInt("paymentTransactionId") + "."
                    );
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public void showAllPaymentTransactions(){
            String sqlAllPayments = "SELECT * FROM PaymentTransaction";

            try(Connection connection = this.connect();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlAllPayments)) {

                while (rs.next()) {
                    System.out.println(
                        rs.getInt("paymentTransactionId") + ", " +
                        rs.getInt("amount") + ", " +
                        rs.getInt("senderMobileNumber") + ", " +
                        rs.getInt("recieverMobileNumber") + ", " +
                   /*     rs.getDate("registrationDateTime") + ", " +*/
                        "'" + rs.getString("message") + "'" + ", " +
                        rs.getString("image") + "."
                    );
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public void makePaymentTransaction() throws SQLException{
            double inputAmount;
            int inputSenderMobileNumber;
            int inputRecieverMobileNumber;
            String inputTransactionDateTime = getCurrentDateTime();
            String inputMessage;
            String inputImage;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Indtast et beløb: ");
            inputAmount = scanner.nextDouble();

            System.out.println("Indtast dit mobilnummer (uden +45): ");
            inputSenderMobileNumber = scanner.nextInt();

            System.out.println("Indtast modtagers mobilnummer (uden +45): ");
            inputRecieverMobileNumber = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Indtast en besked: ");
            inputMessage = scanner.nextLine();

            System.out.println("Indsæt billede (format .png eller .jpg): ");
            inputImage = scanner.nextLine();

            String sql = "INSERT INTO PaymentTransaction (amount, senderMobileNumber, recieverMobileNumber, transactionDateTime, message, image) VALUES (" + "'" + inputAmount + "', '" + inputSenderMobileNumber + "', '" + inputRecieverMobileNumber + "', '" + inputTransactionDateTime + "', '" + inputMessage + "', '" + inputImage + "')";

            try(Connection connection = this.connect()){
                Statement stmt = connection.createStatement();
                int rs = stmt.executeUpdate(sql);

                System.out.println("*** Betalingen er nu overført ***");
                System.out.println("Amount: " + inputAmount);
                System.out.println("Recievers mobileNumber: " + inputRecieverMobileNumber);
                System.out.println("Senders mobileNumber: " + inputSenderMobileNumber);
                System.out.println("TransactionDatetime: " + inputTransactionDateTime);
                System.out.println("Message: " + inputMessage);
                System.out.println("Image: " + inputImage);

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public String getCurrentDateTime(){
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String currentDateTime = dateFormat.format(date);
            return  currentDateTime;
        }

        public static void main(String[] args) {
            ShowUsersAndPaymentsTransactions app = new ShowUsersAndPaymentsTransactions();
            System.out.println("**** ALL PAYMENT TRANSACTIONS JOINED WITH USERS *****");
            app.showAll();
            System.out.println("\n" + "**** USERS *****");
            app.showAllUsers();
            System.out.println("\n" + "**** PAYMENTTRANSACTIONS *****");
            app.showAllPaymentTransactions();

            System.out.println("\n" + "**** MAKE NEW PAYMENT TRANSACTION *****");
                try {
                    app.makePaymentTransaction();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        }
    }
