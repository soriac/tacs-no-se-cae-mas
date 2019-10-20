package org.tacs.grupocuatro.util;

import org.tacs.grupocuatro.DAO.RepositoryDAO;
import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.entity.User;

public class DAOManager {
    public static void clearAllDAOs() {
        UserDAO userDao = UserDAO.getInstance();
        for(User user: userDao.getAll()) {
            if (!user.getEmail().equals("admin"))
                UserDAO.getInstance().delete(user);
        }
        RepositoryDAO.getInstance().getAll().removeIf(repo -> true);
    }
}
