package dao;

import model.User;
import util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util util;

    public UserDaoJDBCImpl() {
        util=new Util();
    }

    public void createUsersTable() {

        final String query = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastname VARCHAR(100), age INT);";

        try (Statement statement = util.getConnection().createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        final String query ="DROP TABLE IF EXISTS users";

        try (Statement statement = util.getConnection().createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        final String QUERY ="INSERT INTO users(name, lastname, age) VALUES (?,?,?)";

        try (PreparedStatement statement = util.getConnection().prepareStatement(QUERY)){
            statement.setString(1, name);           //подставляем значения в шаблон
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        final String QUERY ="DELETE FROM users WHERE id=?";
        try (PreparedStatement statement = util.getConnection().prepareStatement(QUERY)){
            statement.setLong(1, id);
            int deleteResult = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        final String QUERY ="SELECT * FROM users";
        List<User> userCollection= new ArrayList<User>();

        try (PreparedStatement statement = util.getConnection().prepareStatement(QUERY)){
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");

                userCollection.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userCollection;
    }

    public void cleanUsersTable() {

        final String QUERY ="TRUNCATE TABLE users";
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
