package fr.Tarzan;

import cn.nukkit.plugin.PluginBase;
import fr.Tarzan.event.PlayerBed;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PlayerBed(), this);
        this.getLogger().info("§6OnePlayerSleep by §aMrRazamataz §6has been §aenabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("§6OnePlayerSleep by §aMrRazamataz §6has been §cdisabled!");
    }
}
