package fr.Tarzan.event;

import cn.nukkit.Player;
import cn.nukkit.event.*;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBedLeaveEvent;
import cn.nukkit.level.Level;
import cn.nukkit.event.player.*;
import cn.nukkit.network.protocol.*;
import java.util.*;
public class PlayerBed implements Listener {
    @EventHandler
    public void onBed(PlayerBedLeaveEvent event){
        Player player = event.getPlayer();
        Level level = event.getBed().getLevel();
        float oldHealth = player.getHealth();
        player.attack(1);//.getHandle().a(true,DamageSource.CACTUS);
        player.setHealth(oldHealth);
        level.setTime(Level.TIME_DAY);
        level.setRaining(false);
        level.setThundering(false);
        player.setSpawn(player.getLocation());
        player.sendMessage("§aRespawn point set!");
        player.getServer().broadcastMessage("[§6OnePlayerSleep§r]§6 It's §6Daytime!");

    }
}