import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class util {

    public static ItemStack getHead(Player player) {
        int lifePlayer = (int) player.getHealth();
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(player.getName());
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Custom head");
        skull.setLore(lore);
        skull.setOwner(player.getName());
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack getHead(Entity entity) {

        ItemStack item;

        if (entity instanceof Creeper){
            item = new ItemStack(Material.CREEPER_HEAD, 1, (short) 3);
        }else if (entity instanceof Skeleton){
            item = new ItemStack(Material.SKELETON_SKULL, 1, (short) 3);
        }else if (entity instanceof Zombie){
            item = new ItemStack(Material.ZOMBIE_HEAD, 1, (short) 3);
        }else if (entity instanceof WitherSkeleton){
            item = new ItemStack(Material.WITHER_SKELETON_SKULL, 1, (short) 3);
        }else {
            return null;
        }

        SkullMeta skull = (SkullMeta) item.getItemMeta();
        item.setItemMeta(skull);
        return item;
    }

    public static Location locationLerp(Location start, Location end, double percent) {
        double x = start.getX() + (end.getX() - start.getX()) * percent;
        double y = start.getY() + (end.getY() - start.getY()) * percent;
        double z = start.getZ() + (end.getZ() - start.getZ()) * percent;
        return new Location(start.getWorld(), x, y, z);
    }
}
