import juligame.epicswords2.API;
import org.bukkit.Location;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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


    }
}
