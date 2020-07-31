package fr.Tarzan;

import cn.nukkit.plugin.PluginBase;
import fr.Tarzan.event.PlayerBed;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PlayerBed(), this);
        this.getLogger().info("Plugin Enable");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin Disable");
    }
}
