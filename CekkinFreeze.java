package com.cekkin.cekkinfreeze;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CekkinFreeze extends JavaPlugin implements Listener
{
	public ArrayList<String> congelados = new ArrayList<String>();
	String Prefix = getConfig().getString("Prefix");
	String cmderror = getConfig().getString("cmderror");
	String SoloJugador = getConfig().getString("SoloJugador").replaceAll("(&([a-f0-9]))", "\u00A7$2");
	String MsgCongelado1 = getConfig().getString("MsgCongelado1");
	String MsgCongelado2 = getConfig().getString("MsgCongelado2");
	String MsgCongelado3 = getConfig().getString("MsgCongelado3");
	String MsgMoveCongelado = getConfig().getString("MsgMoveCongelado");
	String MsgDescongelado = getConfig().getString("MsgDescongelado");
	String playercmd = getConfig().getString("playercmd").replaceAll("(&([a-f0-9]))", "\u00A7$2");
	String permission = getConfig().getString("permission").replaceAll("(&([a-f0-9]))", "\u00A7$2");
	
	public void onEnable()
	{
		congelados.clear();
		Bukkit.getLogger().info(this.Prefix + "CekkinFreeze ACTIVADO!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable()
	{
		Bukkit.getLogger().info(this.Prefix + "CekkinFreeze DESACTIVADO!");
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		
		if (congelados.contains(e.getPlayer().getName()))
		{
			e.getPlayer().teleport(e.getPlayer());
			String MsgMoveCongelado = getConfig().getString("MsgMoveCongelado");
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', MsgMoveCongelado));
			return;
		}
		
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		{
			if (cmd.getName().equalsIgnoreCase("freeze"))
			{
				if(args.length == 0)
				{
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + cmderror));
				}else{
					Player p = Bukkit.getPlayer(args[0]);
					if (p == null)
					{
						sender.sendMessage(ChatColor.RED + "El jugador esta offline");
					}else{
						if(congelados.contains(p.getName()))
						{
							congelados.remove(p.getName());
							p.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgDescongelado));
							p.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
						}else{
							congelados.add(p.getName());
							p.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgCongelado1));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgCongelado2));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgCongelado3));
							p.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
						}
					}
				}
			}
			if (!sender.hasPermission("cekkin.freeze")) 
			{
	        	   sender.sendMessage(ChatColor.RED + "No tienes permisos!");
	        	   return true;
	        	} else {
	        	   if (!(sender instanceof Player))
	        	   {
	        		   this.congelados.add(sender.getName());
	        		   sender.sendMessage(ChatColor.DARK_RED + "Este comando solo se puede ejecutar como jugador.");
	        		   Bukkit.getLogger().info(this.Prefix + "Player " + sender.getName() + " successfully froze " + sender.getName());
	        		   return true;
	        	   }
	        	   
	        	}
			return true;
		}
	}
}
	           
