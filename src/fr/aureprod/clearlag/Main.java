package fr.aureprod.clearlag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin 
{
	private Boolean coffreON = true;
	private float seconds_base = (float) this.getConfig().getConfigurationSection("global").getInt("temps");
	private float seconds = seconds_base;
	public Main main = this;
	private BukkitTask taskA;
	private BukkitTask taskB;
	
	private void boucleA()
	{
		taskA = Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() 
		{			
			@Override
			public void run()
			{
				if(coffreON)
				{
					Bukkit.getScheduler().cancelTask(taskA.getTaskId());
					boucleB();
				}
			}
		}, (long)(seconds * 20), (long)(seconds * 20)); // Always multiply by twenty because that's the amount of ticks in Minecraft
	}
	
	private void boucleB()
	{
		taskB = Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() 
		{		
			private Integer timer = 60;
			@Override
			public void run()
			{
				if(coffreON)
				{
					if (timer == 60 && getConfig().getConfigurationSection("global.message60").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message60").getString("message"));
					}
					else if (timer == 30 && getConfig().getConfigurationSection("global.message30").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message30").getString("message"));
					}
					else if (timer == 10 && getConfig().getConfigurationSection("global.message10").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message10").getString("message"));
					}
					else if (timer == 5 && getConfig().getConfigurationSection("global.message5").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message5").getString("message"));
					}
					else if (timer == 4 && getConfig().getConfigurationSection("global.message4").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message4").getString("message"));
					}
					else if (timer == 3 && getConfig().getConfigurationSection("global.message3").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message3").getString("message"));
					}
					else if (timer == 2 && getConfig().getConfigurationSection("global.message2").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message2").getString("message"));
					}
					else if (timer == 1 && getConfig().getConfigurationSection("global.message1").getBoolean("ok")) 
					{
						Bukkit.getServer().broadcastMessage(getConfig().getConfigurationSection("global.message1").getString("message"));
					}
					else if (timer == 0)
					{	
						Integer quantite = 0;
						
						for (World w : Bukkit.getServer().getWorlds()) 
						{
							for (Entity e : w.getEntities()) 
							{
								if (e.getType() == EntityType.DROPPED_ITEM) 
								{
									quantite++;
									
									e.remove();
								}
								else if (e.getType() == EntityType.ARROW) 
								{
									quantite++;
									
									e.remove();
								}
							}
						}
						
						if (getConfig().getConfigurationSection("global.message_clearlag").getBoolean("ok")) 
						{						
							String s = getConfig().getConfigurationSection("global.message_clearlag").getString("message");
							s = s.replaceAll("%QUANTITE%", "" + quantite);
							
							Bukkit.getServer().broadcastMessage(s);
						}
						
						Bukkit.getScheduler().cancelTask(taskB.getTaskId());
						boucleA();
					}
					
					timer--;
				}
				else
				{
					Bukkit.getScheduler().cancelTask(taskB.getTaskId());
					boucleA();
				}
				//Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new coffrealeatoire(this), (long)(this.seconds * 20)); // Always multiply by twenty because that's the amount of ticks in Minecraft
			}
		}, 0, (long) 20); // Always multiply by twenty because that's the amount of ticks in Minecraft
	}
	
	@Override
	public void onEnable() 
	{
		saveDefaultConfig();
		
		System.out.println("Le plugin clear lag est est demarre !!!");
		getCommand("start_clearlag").setExecutor(this);
		getCommand("stop_clearlag").setExecutor(this);
		
		boucleA();
	}
	
	@Override
	public void onDisable() 
	{
		System.out.println("Le plugin clear lag est arreter !!!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) 
	{
		if (cmd.getName().equalsIgnoreCase("start_clearlag"))
		{
			if (args.length == 0)
			{
				coffreON = true;
				
				if (sender instanceof Player) 
				{
					sender.sendMessage(ChatColor.GREEN + "Le clear lag est sur ON ...");
					System.out.println("Le clear lag est sur ON ...");
				}
				else
				{
					System.out.println("Le clear lag est sur ON ...");
				}
				
				return true;
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Il ne faut pas mettre d'arguments dans cette commande !!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("stop_clearlag"))
		{
			if (args.length == 0)
			{
				coffreON = false;
				
				if (sender instanceof Player) 
				{
					sender.sendMessage(ChatColor.RED + "Le clear lag est sur OFF ...");
					System.out.println("Le clear lag est sur OFF ...");
				}
				else
				{
					System.out.println("Le clear lag est sur OFF ...");
				}
				
				return true;
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Il ne faut pas mettre d'arguments dans cette commande !!");
			}
		}
		return false;
	}
}
