package me.nahuld.simpletpa.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nahuld.simpletpa.Main;
import me.nahuld.simpletpa.data.Settings;
import me.nahuld.simpletpa.utils.Messager;

public class RequestToggleCommand implements CommandExecutor {
	
	private Main main;
	
	private Messager messager;
	
	private Settings config;
	
	public RequestToggleCommand(Main main) {
		this.main = main;
		
		messager = main.messager();
		
		config = main.getSettings();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) sender.sendMessage(messager.getMessage("error.not-player"));
		else {
			Player player = (Player) sender;
			
			if (!player.hasPermission("simpletpa.tpa")) {
				player.sendMessage(messager.getMessage("error.no-permission"));
				return true;
			}
			
			String function = "ON";
			
			List<UUID> toggled = main.getToggled();
			List<String> configFile = config.getConfig().getStringList("toggled");
			if (configFile == null) configFile = new ArrayList<>();
			
			if (toggled.contains(player.getUniqueId())) {
				function = "OFF";
				toggled.remove(player.getUniqueId());
				configFile.remove(player.getUniqueId().toString());
			} else {
				toggled.add(player.getUniqueId());
				configFile.add(player.getUniqueId().toString());
			}
			
			String message = messager.getMessage("success.toggle").replace("%boolean%", function);
			
			config.getConfig().set("toggled", configFile);
			config.save();
			
			player.sendMessage(message);
		}
		
		return true;
	}
}
