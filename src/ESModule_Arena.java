import de.myzelyam.api.vanish.VanishAPI;
import io.lumine.mythic.api.MythicPlugin;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.adapters.AbstractPlayer;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.utils.serialize.Position;
import io.lumine.mythic.core.mobs.ActiveMob;
import juligame.epicswords2.API;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ESModule_Arena {
    public static void main(){
        API.addArmorAction(
            "Anubis",
            (portador, attacker, actionLevel, damage, finalDamage, entityDamageByEntityEvent, armorPieces)
                    -> {
                MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("AnubisPet").orElse(null);

                assert mob != null;
                ActiveMob entity = mob.spawn(new AbstractLocation(Position.of(portador.getLocation())), 10);
                entity.setTarget(BukkitAdapter.adapt(attacker));
                System.out.println(entity.getNewTarget().getName());

                entity.setOwner(portador.getUniqueId());


                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (entity.isDead()) {
                            this.cancel();
                        }
                        if (attacker.isDead()){
                            entity.despawn();
                        }
                    }
                }.runTaskTimer(API.pluginInstance, 0, 20);
            }
        );



    }

}
