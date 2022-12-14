import de.myzelyam.api.vanish.VanishAPI;
import juligame.epicswords2.API;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import util.UPECompatibility;

public class ESModule_Lunar {
    public static void main(){
        API.addArmorAction(
            "Invisibilidad",
            (portador, attacker, actionLevel, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
                    -> {
                double duracion = 20 * actionLevel * armorPieces;
                portador.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int) duracion - 5, 1, false, false));
                UPECompatibility.createUPEParticle("EnergiaAzul", portador, false ,1);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        VanishAPI.hidePlayer(portador);
                    }
                }.runTaskLater(API.pluginInstance, 5);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        VanishAPI.showPlayer(portador);
                        UPECompatibility.createUPEParticle("EnergiaAzul", portador, false ,1);
                    }
                }.runTaskLater(API.pluginInstance, (int) duracion);
            }
        );


        API.addArmorAction(
            "Rayo Lunar",
            (portador, attacker, actionLevel, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
                    -> {
                System.out.println(armorPieces);
                double duracion = 20 * armorPieces;
                double d = actionLevel;
                new BukkitRunnable() {
                    int timer = 0;
                    @Override
                    public void run() {
                        if (timer == duracion) {
                            this.cancel();
                            return;
                        }
                        int random = (int) (Math.random() * 4);
                        UPECompatibility.createUPEParticle("RayosAzules_"+random, portador, false ,1, false, false, 1);

                        for (Entity e : portador.getNearbyEntities(4, 4, 4)) {
                            if (!(e instanceof LivingEntity)) continue;
                            LivingEntity le = (LivingEntity) e;
                            if (le.getType() == EntityType.PLAYER) {
                                if (le == portador) {
                                    continue;
                                }
                                if (le.hasMetadata("NPC")) {
                                    continue;
                                }
                            }
                            le.damage(d);
                        }
                        portador.getWorld().playSound(portador.getLocation(), "minecraft:block.redstone_torch.burnout", 0.2f, 2);
                        timer += 10;
                    }
                }.runTaskTimer(API.pluginInstance, 0,10);
            });

        API.addComboAction(
                "Rayo Lunar",
                (portador, attacker, actionLevel, event) -> {
                    int damage = actionLevel;

                    int random = (int) (Math.random() * 4);
                    UPECompatibility.createUPEParticle("RayosAzules_"+random, portador, false ,4, false, false, 1);

                    for (Entity e : portador.getNearbyEntities(4, 4, 4)) {
                        if (!(e instanceof LivingEntity)) continue;
                        LivingEntity le = (LivingEntity) e;
                        if (le.getType() == EntityType.PLAYER) {
                            if (le == portador) {
                                continue;
                            }
                            if (le.hasMetadata("NPC")) {
                                continue;
                            }
                        }
                        le.damage(damage);
                    }
                    portador.getWorld().playSound(portador.getLocation(), "minecraft:block.redstone_torch.burnout", .4f, 2);
                });

    }

}
