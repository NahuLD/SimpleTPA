package me.nahuld.simpletpa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nahuld.simpletpa.Main;
import me.nahuld.simpletpa.plugin.Request;
import me.nahuld.simpletpa.utils.Messager;

public class RequestCommand implements CommandExecutor {

	private Main main;
	
	private Messager messager;
	public RequestCommand(Main main) {
		this.main = main;
		messager = main.messager();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) sender.sendMessage(messager.getMessage("error.not-player"));
		else {
			Player requester = (Player) sender;

			if (requester.hasPermission("simpletpa.tpa")) {
				requester.sendMessage(messager.getMessage("error.no-permission"));
				return true;
			}
			
			if (args.length <= 0) requester.sendMessage(messager.getMessage("error.syntax"));
			else {
				
				//Let's check if he already sent another invitation.
				Request check = main.manager().getRequester(requester);
				if (check != null) requester.sendMessage(messager.getMessage("error.already-sent"));
				else {
				
					Player requested = Bukkit.getPlayer(args[0]);
				
					if (requested != null) {
					
						Request request = new Request(main, requester, requested);
						request.send();
					
						main.manager().getRequests().add(request);
					
					} else requester.sendMessage(messager.getMessage("error.not-found"));
				}
			}
		}
		
		return true;
	}
}
