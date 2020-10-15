package dev.dejay.reactav;

import dev.dejay.reactav.listeners.ServerStartupListener;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.var;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReactAV extends JavaPlugin {

    public boolean shutdown;
    public String problematicPath;
    Path pluginsDir;

    @Override
    public void onEnable() {
        try {
            pluginsDir = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (SecurityException | URISyntaxException e) {
            getServer().getLogger().severe("Failed to get plugin folder, aborting.");
            getServer().getPluginManager().disablePlugin(this);
        }

        // I gotta load this event first, then do the task
        getServer().getPluginManager().registerEvents(new ServerStartupListener(), this);
        testPlugins();
    }

    @SneakyThrows // Lombok is truly saving this project
    public Stream<Path> getPlugins() {
        return Files.list(pluginsDir).filter(Files::isRegularFile);
    }

    @SneakyThrows // I don't EXPECT this to fail. But, worst case. Do nothing.
    public void testPlugins() {
        for (var plugin : getPlugins().toArray()) { // Lombok is a gift to this world
            FileSystem pluginFS = FileSystems.newFileSystem((Path) plugin, (ClassLoader) null); // disgusting cast here because maven will get salty
            if (Files.exists(pluginFS.getPath("/javassist"))) {
                log(plugin.toString());
                problematicPath = plugin.toString();
                shutdown = true;
            }
        }
    }

    public void log(String path){
        getLogger().severe("!!! A PLUGIN WAS DETECTED TO CONTAIN JAVASSIST !!!");
        getLogger().severe("\"But what does this mean?\" - You, Probably");
        getLogger().severe("Though React is not 100% sure why a plugin would contain this, ");
        getLogger().severe("but in many cases these plugins contain a virus/backdoor that allows the host to run commands.");
        getLogger().severe("The host also gets a pretty package of all your online players and your logs! (IP Addresses!) That's not great.");
        getLogger().severe("In an effort to help you, React has simply turned off the server. You may want to look into: ");
        getLogger().severe(path); // Again, redundant cast but I need the compiler to know what I want.
    }

    public static ReactAV get() {
        return JavaPlugin.getPlugin(ReactAV.class); // Genius idea from a friend. Thanks ;)
    }

} // Lombok Appreciation Post
