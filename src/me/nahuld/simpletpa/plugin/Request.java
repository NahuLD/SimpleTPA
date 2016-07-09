package me.nahuld.simpletpa.plugin;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.nahuld.simpletpa.Main;
import me.nahuld.simpletpa.utils.Effect;
import me.nahuld.simpletpa.utils.Messager;
import me.nahuld.simpletpa.utils.ParticleEffect;

public class Request implements Listener {
	
	private Main main;
	
	private Player requester, requested;
	
	private FileConfiguration config;
	
	private Messager messager;
	
	private boolean accepted;
	private boolean denied;
	
	private boolean particle;
	
	public Request(Main main, Player requester, Player requested) {
		this.main = main;
		this.requested = requested;
		this.requester = requester;
		
		config = main.config().getConfig();
		messager = main.messager();
		
		accepted = false;
		denied = false;
		
		particle = false;
		
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	private void end() {
		try {
			HandlerList.unregisterAll(this);
			requested = null;
			requester = null;
			config = null;
			messager = null;
			main.manager().getRequests().remove(this);
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	public Player getRequester() { return requester; }
	
	public Player getRequested() { return requested; }
	
	public void deny() {
		denied = true;
		requested.sendMessage(messager.getMessage("request.deny.requested")
				.replace("%requester%", requester.getName()));
		requester.sendMessage(messager.getMessage("request.deny.requester")
				.replace("%requested%", requested.getName()));
		end();
	}
	
	public void send() {

		requested.sendMessage(messager.getMessage("request.recieved")
				.replace("%requester%", requester.getName()));
		requester.sendMessage(messager.getMessage("request.sent")
				.replace("%requested%", requested.getName()));
		
		new BukkitRunnable() {

			int tpaRequestTime = config.getInt("tpa.request-delay");
			
			@Override
			public void run() {
				if (accepted || denied) {
					cancel();
				}
				
				if (tpaRequestTime <= 0) {
					cancel();
					requester.sendMessage(messager.getMessage("request.timed-out")
							.replace("%requested%", requested.getName()));
					end();
				}

				tpaRequestTime--;
			}
			
		}.runTaskTimer(main, 0L, 20L);
	}
	
	public void accept() {
		accepted = true;
		
		requested.sendMessage(messager.getMessage("request.accept")
				.replace("%requester%", requester.getName()));
		requester.sendMessage(messager.getMessage("request.accepted")
				.replace("%requested%", requested.getName()));
		
		if (config.getBoolean("options.particles")) {
			particle = true;
			showParticles();
			requester.sendMessage(messager.getMessage("request.teleporting"));
			
			new BukkitRunnable() {

				int countdown = config.getInt("tpa.particles-countdown");
				
				@Override
				public void run() {
					
					if (countdown <= 0) {
						cancel();
						particle = false;
						requester.teleport(requested);
						end();
					}
					countdown--;
				}
				
			}.runTaskTimer(main, 0L, 20L);
			
		} else {
			requester.teleport(requested);
			end();
		}
	}
	
	private void showParticles() {
		
	    new BukkitRunnable() {
	    	
		    ParticleEffect particleEffect = ParticleEffect.valueOf(config.getString("options.particle.type"));

		    float radius = 1;
		    
		    int particles = 20;
		    
		    float grow = .2f;
		    
		    int rings = 12;
		    
		    int step = 0;

			@Override
			public void run() {

				if (!particle) cancel();
				
				if (requester != null) {
					Location location = requester.getLocation();
					if (location != null) {
						if (step > rings) { step = 0; }
					
		         		double x, y, z;
		         		y = step * grow;
		        		location.add(0, y, 0);
		        		for (int i = 0; i < particles; i++) {
		            		double angle = (double) 2 * Math.PI * i / particles;
		            		x = Math.cos(angle) * radius;
		            		z = Math.sin(angle) * radius;
		            		location.add(x, 0, z);
		            		new Effect().display(particleEffect, location);
		            		location.subtract(x, 0, z);
		        		}
		        		location.subtract(0, y, 0);
		        		step++;
					}
				}
			}
		    
	    }.runTaskTimer(main, 10L, 8L);
	}
	
	@EventHandler
	protected void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.equals(requester) && particle) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	protected void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player.equals(requested) || player.equals(requester)) {
			denied = true;
			end();
		}
	}
}
