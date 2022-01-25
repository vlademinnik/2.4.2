package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(User user, long id) {
        userDao.deleteUser(user, id);
    }

    @Transactional
    @Override
    public void editUser(User user, long id) {
        userDao.editUser(user, id);
    }

    @Transactional
    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }
}
