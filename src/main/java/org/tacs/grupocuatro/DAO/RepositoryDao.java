package org.tacs.grupocuatro.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.tacs.grupocuatro.entity.Repository;

public class RepositoryDao implements Dao<Repository>{
	
    private List<Repository> repositories = new ArrayList<>();
    
    @Override
    public Optional<Repository> get(long id) {
        return Optional.ofNullable(repositories.get((int) id));
    }
	
	@Override
    public List<Repository> getAll() {
        return repositories;
    }

    @Override
    public void save(Repository repo) {
        repositories.add(repo);
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

	public int numRepositories() {
		return repositories.size();
	}
	
}
