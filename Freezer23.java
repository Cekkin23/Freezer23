package com.cekkin.freezer23;

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

public class Freezer23 extends JavaPlugin implements Listener
{
	public ArrayList<String> Frozen = new ArrayList<String>();
	String Prefix = getConfig().getString("Prefix");
	String cmderror = getConfig().getString("cmderror");
	String OnlyPlayer = getConfig().getString("OnlyPlayer");
	String MsgFrozen1 = getConfig().getString("MsgFrozen1");
	String MsgFrozen2 = getConfig().getString("MsgFrozen2");
	String MsgFrozen3 = getConfig().getString("MsgFrozen3");
	String MsgFrozenMove = getConfig().getString("MsgFrozenMove");
	String MsgUnfrozen = getConfig().getString("MsgUnfrozen");
	String playercmd = getConfig().getString("playercmd");
	String permission = getConfig().getString("permission");
	String AdminFreeze = getConfig().getString("AdminFreeze");
	String AdminUnfreeze = getConfig().getString("AdminUnfreeze");
	
	public void onEnable()
	{
		Frozen.clear();
		Bukkit.getLogger().info(this.Prefix + "Freezer23 ACTIVATED!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable()
	{
		Bukkit.getLogger().info(this.Prefix + "Freezer23 DESACTIVATED!");
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		
		if (Frozen.contains(e.getPlayer().getName()))
		{
			e.getPlayer().teleport(e.getPlayer());
			String MsgMoveFrozen = getConfig().getString("MsgMoveFrozen");
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', MsgMoveFrozen));
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
						sender.sendMessage(ChatColor.RED + "The player is offline!");
					}else{
						if(Frozen.contains(p.getName()))
						{
							Frozen.remove(p.getName());
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', AdminUnfreeze));
							p.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgUnfrozen));
							p.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
						}else{
							Frozen.add(p.getName());
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', AdminFreeze));
							p.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgFrozen1));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgFrozen2));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgFrozen3));
							p.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-------------------------------------");
						}
					}
				}
			}
			if (!sender.hasPermission("cekkin.freeze")) 
			{
	        	   sender.sendMessage(ChatColor.translateAlternateColorCodes('&', permission));
	        	   return true;
	        	} else {
	        	   if (!(sender instanceof Player))
	        	   {
	        		   this.Frozen.add(sender.getName());
	        		   sender.sendMessage(ChatColor.translateAlternateColorCodes('&', OnlyPlayer));
	        		   Bukkit.getLogger().info(this.Prefix + "Player " + sender.getName() + " successfully froze " + sender.getName());
	        		   return true;
	        	   }
	        	   
	        	}
			return true;
		}
	}
}
