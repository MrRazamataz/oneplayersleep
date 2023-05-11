package raza.MrRazamataz;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBedEnterEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.TaskHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import cn.nukkit.level.Level;

public class Main extends PluginBase implements Listener {
    private final Map<Player, TaskHandler> sleepTasks = new ConcurrentHashMap<>();
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().info("§6OnePlayerSleep v3.0.0 by §aMrRazamataz §6has been §aenabled§6!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("§6OnePlayerSleep by §aMrRazamataz §6has been §cdisabled!");
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        getServer().getScheduler().scheduleDelayedTask(this, new Runnable() {
            @Override
            public void run(){
                if (player.isSleeping()) {
                    if (getServer().getOnlinePlayers().size() == 1) {
                        // If only one player is online and they are sleeping, set the time to day
                        player.getLevel().setTime(0);
                        // get current server day count
                        int day = getServer().getTick() / 24000;
                        getServer().broadcastMessage("§6Good morning! The sun rises on a new day. Day: " + day + "§r");
                        Level level = event.getBed().getLevel();
                        level.setRaining(false);
                        level.setThundering(false);
                        player.stopSleep();

                    } else {
                        // If more than one player is online, start a sleep task for the player
                        event.setCancelled(true);
                        player.setSpawn(player.getLocation());
                        player.sendMessage("§bSleeping...§r");
                        // send action bar to all players
                        for (Player p : getServer().getOnlinePlayers().values()) {
                            p.sendActionBar("§b" + player.getDisplayName() + " is sleeping...§r");
                        }
                        TaskHandler task = Server.getInstance().getScheduler().scheduleDelayedTask(Main.this, new Runnable() {
                            public void run() {
                                if (player.isSleeping()) {
                                    player.getLevel().setTime(0);
                                    Level level = event.getBed().getLevel();
                                    level.setRaining(false);
                                    level.setThundering(false);
                                    int day = getServer().getTick() / 24000;
                                    player.stopSleep();
                                    getServer().broadcastMessage("§6" + player.getDisplayName() + " has skipped the night. Good morning! Day: " + day + "§r");
                                } else { // if more than 1 player slept in a bed
                                    // check if day
                                    Level level = player.getLevel();
                                    long time = level.getTime();
                                    boolean isDaytime = time >= 0 && time < 12000 || time >= 24000 && time < 36000;
                                    int day = getServer().getTick() / 24000;
                                    getLogger().info(String.valueOf(isDaytime));
                                    if (isDaytime) {
                                        getServer().broadcastMessage("§6" + player.getDisplayName() + " has skipped the night. Good morning! Day: " + day + "§r");
                                    } else{
                                        player.sendMessage("§cYou have left your bed before morning.");
                                        // send action bar to all players
                                        for (Player p : getServer().getOnlinePlayers().values()) {
                                            p.sendActionBar("§c" + player.getDisplayName() + " has left their bed before morning.§r");
                                        }
                                    }
                                }
                                sleepTasks.remove(player);
                            }
                        }, 100); // Delay of 5 seconds (100 ticks) before skipping the night
                        sleepTasks.put(player, task);
                    }
                } else{
                    getLogger().warning("Player is detected as not sleeping. Server possibly lagging.");

                }
            }

        }, 5);
    }

    @EventHandler
    public void onPlayerQuit(cn.nukkit.event.player.PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TaskHandler task = sleepTasks.get(player);
        if (task != null) {
            task.cancel();
            sleepTasks.remove(player);
        }
    }
}
