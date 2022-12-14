import juligame.epicswords2.API;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class ESModule_Armors {
    public static void main(){
        API.addArmorAction("La Toxi",
                (portador, attacker, actionLevel, damage, finalDamage, event) -> {

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
                (portador, attacker, actionLevel, damage, finalDamage, event) -> {
                    System.out.println("DEBUG: Etapa 1 cargada");
                    ESModule_Weapons.DrawCircle(portador, 1, Particle.CLOUD, 200);
                    ESModule_Weapons.DrawCircle(portador, 1.2f, Particle.HEART, 200);
                    System.out.println("DEBUG: Etapa 2 cargada");
                    portador.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200 + 3 * actionLevel, 1 + 1 * actionLevel));
                    portador.sendMessage(ChatColor.AQUA + "Tu combo en la armadura fue activado y ahora tienes un BUFF de absorption en ti.");
                    attacker.sendMessage(ChatColor.YELLOW + "El combo de la armadura de" + ChatColor.RED  + portador + ChatColor.YELLOW + "Fue activado y ahora tiene un BUFF de absorption en el.");
                    System.out.println("DEBUG: Etapa 3 cargada");
                }); 


    }
}
