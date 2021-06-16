package io.github.nhoj1000.proceduraldungeons;

import com.github.shynixn.structureblocklib.api.bukkit.StructureBlockLibApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Level;

public final class ProceduralDungeons extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        if(!this.getDataFolder().exists())
            this.getDataFolder().mkdir();
        getCommand("testcmd").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 2)
                return true;

            Path path = this.getDataFolder().toPath().resolve(args[1] + ".nbt");
            Location loc = player.getLocation();
            if (args[0].equals("save")) {
                StructureBlockLibApi.INSTANCE
                        .saveStructure(this)
                        .at(loc.subtract(10, 5, 10))
                        .sizeX(20)
                        .sizeY(20)
                        .sizeZ(20)
                        .saveToPath(path)
                        .onException(e -> this.getLogger().log(Level.SEVERE, "Failed to save structure.", e))
                        .onResult(e -> this.getLogger().log(Level.INFO, ChatColor.GREEN + "Saved structure " + args[1] + "."));
            } else if(args[0].equals("load")) {
                StructureBlockLibApi.INSTANCE
                        .loadStructure(this)
                        .at(loc.subtract(10, 5, 10))
                        .loadFromPath(path)
                        .onException(e -> this.getLogger().log(Level.SEVERE, "Failed to load structure.", e))
                        .onResult(e -> this.getLogger().log(Level.INFO, ChatColor.GREEN + "Loaded structure " + args[1] + "."));
            }
        }
        return true;
    }
}
