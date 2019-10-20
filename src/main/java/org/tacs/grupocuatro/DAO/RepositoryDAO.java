package org.tacs.grupocuatro.DAO;

import org.tacs.grupocuatro.HibernateUtil;
import org.tacs.grupocuatro.entity.Repository;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;

import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RepositoryDAO implements DAO<Repository> {

    private static RepositoryDAO instance;

    public static RepositoryDAO getInstance() {
        if (instance == null) {
            instance = new RepositoryDAO();
        }

        return instance;
    }

    private List<Repository> repositories = new ArrayList<>();

    @Override
    public Repository get(long id) {
        Repository repo = new Repository();
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            repo = (Repository) session.createQuery("from Repository WHERE id = " + id).uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return repo;
    }

    @Override
    public List<Repository> getAll() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            repositories = session.createQuery("from Repository  ").getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return repositories;
    }

    public Repository getOrAdd(long id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
        var repo = this.getAll().stream().filter(u -> u.getId() == id).findFirst();

        if (repo.isEmpty()) {
            var foundRepo = GitHubConnect.getInstance().findRepositoryById(id);
            var localRepo = new Repository(foundRepo.getId(), foundRepo.getName());
            this.save(localRepo);
            return localRepo;
        }

        return repo.get();
    }

    public RepositoryGitHub fetchById(long id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
        return GitHubConnect.getInstance().findRepositoryById(id);
    }

    @Override
    public void save(Repository repo) {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            repo.setAdded(new Date());
            repositories.add(repo);
            session.saveOrUpdate("a",repo);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Repository repo) {
        repositories.removeIf(x -> x.getId() == repo.getId());
        repositories.add(repo);
    }

    @Override
    public void delete(Repository repo) {
        repositories.remove(repo);
    }

    public long favCountForId(long id) {
        var repo = this.getAll().stream().filter(r -> r.getId() == id).findFirst();

        if (repo.isEmpty()) return 0;

        return UserDAO.getInstance()
                .getAll()
                .stream()
                .filter(u -> u.getFavRepos().contains(repo.get()))
                .count();
    }
}
