package com.revature.daos;

import java.util.List;

import com.revature.models.Pokemon;

public interface PokemonDao {
	
	Pokemon add(Pokemon p);
	Pokemon getById(int id);
	List<Pokemon> getAll();
	void update(Pokemon p);
	void delete(Pokemon p);

}
