package Randoms;

import juligame.epicswords2.API;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ESModule_MasEjemplos {
    public static void main(){

        //todo DATO IMPORTANTE:
        //el level de un commbo de ARMADURA funciona de la siguiente manera:
        //se ve si el combo esta en varias piezas de armadura
        //y si es asi se suman.

        //es decir que si tengo Xcombo en 2 armaduras, una lvl 2 y otra 5
        //el level del combo sera 7 (se ejecuta 1 vez, pero con lvl 7)

        API.addArmorAction("Glacial", (player, target, level, normalDamage, finalDamage, event, amountOfPieces) -> {
            // bueno la idea de este efecto es relentizar al target
            // y si el combo es lvl 10 para arriba se pone el piso de hielo
            // y le da speed al player

            // primero vamos a relentizar al target
            // para eso vamos a usar el metodo .addPotionEffect que le agrega un effecto de pocion, como indica el nombre xD
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 + 3 * level, 1));
            // el efecto dura 20 ticks y le agrega 5 por nivel de combo


            // ahora vamos a ver si el combo es lvl 10 para arriba y poner el piso de hielo si es asi
            if(level >= 10){
                // el piso de hielo se pone en el target
                // y dura 20 ticks + 3 por nivel de combo


                //lo primero es guardar las coordenadas de los bloques modificados, y guardar el material
                //para despues poder revertir los cambios (y que no quede de hielo)
                HashMap<Location, Material> bloquesModificados = new HashMap<>();

                //esto es un for, lo vimos antes es una repeticion,
                //en esta caso son 2 for juntos.
                //el primero es para las X
                //el segundo es para las Z
                //es decir que estariamos haciendo un cuadrado de 5x5 alrededor del target

                for (int x = -2; x < 2; x++) {
                    for (int z = -2; z < 2; z++) {
                        //agarramos el bloque en las coordenadas
                        // y lo ponemos de hielo

                        //calculamos las coordenadas
                        Block aModificar = target.getWorld().getHighestBlockAt((int) (target.getLocation().getX() + x), (int) (target.getLocation().getZ() + z));
                        bloquesModificados.put(aModificar.getLocation(), aModificar.getType());
                        //ponemos el bloque de hielo
                        aModificar.setType(Material.ICE);
                    }
                }

                //LISTO, hasta aca se puso el piso de hielo 😎

                //ahora vamos a poner el speed al player
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 + 3 * level, 1));

                //y ahora vamos a revertir los cambios en 20 ticks + 3 por nivel de combo
                //para eso vamos a usar el metodo .runTaskLater
                //que ejecuta una tarea despues de cierto tiempo

                System.out.println(bloquesModificados);
                //primero creamos la tarea
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        //este metodo se ejecuta despues de 20 ticks + 3 por nivel de combo
                        //y lo que hace es revertir los cambios

                        //recorremos la lista de bloques modificados
                        for (Location aModificar : bloquesModificados.keySet()) {
                            //y lo ponemos de nuevo como estaba
                            if (bloquesModificados.get(aModificar) == Material.ICE) continue;
                            aModificar.getBlock().setType(bloquesModificados.get(aModificar));
                        }
                    }
                }.runTaskLater(API.pluginInstance, 20 + 3L * level);
                // y aca arriba con .runTaskLater le decimos que lo ejecute despues de 20 ticks + 3 por nivel de combo

                UPECompatibility.createUPEParticle("RayosDeHielo", target, false, 1.5f, false, false, .5f);
            }
        });
    }
}
