import io.github.hubertupe.ultimateparticleeffects.ParticleEffectCreator;
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

public class ESModules_Veneno {
    public static void main() {


        API.addComboAction("Veneno", (player, target, level, event) -> {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30+5*level, level));
            float duration = 1.5f+.5f*level; //segundos
            float damage = 1+.5f*level;

            boolean doParticles = level >= 2;

            float scale = 1f;
            if (level == 2) scale = .3f;
            if (level == 3) scale = .5f;
            if (level == 4) scale = .7f;
            if (level >= 5) scale = 1f;

            final ParticleEffectCreator pec = doParticles ?
                    UPECompatibility.createUPEParticle("Veneno", target, false, 1, true, false, scale, true) : null;

            new BukkitRunnable() {
                    float time = 0;
                    @Override
                    public void run() {
                        target.damage(damage);
                        time += .5f;
                        if (target.isDead() || time >= duration) {
                            this.cancel();
                            if (doParticles) pec.removeParticleEffect();
                        }
                    }
                }.runTaskTimer(API.pluginInstance, 0, 10);
        });


    }
}
