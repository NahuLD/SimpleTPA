package me.nahuld.simpletpa.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.nahuld.simpletpa.Main;

public class RequestManager {
	
	private List<Request> requests;
	
	public RequestManager(Main main) {
		requests = new ArrayList<>();
	}
	
	public List<Request> getRequests() { return requests; }
	
	public Request getRequester(Player requester) {
		for (Request request : requests) {
			if (request.getRequester().equals(requester))
				return request;
		}
		return null;
	}
	
	public Request getRequested(Player requested) {
		for (Request request : requests) {
			if (request.getRequested().equals(requested))
				return request;
		}
		return null;
	}
}
