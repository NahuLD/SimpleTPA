package me.nahuld.simpletpa.plugin;

public enum RequestType {
	GO_TO_REQUESTED("normal"),
	GO_TO_REQUESTER("here");
	
	private String name;
	
	private RequestType(String name) {
		this.name = name;
	}
	
	public String getType() { return name; }
}
