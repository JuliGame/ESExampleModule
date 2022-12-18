import de.myzelyam.api.vanish.VanishAPI;
import de.myzelyam.supervanish.SuperVanish;
import juligame.epicswords2.API;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ESModule_Armors {
    public static void main(){
        API.addArmorAction("La Toxi",
                (portador, attacker, actionLevel, damage, finalDamage, event, amountOfPieces) -> {

                    int duracion = 90 + actionLevel * 10;

                    Location posicion = portador.getLocation();

                    Entity entidad = posicion.getWorld().spawnEntity(posicion, EntityType.AREA_EFFECT_CLOUD);
                    AreaEffectCloud efecto = (AreaEffectCloud) entidad;

                    efecto.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, duracion, 1, true, true), true);

                    new BukkitRunnable() {
                        int timer = 0;
                        @Override
                        public void run() {
                            portador.removePotionEffect(PotionEffectType.SLOW);
                            timer++;
                            if (timer == duracion + 200) this.cancel();
                        }
                    }.runTaskTimer(API.pluginInstance, 0, 1);



                });


        API.addArmorAction("ResuDorado",
                (portador, attacker, actionLevel, damage, finalDamage, event, amountOfPieces) -> {
                    System.out.println("DEBUG: Etapa 1 cargada");
                    ESModule_Weapons.DrawCircle(portador, 1, Particle.CLOUD, 200);
                    ESModule_Weapons.DrawCircle(portador, 1.2f, Particle.HEART, 200);
                    System.out.println("DEBUG: Etapa 2 cargada");
                    portador.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200 + 3 * actionLevel, 1 + 1 * actionLevel));
                    portador.sendMessage(ChatColor.AQUA + "Tu combo en la armadura fue activado y ahora tienes un BUFF de absorption en ti.");
                    attacker.sendMessage(ChatColor.YELLOW + "El combo de la armadura de" + ChatColor.RED  + portador + ChatColor.YELLOW + "Fue activado y ahora tiene un BUFF de absorption en el.");
                    System.out.println("DEBUG: Etapa 3 cargada");
                });

        API.addArmorAction(
                "Fire",
                (portador, attacker, level, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
//                -> attacker.setFireTicks(level * 40)
                -> attacker.setVelocity(getVector(attacker.getLocation(), portador.getLocation()).multiply(level))
        );

        API.addArmorAction(
                "Empuje",
                (portador, attacker, level, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
                -> attacker.setVelocity(getVector(attacker.getLocation(), portador.getLocation()).multiply(level))
        );

        API.addArmorAction(
                "Veneno",
                (portador, attacker, actionLevel, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
                    -> attacker.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30, actionLevel))
        );

        API.addArmorAction(
                "Damage",
                (portador, attacker, actionLevel, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
                  -> attacker.damage(actionLevel)
            );

        API.addArmorAction(
                "Heal",
                (portador, attacker, actionLevel, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
                    -> portador.setHealth(Math.min(portador.getHealth() + actionLevel, portador.getMaxHealth()))
        );



    }

    private static Vector getVector(Location l1, Location l2) {
        return new Vector(l1.getX() - l2.getX(), l1.getY() - l2.getY(), l1.getZ() - l2.getZ()).normalize();
    }
}
