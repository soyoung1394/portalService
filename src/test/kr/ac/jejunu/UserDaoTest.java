package kr.ac.jejunu;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserDaoTest {
    private UserDao userDao;
    private DaoFactory daoFactory;
//    private UserDao hallaUserDao;

    @Before
    public void setup(){
        userDao=daoFactory.getUserDao();
//        jejuuserDao  = new JejuUserDao(new JejuConnectionMaker());
//        hallaUserDao  = new HallaUserDao();
    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        int id =1;
        User user = userDao.get(1);
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("a"));
        assertThat(user.getPassword(), is("1234"));
    }


    @Test
    public void add() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("hh") ;
        user.setPassword("1111");
        Integer id =userDao.insert(user);
//        User insertedUser =jejuuserDao.insert(user);
//        제대로 됬는지 검증하기 위해

        User insertedUser = userDao.get(id);
        //제대로된 결과 확인하기
        assertThat(insertedUser.getId(), is(id));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }
//    @Test
//    public void hallaGet() throws SQLException, ClassNotFoundException {
//        int id =1;
//        User user = hallaUserDao.get(1);
//        assertThat(user.getId(), is(1));
//        assertThat(user.getName(), is("a"));
//        assertThat(user.getPassword(), is("1234"));
//    }
//
//
//    @Test
//    public void hallaAdd() throws SQLException, ClassNotFoundException {
//        User user = new User();
//        user.setName("hh") ;
//        user.setPassword("1111");
//        Integer id =hallaUserDao.insert(user);
////        User insertedUser =userDao.insert(user);
////        제대로 됬는지 검증하기 위해
//
//        User insertedUser = hallaUserDao.get(id);
//        //제대로된 결과 확인하기
//        assertThat(insertedUser.getId(), is(id));
//        assertThat(insertedUser.getName(), is(user.getName()));
//        assertThat(insertedUser.getPassword(), is(user.getPassword()));
//    }
}
