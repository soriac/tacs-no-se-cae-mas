package org.tacs.grupocuatro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.tacs.grupocuatro.github.*;
import org.tacs.grupocuatro.github.entity.RepositoriesGitHub;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.enums.*;
import org.tacs.grupocuatro.github.exceptions.GitHubConnectionException;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;
import org.tacs.grupocuatro.github.query.decorators.*;

public class GitHubAPITests {
	
	@Test
	void connectionToAPI() throws GitHubConnectionException {
		
		GitHubConnect conn = GitHubConnect.getInstance();
		conn.tryConnection();
		
	}
	
	@Test
	void usingToken() {
		
		GitHubConnect conn = GitHubConnect.getInstance();
		assertEquals(30, conn.getLimits().get("limit"));;
		
	}
	
    @Test
    void getRepoWithDecoratorsWithoutKeywords() throws GitHubRequestLimitExceededException {
    	
		GitHubConnect conn = GitHubConnect.getInstance();
		
		List<GitHubQueryDecorator> deco = new ArrayList<GitHubQueryDecorator>();
		deco.add((GitHubQueryDecorator) new Comparison(ValueType.STARS, Comparator.GREATER, 5));
		deco.add((GitHubQueryDecorator) new Operation(Operator.AND));
		deco.add((GitHubQueryDecorator) new Between(ValueType.FORKS,10,20));
		
		RepositoriesGitHub repo = conn.searchRepository(Order.ASC, Sort.STARS, deco);
		
		assertTrue(repo.getRepos().size() > 0);
		
    }
    
    
    @Test
    void getRepoWithOutDecoratorsWithKeywords() throws GitHubRequestLimitExceededException {
    	
		GitHubConnect conn = GitHubConnect.getInstance();
		
		List<GitHubQueryDecorator> deco = new ArrayList<GitHubQueryDecorator>();
		
		RepositoriesGitHub repo = conn.searchRepository(Order.ASC, Sort.STARS, deco, "jquery", "test");
		
		assertTrue(repo.getRepos().size() > 0);
		
    }
    
    @Test
    void getRepoWithDecoratorsWithKeywords() throws GitHubRequestLimitExceededException {
    	
		GitHubConnect conn = GitHubConnect.getInstance();
		
		List<GitHubQueryDecorator> deco = new ArrayList<GitHubQueryDecorator>();
		deco.add((GitHubQueryDecorator) new Comparison(ValueType.STARS, Comparator.GREATER, 5));
		deco.add((GitHubQueryDecorator) new Operation(Operator.AND));
		deco.add((GitHubQueryDecorator) new Between(ValueType.FORKS,10,20));
		
		RepositoriesGitHub repo = conn.searchRepository(Order.ASC, Sort.STARS, deco, "jquery", "test");
		
		assertTrue(repo.getRepos().size() > 0);
		
    }
    
    @Test
    void getRepoById() throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
    	
		GitHubConnect conn = GitHubConnect.getInstance();
		RepositoryGitHub repo = conn.findRepositoryById(20633049);
		
    	assertNotNull(repo);
    	
    }
    
    @Test
    void getRepoByIdException() throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
    	
		GitHubConnect conn = GitHubConnect.getInstance();
    	
		assertThrows(GitHubRepositoryNotFoundException.class, () ->
			{conn.findRepositoryById(981938123);
		});    
    	
    }
    

    
}
