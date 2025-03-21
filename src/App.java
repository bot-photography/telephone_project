import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gec", "root", "root");
            System.out.println("Connection established!");

            while (true) {
                System.out.println("\nChoose an option: ");
                System.out.println("1. Add Data");
                System.out.println("2. Show Data");
                System.out.println("3. Delete Data");
                System.out.println("4. Exit");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline left-over

                switch (choice) {
                    case 1:
                        // code for inserting data
                        String query = "INSERT INTO telephone (phone, Name) VALUES (?, ?)";
                        PreparedStatement pst = con.prepareStatement(query);

                        System.out.println("Enter phone number: ");
                        long phone = sc.nextLong();
                        sc.nextLine();  // Consume the newline character left by nextLong

                        System.out.println("Enter name: ");
                        String name = sc.nextLine();

                        pst.setLong(1, phone);
                        pst.setString(2, name);
                        pst.executeUpdate();
                        System.out.println("Record inserted successfully!");
                        break;

                    case 2:
                        // code for displaying data
                        String q = "SELECT * FROM telephone"; 
                        PreparedStatement pstSelect = con.prepareStatement(q);
                        ResultSet rs = pstSelect.executeQuery();

                        System.out.println("Phone Number | Name");
                        System.out.println("-------------------");
                        while (rs.next()) {
                            System.out.println(rs.getLong(1) + " | " + rs.getString(2));
                        }
                        break;

                    case 3:
                        // code for deleting data
                        System.out.println("Enter phone number to delete: ");
                        long delPhone = sc.nextLong();
                        sc.nextLine();  // Consume the newline charact er left by nextLong

                        String deleteQuery = "DELETE FROM telephone WHERE phone = ?";
                        PreparedStatement pstDelete = con.prepareStatement(deleteQuery);
                        pstDelete.setLong(1, delPhone);
                        int rowsAffected = pstDelete.executeUpdate();
                        
                        if (rowsAffected > 0) {
                            System.out.println("Record deleted successfully!");
                        } else {
                            System.out.println("No record found with that phone number.");
                        }
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        con.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice, please select again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }    }
}
