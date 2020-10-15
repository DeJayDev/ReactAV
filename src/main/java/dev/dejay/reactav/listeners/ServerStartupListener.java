package dev.dejay.reactav.listeners;

import dev.dejay.reactav.ReactAV;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerStartupListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST) // Lowest Priority allows me to be the first
    public void onServerStartup(ServerLoadEvent event) {
        if (ReactAV.get().shutdown) {
            ReactAV.get().log(ReactAV.get().problematicPath);
            Bukkit.getServer().shutdown(); // bye :D
        }
    }

}
