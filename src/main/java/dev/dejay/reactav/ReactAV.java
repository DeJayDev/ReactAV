package dev.dejay.reactav;

import java.io.IOException;
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

    Path pluginsDir;

    @Override
    public void onEnable() {
        try {
            pluginsDir = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (SecurityException | URISyntaxException e) {
            getServer().getLogger().severe("Failed to get plugin folder, aborting.");
            getServer().getPluginManager().disablePlugin(this);
        }

        testPlugins();
    }

    @SneakyThrows // Lombok is truly saving this project
    public Stream<Path> getPlugins() {
           return Files.list(pluginsDir).filter(Files::isRegularFile);
    }

    @SneakyThrows // I don't EXPECT this to fail. But, worst case. Do nothing.
    public void testPlugins()  {
        for(var plugin : getPlugins().toArray()) { // Lombok is a gift to this world
            FileSystem pluginFS = FileSystems.newFileSystem((Path) plugin, (ClassLoader) null); // disgusting cast here because maven will get salty
            if(Files.exists(pluginFS.getPath("/javassist"))) {
                for (int i = 0; i < 50; i++) {
                    getServer().getLogger().severe("!!! A PLUGIN WAS DETECTED TO CONTAIN JAVASSIST !!!");
                }
                getServer().shutdown();
            }
        }
    }

} // Lombok Appreciation Post
