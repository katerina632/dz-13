import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationsDB {
    private final static String URL = "jdbc:postgresql://localhost:4567/postgres";
    private final static String USER_NAME = "postgres";
    private final static String USER_PASSWORD = "postgres";
    private final static String QUERY_SELECT_ALL = "select * from books";
    private final static String QUERY_INSERT = "insert into books values(?,?,?)";
    private final static String QUERY_UPDATE = "update books set title=? where id=?";
    private final static String QUERY_DELETE = "delete from books where d=?";

    public static List<Book> getBooksFromDB() {
        List<Book> books = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {
            Statement sqlStatement =  connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery(QUERY_SELECT_ALL);


            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("barcode"), resultSet.getString("description"),
                        resultSet.getString("title"), resultSet.getString("author"),
                        resultSet.getDouble("price"));
                books.add(book);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection string" +
                    ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD));
        }
        return books;
    }

    public static void insert(int id, int barcode, String title, String description, String author, double price) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, barcode);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, description);
            preparedStatement.setString(5, author);
            preparedStatement.setDouble(6, price);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection string" +
                    ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD));
        }
    }

    public static void update(int id, String title) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(3, title);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection string" +
                    ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD));
        }
    }

    public static void delete(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection string" +
                    ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD));
        }
    }
}
