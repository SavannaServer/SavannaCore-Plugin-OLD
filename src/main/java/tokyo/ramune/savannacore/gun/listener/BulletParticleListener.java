package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.gun.event.BulletMoveEvent;

public final class BulletParticleListener implements Listener {
    @EventHandler
    public void onBulletMove(BulletMoveEvent event) {
        drawLine(event.getFrom(), event.getTo(), 1);
    }

    private void drawLine(Location point1, Location point2, double space) {
        World world = point1.getWorld();
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            world.spawnParticle(Particle.CRIT, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, 0);
            length += space;
        }
    }
}
