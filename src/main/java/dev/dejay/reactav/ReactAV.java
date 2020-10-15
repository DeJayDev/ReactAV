package dev.dejay.reactav;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReactAV extends JavaPlugin {

    Path pluginsDir;

    @Override
    public void onEnable() {
        try {
            pluginsDir = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (SecurityException | URISyntaxException e) {
            getServer().getLogger().severe("Failed to get plugin folder, aborting.");
            getServer().getPluginManager().disablePlugin(this);
        }

        getServer().getLogger().info(pluginsDir.toString());
    }

}
