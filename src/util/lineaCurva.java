package util;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class lineaCurva {

    Location origen;
    int cantidadDePuntos;
    int it;
    lineaCurva(Location origen, int steps){
        this.origen = origen;
        this.cantidadDePuntos = steps;
        this.it = 0;
    }
    public Location getNext(LivingEntity loc2) {
        //creates a curved line between two locations
        ArrayList<Location> locs = new ArrayList<>();
        double x1 = origen.getX();
        double y1 = origen.getY();
        double z1 = origen.getZ();
        double x2 = loc2.getLocation().getX();
        double y2 = loc2.getLocation().getY()+1f;
        double z2 = loc2.getLocation().getZ();
        double x = x1;
        double y = y1;
        double z = z1;
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double d2 = d / cantidadDePuntos;
        double d3 = d2 / d;
        double dx2 = dx * d3;
        double dy2 = dy * d3;
        double dz2 = dz * d3;

        x += dx2 * it;
        y += dy2 * it;
        z += dz2 * it;
        it++;
        return new Location(origen.getWorld(), x, y, z);

    }
}
