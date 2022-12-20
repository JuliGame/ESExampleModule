import juligame.epicswords2.API;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import util.lineaCurva;

import java.util.ArrayList;

public class ESModule_Sangre {
    public static void main(){
        //se cura 10% por daño x level
        API.addComboAction("Robo De Vida",
            (portador, attacker, actionLevel, event) -> {
                double finalDamage = event.getFinalDamage();
                System.out.println("finalDamage: " + finalDamage);
                double curacion = finalDamage * 0.1f * actionLevel;
                System.out.println("curacion: " + curacion);
                // ronund to 2 decimals
                double vida = Math.round((portador.getHealth() + curacion) * 100.0) / 100.0;
                System.out.println("vida: " + vida);
                if (vida > portador.getMaxHealth()) {
                    vida = portador.getMaxHealth();
                }
                World world = portador.getWorld();

                world.playSound(portador.getLocation(), "item.honey_bottle.drink", 1, 1);


                int steps = 10;

                ArrayList<lineaCurva> ll = new ArrayList<>();

                for (int i = 0; i < actionLevel; i++) {
                    ll.add(new lineaCurva(attacker.getLocation().add(
                            Math.random() - .5f*2,
                            Math.random() - .5f*2 + 1,
                            Math.random() - .5f*2
                    ), 10));
                }

                double finalVida = vida;
                new BukkitRunnable(){
                    int it = 0;
                    @Override
                    public void run() {
                        if (it >= steps){
                            if (!portador.isDead()) portador.setHealth(finalVida);
                            this.cancel();
                            return;
                        }


                        ll.forEach(l -> {
                            world.spawnParticle(Particle.REDSTONE, l.getNext(portador), 0, 0, 0, 0, 0, new Particle.DustOptions(Color.fromBGR(0, 0, 175), 1));
                        });

                        it++;
                    }
                }.runTaskTimer(API.pluginInstance, 0, 1);

            });

    }
}
