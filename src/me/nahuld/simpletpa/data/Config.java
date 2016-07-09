package me.nahuld.simpletpa.data;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.nahuld.simpletpa.Main;

public class Config {
	
	private Main main;
	
	private File dataFolder;
	private File file;
	
	private FileConfiguration config;
	
	public Config(Main main) {
		this.main = main;
		dataFolder = main.getDataFolder();
		file = new File(dataFolder, "config.yml");
		
		if (!dataFolder.exists()) dataFolder.mkdirs();
		if (!file.exists())
			main.saveResource("config.yml", false);
		config = YamlConfiguration.loadConfiguration(file);
		save();
	}

	public FileConfiguration getConfig() { return config; }
	
	public void save() { 
		try {
			config.save(file);
		} catch (Exception ex) {
			main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error while saving: " + file.getName());
		}
	}
}
