package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser("Mike", "Tyson", (byte) 56);
        userServiceImpl.saveUser("Bob", "Marley", (byte) 36);
        userServiceImpl.saveUser("Tom", "Clancy", (byte) 66);
        userServiceImpl.saveUser("Jack", "Napier", (byte) 49);
        userServiceImpl.removeUserById(3);
        for (User allUser : userServiceImpl.getAllUsers()) {
            System.out.println(allUser);
        }
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();

       //UserDaoHibernateImpl userDou= new UserDaoHibernateImpl();
       //userDou.saveUser("Jack", "Napier", (byte) 49);
    }
}
