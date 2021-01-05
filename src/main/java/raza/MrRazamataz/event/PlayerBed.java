package raza.MrRazamataz.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.*;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBedEnterEvent;
import cn.nukkit.level.Level;
import cn.nukkit.event.player.*;
import cn.nukkit.network.protocol.*;
import java.util.*;
public class PlayerBed implements Listener {
    @EventHandler
    public void onBed(PlayerBedEnterEvent event){
        Player player = event.getPlayer();
        Level level = event.getBed().getLevel();
        level.setRaining(false);
        level.setThundering(false);
        player.setSpawn(player.getLocation());
        event.setCancelled();
        player.sendMessage("§aRespawn point set!");
        player.getServer().broadcastMessage("[§6OnePlayerSleep§r]§6 It's §6Daytime!");
        level.setTime(Level.TIME_DAY);

    }

    {
    }
}