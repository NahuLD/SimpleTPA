package me.nahuld.simpletpa;

import org.bukkit.plugin.java.JavaPlugin;

import me.nahuld.simpletpa.commands.RequestAcceptCommand;
import me.nahuld.simpletpa.commands.RequestCommand;
import me.nahuld.simpletpa.commands.RequestDenyCommand;
import me.nahuld.simpletpa.data.Config;
import me.nahuld.simpletpa.plugin.RequestManager;
import me.nahuld.simpletpa.utils.Messager;

public class Main extends JavaPlugin {
	
	private Config config;
	
	private Messager messager;
	
	private RequestManager manager;
	
	@Override
	public void onEnable() {
		
		messager = new Messager(this);
		
		config = new Config(this);
		
		manager = new RequestManager(this);
		
		setupCommands();
	}
	
	private void setupCommands() {
		getServer().getPluginCommand("tpa").setExecutor(new RequestCommand(this));
		getServer().getPluginCommand("tpaccept").setExecutor(new RequestAcceptCommand(this));
		getServer().getPluginCommand("tpdeny").setExecutor(new RequestDenyCommand(this));
	}
	
	public Config config() { return config; };
	
	public Messager messager() { return messager; }
	
	public RequestManager manager() { return manager; }
}
