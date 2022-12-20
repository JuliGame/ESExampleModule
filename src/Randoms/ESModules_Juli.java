package Randoms;

import juligame.epicswords2.API;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ESModules_Juli {
    public static void main() {


        API.addComboAction("Tornado",
            (player, target, actionLevel, event) -> {

                float duration = 40;
                float distance = 15;

                Location pl = player.getLocation();

                Vector dirBetweenLocations = target.getLocation().toVector().subtract(pl.toVector());
                Location loc = target.getLocation();
                loc.setDirection(dirBetweenLocations);

                LivingEntity invincible = target.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
                    armorStand.setInvulnerable(true);
                    armorStand.setGravity(false);
                    armorStand.setVisible(false);
                    armorStand.setMarker(true);
                });


                new BukkitRunnable(){
                    int time = 0;
                    @Override
                    public void run(){
                        if (time >= duration+1){
                            this.cancel();
                            invincible.remove();
                        }

                        invincible.teleport(loc);
                        loc.add(loc.getDirection().multiply(distance/duration));

                        List<Entity> nearbyEntites = new ArrayList<>(loc.getWorld().getNearbyEntities(loc, 2, 5, 2));
                        nearbyEntites.remove(player);
                        nearbyEntites.forEach(entity -> {
                            entity.setVelocity(loc.getDirection().multiply(0.5).add(new Vector(0, 0.5, 0)));
                        });

                        time++;
                    }
                }.runTaskTimer(API.pluginInstance, 0, 1);


                UPECompatibility.createUPEParticle("Tornado", invincible, false, 1.5f);
            });


        API.addComboAction("Wither",
                (player, target, actionLevel, event) -> {

                    target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, (actionLevel/2) + 1));
                    if (actionLevel < 10) return;

                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            target.damage(2*actionLevel);
                            target.getWorld().playSound(target.getLocation(), "entity.wither.break_block", 1, 0);
                        }
                    }.runTaskLater(API.pluginInstance, 15 );
                    target.getWorld().playSound(player.getLocation(), "entity.ghast.death", 1, 0);
                    UPECompatibility.createUPEParticle("Calavera", target, false, 1.5f, true, true, .5f);
                });

        API.addComboAction("DoubleAttack",
            (player, target, actionLevel, event) -> {
                player.attack(target);
                target.getWorld().spawnParticle(Particle.CRIT, target.getLocation(), 20, 0.5, 0.5, 0.5, 0.1);
            });

        API.addComboAction("Stun", (player, target, level, event) -> {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 20, 100));
            UPECompatibility.createUPEParticle("Stun", target, false, 1.5f, true, true, 1);
        });
    }
}
