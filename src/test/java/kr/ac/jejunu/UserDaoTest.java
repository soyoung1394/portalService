package kr.ac.jejunu;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    private UserDao userDao;
    private DaoFactory daoFactory;
//    private UserDao hallaUserDao;

    @Before
    public void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
//        ApplicationContext applicationContext = new GenericXmlApplicationContext("classpath:daoFactory.xml");
        userDao = applicationContext.getBean("userDao", UserDao.class);
    }
    @Test
    public void update() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        user.setName("a");
        user.setPassword("1234");
        userDao.update(user);

        User updateUser = userDao.get(id);
        assertThat(updateUser.getId(),is(user.getId()));
        assertThat(updateUser.getName(), is(user.getName()));
        assertThat(updateUser.getPassword(), is(user.getPassword()));


    }

    private Integer insertUserTest(User user) throws SQLException, ClassNotFoundException {
        user.setName("a") ;
        user.setPassword("1234");
        return userDao.insert(user);
    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        userDao.delete(id);

        User deletedUser = userDao.get(id);
        assertThat(deletedUser, nullValue());

    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        int id =1;
        User user = userDao.get(id);
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("a"));
        assertThat(user.getPassword(), is("1234"));
    }


    @Test
    public void add() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);
//        User insertedUser =jejuuserDao.insert(user);
//        제대로 됬는지 검증하기 위해

        User insertedUser = userDao.get(id);
        //제대로된 결과 확인하기
        assertThat(insertedUser.getId(), is(id));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

}
