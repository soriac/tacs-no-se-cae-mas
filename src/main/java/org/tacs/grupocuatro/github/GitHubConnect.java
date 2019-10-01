package org.tacs.grupocuatro.github;

import org.tacs.grupocuatro.github.GitHubRequest.Type;
import org.tacs.grupocuatro.github.entity.RepositoriesGitHub;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.enums.Order;
import org.tacs.grupocuatro.github.enums.Sort;
import org.tacs.grupocuatro.github.exceptions.GitHubConnectionException;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.github.query.GitHubQueryBuilder;
import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GitHubConnect {
	

    private static GitHubConnect instance = null;
    private String token = null;

    private GitHubConnect() {

        String token = System.getenv("GITHUB_TACS");

        if (token == null) {
            System.out.println("WARNING - No esta definida la variable de ambiente GITHUB_TACS. Se procede a usar GitHub sin autorizacion");
        }

        this.token = token;

    }

    public static GitHubConnect getInstance() {
        if (instance == null) {
            instance = new GitHubConnect();
        }
        return instance;
    }

    public void tryConnection() throws GitHubConnectionException {
        GitHubRequest request = new GitHubRequest(this.token);
        int code = request.doTest();

        if (code != 200) {
            throw new GitHubConnectionException();
        }

    }
    
    
    public RepositoriesGitHub searchRepository(Order order, Sort sort, List<GitHubQueryDecorator> decorators, String... keyboards) throws GitHubRequestLimitExceededException {

        GitHubRequest request = new GitHubRequest(this.token);
        GitHubQueryBuilder query = new GitHubQueryBuilder();

        if (order != null) {
            query.setOrder(order);
        }

        if (sort != null) {
            query.setSort(sort);
        }
        
        Arrays.asList(keyboards).forEach(query::putKeyword);
        decorators.forEach(query::putDecorator);

        return request.doSearchRepository(query.build());
    
    }

    public RepositoryGitHub findRepositoryById(String id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
        GitHubRequest request = new GitHubRequest(this.token);
        return request.doRepositoryById(id);
    }
    
	public Map<String, Integer> getLimits() {
		
		GitHubRequest request = new GitHubRequest(this.token);
		return request.getLimits(Type.SEARCH);
		
	}
}
