package kr.ac.jejunu;

import java.sql.*;

public class UserDao {
    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public User get(int id) throws ClassNotFoundException, SQLException {
        //mysql driver load
        Connection connection = connectionMaker.getConnection();
        ;// 컬렉션을 맺어줌  즉 db랑 자바프로젝트랑 연결을 되었다.
        //sql 작성하고
        PreparedStatement preparedStatement = connection.prepareStatement("select*from userinfo where id=?");//sql을 실행하기위해 preparedStatement는 물음표를 써서 나중에 맵핑할 수 있다. 왜 Statement를 안쓰고 prepared를 할까? 성능이 더 좋아서 프리페어런스를 쓰면 처음들어왔을 때 파싱한번만 하기 때문에!! 쿼리파싱은 이렇게하고 나중에 vvalue만 들여넣는 식으로
        preparedStatement.setInt(1, id);//db는 커서를 가지고 있다.
        //        sql 실행하고
        ResultSet resultSet = preparedStatement.executeQuery();
        //        결과를 user에 매핑하고
        resultSet.next();//맨위를 가르키고 있기때문에 아무것도 없기대문에 next를 한다
        User user = new User();
        try {
            connection = connectionMaker.getConnection();
            preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        //결과를 리턴한다.
        return user;
    }

    public Integer insert(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id;
        try {
            connection = connectionMaker.getConnection();
            preparedStatement = connection.prepareStatement(
                    "insert into userinfo(name, password) values (?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select last_insert_id()");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            id = resultSet.getInt(1);
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
        return id;
    }
}