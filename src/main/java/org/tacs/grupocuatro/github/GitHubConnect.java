package org.tacs.grupocuatro.github;

import java.util.List;

import org.tacs.grupocuatro.github.entity.RepositoriesGitHub;
import org.tacs.grupocuatro.github.enums.Order;
import org.tacs.grupocuatro.github.enums.Sort;
import org.tacs.grupocuatro.github.exceptions.GitHubConnectionException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.github.query.GitHubQueryBuilder;
import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;

public class GitHubConnect {
	
	/* Ejemplo de uso GitHub

	GitHubConnect conn = GitHubConnect.getInstance();
	conn.tryConnection();
	
	List<GitHubQueryDecorator> deco = new ArrayList<GitHubQueryDecorator>();
	deco.add((GitHubQueryDecorator) new Comparison(ValueType.STARS, Comparator.GREATER, 5));
	deco.add((GitHubQueryDecorator) new Operation(Operator.AND));
	deco.add((GitHubQueryDecorator) new Between(ValueType.FORKS,10,20));
	
	RepositoriesGitHub repo = conn.searchRepository(Order.ASC, Sort.STARS, deco);
	
	repo tiene links a las proximas llamadas de la API y
	una lista de los repositorios con los filtros pedidos 
	
	*/
	
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
		if(instance == null) {
			instance = new GitHubConnect();
		}
		return instance;
	}
	
	public void tryConnection() throws GitHubConnectionException {
				
		GitHubRequest request = new GitHubRequest(this.token);
		int code = request.doTest();
		
		if(code != 200) {
			throw new GitHubConnectionException();
		}
		
	}
	
	public RepositoriesGitHub searchRepository(Order order, Sort sort, List<GitHubQueryDecorator> decorators) throws GitHubRequestLimitExceededException {
		
		GitHubRequest request = new GitHubRequest(this.token);
		GitHubQueryBuilder query = new GitHubQueryBuilder();
		
		if (order != null) {
			query.setOrder(order);
		}
		
		if(sort != null) {
			query.setSort(sort);
		}
		
		decorators.forEach(x -> query.putDecorator(x));
		
		return request.doSearchRepository(query.build());
		
	}
	
	
	/*
	public checkLimit() {
		
		GitHubRequest request = new GitHubRequest(this.token);
		int a,b = request.checkLimit();
		
	}
	*/
	
	
}