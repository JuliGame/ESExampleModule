import io.github.hubertupe.ultimateparticleeffects.ParticleEffectCreator;
import juligame.epicswords2.API;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import util.UPECompatibility;

public class ESModules_Veneno {
    public static void main() {


        API.addComboAction("Veneno", (player, target, level, event) -> {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30+5*level, level));
            float duration = 1.5f+.5f*level; //segundos
            float damage = 1+.5f*level;



            float scale = 0.5f;
            if (level == 2) scale = .6f;
            if (level == 3) scale = .7f;
            if (level == 4) scale = .8f;
            if (level >= 5) scale = 1f;

            final ParticleEffectCreator pec =
                    UPECompatibility.createUPEParticle("Veneno", target, false, 1, true, false, scale, true);

            new BukkitRunnable() {
                    float time = 0;
                    @Override
                    public void run() {
                        target.damage(damage);
                        time += .5f;
                        if (target.isDead() || time >= duration) {
                            this.cancel();
                            pec.removeParticleEffect();
                        }
                    }
                }.runTaskTimer(API.pluginInstance, 0, 10);
        });


    }
}
