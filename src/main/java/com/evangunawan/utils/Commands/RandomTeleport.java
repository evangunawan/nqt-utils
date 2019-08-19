package com.evangunawan.utils.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RandomTeleport implements CommandExecutor {

    private double[] generateRandomPosition(Player player){
        double[] randomPos = new double[]{0.0,0.0,0.0};
        //Position X
        randomPos[0] = (-100000 + (Math.random() * ((100000 + 100000) + 1)));
        //Position Z
        randomPos[2] = (-100000 + (Math.random() * ((100000 + 100000) + 1)));
        //Position Y (SAFE)
        randomPos[1] = player.getWorld().getHighestBlockYAt((int)randomPos[0],(int)randomPos[2]) + 2;
        return randomPos;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player || sender instanceof ConsoleCommandSender){
            if(sender.hasPermission("utils.tprandom")){
                if(args.length == 0 && sender instanceof Player){
                    Player player = (Player)sender;
                    double[] targetPos = generateRandomPosition(player);
                    Location targetLoc = new Location(player.getWorld(), targetPos[0],targetPos[1],targetPos[2]);
                    player.teleport(targetLoc);
                    player.sendMessage(ChatColor.GREEN + "Successfully teleported to a random position.");
                    return true;
                }
                else if(args.length == 1){
                    if(!sender.hasPermission("utils.tprandom.others")){
                        sender.sendMessage(ChatColor.RED + "You do not have permission to do this.");
                        return true;
                    }
                    Player targetPlayer = Bukkit.getPlayer(args[0]);
                    if(targetPlayer != null){
                        double[] targetPos = generateRandomPosition(targetPlayer);
                        Location targetLoc = new Location(targetPlayer.getWorld(), targetPos[0],targetPos[1],targetPos[2]);
                        targetPlayer.teleport(targetLoc);
                        sender.sendMessage(ChatColor.GREEN + "Successfully teleported player to a random position.");
                        targetPlayer.sendMessage(ChatColor.GOLD + "You have moved to a random position.");
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.RED + "Player not found.");
                    }

                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to do this.");
                return true;
            }

        }
        return false;
    }
}
