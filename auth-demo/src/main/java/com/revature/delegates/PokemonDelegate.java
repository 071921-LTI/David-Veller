package com.revature.delegates;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Pokemon;
import com.revature.models.User;
import com.revature.services.PokemonService;
import com.revature.services.PokemonServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

public class PokemonDelegate implements Delegatable {

	PokemonService ps = new PokemonServiceImpl();
	UserService us = new UserServiceImpl();

	@Override
	public void process(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		String method = rq.getMethod();

		switch (method) {
		case "GET":
			handleGet(rq, rs);
			break;
		case "POST":
			handlePost(rq, rs);
			break;
		case "PUT":
			handlePut(rq, rs);
			break;
		case "DELETE":
			handleDelete(rq, rs);
			break;
		default:
			rs.sendError(405);
		}
	}

	@Override
	public void handleGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		String pathNext = (String) rq.getAttribute("pathNext");
		String token = rq.getHeader("Authorize");

		if (token == null) {
			rs.sendError(403, "get out");
		} else {

			if (pathNext == null) {
				
				List<Pokemon> pokemons = ps.getPokemon();
				try (PrintWriter pw = rs.getWriter()) {
					pw.write(new ObjectMapper().writeValueAsString(pokemons));
				}

			}else {
				Pokemon pokemon = ps.getPokemonById(Integer.valueOf(pathNext));
				if (Integer.valueOf(token.split(":")[0]) != pokemon.getUser().getId()) {
					rs.sendError(403, "get out");
				}else {
					try {
						pokemon.setUser(us.getUserById(pokemon.getUser().getId()));
					} catch (UserNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try (PrintWriter pw = rs.getWriter()) {
						pw.write(new ObjectMapper().writeValueAsString(pokemon));
					}
				}
			}
		}
	}

	@Override
	public void handlePut(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {

	}

	@Override
	public void handlePost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {

		InputStream request = rq.getInputStream();
		// Converts the request body into a User.class object
		Pokemon pokemon = new ObjectMapper().readValue(request, Pokemon.class);

		if (!ps.addPokemon(pokemon)) {
			rs.sendError(400, "Unable to add pokemon.");
		} else {
			rs.setStatus(201);
		}

	}

	@Override
	public void handleDelete(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
