package Randoms;

import dev.esophose.playerparticles.particles.FixedParticleEffect;
import juligame.epicswords2.API;
import juligame.epicswords2.utils.colors;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

public class ESModule_Weapons {
    public static void main() {


        API.addComboAction("Metabolismo",
                (player, target, actionLevel, event) -> {
                    if (target instanceof Player){
                        Player targetPlayer = (Player) target;

                        int comida = targetPlayer.getFoodLevel() - actionLevel;
                        targetPlayer.setFoodLevel(comida);
                    }
                }
        );
        API.addComboAction("Rologod_Oscuridad demoniaca",
        (player, target, actionLevel, event) -> {
            if (target instanceof  Player) {
                Player targetPlayer = (Player) target;
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200 + 3 * actionLevel, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200 + 3 * actionLevel, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200 + 3 * actionLevel, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200 + 3 * actionLevel, 1));
                target.getWorld().spawnParticle(Particle.SMOKE_NORMAL, target.getLocation(), 80);
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, target.getLocation(), 80);
                   }
              }
                   );


        API.addComboAction("Rologod_Maldicion de las mareas",
       (player, target, actionLevel, event) -> {
            if (target instanceof  Player) {
                Player targetPlayer = (Player) target;
                ((Player) target).spawnParticle(Particle.MOB_APPEARANCE, target.getLocation(), 80);
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200 + 3 * actionLevel, 1));
            }
        }
        );
        API.addComboAction("Rologod_Materializador dorado",
                (player, target, actionLevel, event) -> {
                    if (target instanceof  Player) {
                        ItemStack manzanas = new ItemStack(Material.GOLDEN_APPLE);
                        double randDouble = Math.random();

                        if(randDouble <= 0.8D) {
                            Player targetPlayer = (Player) target;
                            String prefix = ChatColor.GRAY+"["+ChatColor.BLUE+ChatColor.BOLD +"Exilon"+ChatColor.AQUA+ChatColor.BOLD+"MC"+ChatColor.GRAY+"]" ;
                            player.sendMessage(prefix + colors.Companion.next()+" Obtuviste una Manzana dorada por tu COMBO");
                            player.getInventory().addItem(manzanas);
                        }

                    }
                }
        );
        API.addComboAction("FlyWhenKill",
                (player, target, actionLevel, event) -> {
                    boolean estamuerto = target.getHealth() - event.getFinalDamage() <= 0;

                    if (estamuerto){
                        player.setAllowFlight(true);
                        player.sendMessage(colors.Companion.next() + "Bufo de volar activado");

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.setAllowFlight(false);
                                player.setFlying(false);
                            }
                        }.runTaskLater(API.pluginInstance, 200 + 20 * actionLevel);
                        DrawCircle(player, 1, Particle.FLAME, 200);
                    }
                });


        API.addComboAction("Decapitador",
                (player, target, actionLevel, event) -> {
                    boolean estamuerto = target.getHealth() - event.getFinalDamage() <= 0;

                    if (estamuerto){
                        ItemStack item;
                        if (target instanceof Player){
                            item = util.getHead((Player) target);
                        }else {
                            item = util.getHead(target);
                        }
                        if (item == null) return;
                        target.getWorld().dropItem(target.getLocation(), item);
                        UPECompatibility.createUPEParticle("Cortada", player, false, 1, true, true, 1);
                        UPECompatibility.createUPEParticle("Veneno", target, false, 30, true, false, 1, false, Particle.FALLING_LAVA);
                    }
                });

        API.addComboAction("Swap",
                (player, target, actionLevel, event) -> {
                    Location pl = player.getLocation();

                    Vector dirBetweenLocations = player.getLocation().toVector().subtract(target.getLocation().toVector());
                    Location loc = target.getLocation();
                    loc.setDirection(dirBetweenLocations);
                    player.teleport(loc);

                    target.teleport(pl);

                    target.getWorld().spawnParticle(Particle.PORTAL, target.getLocation(), 25);
                    player.getWorld().spawnParticle(Particle.PORTAL, target.getLocation(), 25);
                });

        API.addComboAction("yunque",
                (player, target, actionLevel, event) -> {
                    new BukkitRunnable() {
                        int times = 10*actionLevel;
                        @Override
                        public void run() {
                            Location loc = target.getLocation().add(0,10,0);

                            for (int x = -2; x <= 2; x++){
                                for (int z = -2; z <= 2; z++){
                                    int r = new Random().nextInt(2) + 1;
                                    if (r <= 1){
                                        loc.getWorld().spawnEntity(loc.add(x,0,z), EntityType.SNOWBALL);
                                    }
                                }
                            }


                            times--;
                            if (times == 0) this.cancel();
                        }
                    }.runTaskTimer(API.pluginInstance, 0 ,1);
                });

        API.addComboAction("Fuego_Facha",
                (player, target, actionLevel, event) -> {
                    target.setFireTicks(20*actionLevel);
                    DrawCircle(player, 1, Particle.FLAME, 200);
                    DrawCircle(player, 1.2f, Particle.SMOKE_NORMAL, 200);
                });
    }

    static public void DrawCircle(LivingEntity target, float r, Particle particle, int duration)
    {
        new BukkitRunnable() {
            int time = duration;
            private double angle = 180;
            @Override
            public void run() {
                double x = (r * Math.sin(angle));
                double z = (r * Math.cos(angle));
                angle -= 0.1;

                target.getWorld().spawnParticle(particle,
                        target.getLocation().getX() + x,
                        target.getLocation().getY() + 0.1,
                        target.getLocation().getZ() + z,
                        0, 0, 0.1, 0);

                target.getWorld().spawnParticle(particle,
                        target.getLocation().getX() - x,
                        target.getLocation().getY() + 0.1,
                        target.getLocation().getZ() - z,
                        0, 0, 0.1, 0);

                target.getWorld().spawnParticle(particle,
                        target.getLocation().getX() + x,
                        target.getLocation().getY() + 0.1,
                        target.getLocation().getZ() - z,
                        0, 0, 0.1, 0);

                target.getWorld().spawnParticle(particle,
                        target.getLocation().getX() - x,
                        target.getLocation().getY() + 0.1,
                        target.getLocation().getZ() + z,
                        0, 0, 0.1, 0);


                time--;
                if (time == 0) this.cancel();
            }
        }.runTaskTimer(API.pluginInstance, 0 ,1);
    }
}
