import io.github.hubertupe.ultimateparticleeffects.ParticleEffectCreator;
import juligame.epicswords2.API;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

public class UPECompatibility {
    public static ParticleEffectCreator createUPEParticle(String name, LivingEntity target, boolean collide, float speed) {
        ParticleEffectCreator pec = new ParticleEffectCreator(null, null, collide);
        if(pec.collide) Bukkit.getPluginManager().registerEvents(pec, API.pluginInstance);
        pec.CreateParticleEffect(name, target, true, false, Particle.REDSTONE, false, false, 1, speed);
        return pec;
    }
    public static ParticleEffectCreator createUPEParticle(String name, LivingEntity target, boolean collide, float speed,  boolean follow,  boolean rotate, float scale) {
        ParticleEffectCreator pec = new ParticleEffectCreator(null, null, collide);
        if(pec.collide) Bukkit.getPluginManager().registerEvents(pec, API.pluginInstance);
        pec.CreateParticleEffect(name, target, follow, rotate, Particle.REDSTONE, false, false, scale, speed);
        return pec;
    }
    private static HashMap<String, ParticleEffectCreator> list = new HashMap<>();
    public static ParticleEffectCreator createUPEParticle(String name, LivingEntity target, boolean collide, float speed,  boolean follow,  boolean rotate, float scale, boolean loop) {
        ParticleEffectCreator pec = new ParticleEffectCreator(null, null, collide);
        if(pec.collide) Bukkit.getPluginManager().registerEvents(pec, API.pluginInstance);
        pec.CreateParticleEffect(name, target, follow, rotate, Particle.REDSTONE, loop, false, scale, speed);
        return pec;
    }
    public static ParticleEffectCreator createUPEParticle(String name, LivingEntity target, boolean collide, float speed,  boolean follow,  boolean rotate, float scale, boolean loop, Particle particle) {
        ParticleEffectCreator pec = new ParticleEffectCreator(null, null, collide);
        if(pec.collide) Bukkit.getPluginManager().registerEvents(pec, API.pluginInstance);
        pec.CreateParticleEffect(name, target, follow, rotate, particle, loop, false, scale, speed);
        return pec;
    }
}
