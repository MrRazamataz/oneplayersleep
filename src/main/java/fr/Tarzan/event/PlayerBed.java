package fr.Tarzan.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBedLeaveEvent;;
import cn.nukkit.level.Level;

public class PlayerBed implements Listener {
    @EventHandler
    public void onBed(PlayerBedLeaveEvent event){
        Player player = event.getPlayer();
        Level level = event.getBed().getLevel();
        level.setTime(Level.TIME_DAY);
        level.setRaining(false);
        level.setThundering(false);
        level.setRainTime(60 * 20);
        level.setThunderTime(60 * 20);
        player.setSpawn(player.getLocation());
        player.getServer().broadcastMessage("[§6§l!§r]It's §6Daytime!");
    }
}