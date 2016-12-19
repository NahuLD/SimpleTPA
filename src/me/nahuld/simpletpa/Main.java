package me.nahuld.simpletpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import me.nahuld.simpletpa.commands.ReloadCommand;
import me.nahuld.simpletpa.commands.RequestAcceptCommand;
import me.nahuld.simpletpa.commands.RequestCommand;
import me.nahuld.simpletpa.commands.RequestDenyCommand;
import me.nahuld.simpletpa.commands.RequestHereCommand;
import me.nahuld.simpletpa.commands.RequestToggleCommand;
import me.nahuld.simpletpa.data.Config;
import me.nahuld.simpletpa.data.Settings;
import me.nahuld.simpletpa.plugin.RequestManager;
import me.nahuld.simpletpa.utils.Messager;

public class Main extends JavaPlugin {
	
	private List<UUID> toggled;
	
	private Config config;
	
	private Settings settings;
	
	private Messager messager;
	
	private RequestManager manager;
	
	@Override
	public void onEnable() {
		
		toggled = new ArrayList<>();
		
		messager = new Messager(this);
		
		config = new Config(this);
		
		settings = new Settings(this);
		
		manager = new RequestManager(this);
		
		for (String toggled : settings.getConfig().getStringList("toggled")) {
			this.toggled.add(UUID.fromString(toggled));
		}
		
		setupCommands();
	}
	
	private void setupCommands() {
		getServer().getPluginCommand("tpa").setExecutor(new RequestCommand(this));
		getServer().getPluginCommand("tpaccept").setExecutor(new RequestAcceptCommand(this));
		getServer().getPluginCommand("tpahere").setExecutor(new RequestHereCommand(this));
		getServer().getPluginCommand("tpdeny").setExecutor(new RequestDenyCommand(this));
		getServer().getPluginCommand("tptoggle").setExecutor(new RequestToggleCommand(this));
		getServer().getPluginCommand("simpletpareload").setExecutor(new ReloadCommand(this));
	}
	
	public Config config() { return config; };
	
	public Messager messager() { return messager; }
	
	public RequestManager manager() { return manager; }
	
	public List<UUID> getToggled() { return toggled; }
	
	public Settings getSettings() { return settings; }
}
