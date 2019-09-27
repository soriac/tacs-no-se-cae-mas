package org.tacs.grupocuatro.util;

import org.tacs.grupocuatro.DAO.RepositoryDAO;
import org.tacs.grupocuatro.DAO.UserDAO;

public class DAOManager {
    public static void clearAllDAOs() {
        UserDAO.getInstance().getAll().removeIf(user -> !user.getEmail().equals("admin"));
        RepositoryDAO.getInstance().getAll().removeIf(repo -> true);
    }
}
