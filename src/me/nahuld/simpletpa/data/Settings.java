package me.nahuld.simpletpa.data;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.nahuld.simpletpa.Main;

public class Settings {
	private Main main;
	
	private File dataFolder;
	private File file;
	
	private FileConfiguration config;
	
	public Settings(Main main) {
		this.main = main;
		dataFolder = main.getDataFolder();
		file = new File(dataFolder, "settings.yml");
		
		if (!dataFolder.exists()) dataFolder.mkdirs();
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
