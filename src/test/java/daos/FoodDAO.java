package daos;

import java.sql.*;

public class FoodDAO {
    Connection connection;

    public FoodDAO(Connection connection) {
        this.connection = connection;
    }


    public String getFoodByName(String name) {
        String query = "SELECT * FROM food WHERE FOOD_NAME = ?";
        String actualRow = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            actualRow = resultSet.getString(2) + " "
                    + resultSet.getString(3) + " "
                    + resultSet.getBoolean(4);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actualRow;

    }

    public void deleteByName(String name) {
        try (PreparedStatement pr = connection.prepareStatement(
                "DELETE FROM FOOD " +
                        "WHERE FOOD_ID = ( " +
                        "    SELECT MAX(FOOD_ID) " +
                        "    FROM FOOD " +
                        "    WHERE FOOD_NAME = ? " +
                        ")"
        )) {
            pr.setString(1, name);
            pr.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int count() {
        int count;
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT count(FOOD_ID) as count FROM food";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            count = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
