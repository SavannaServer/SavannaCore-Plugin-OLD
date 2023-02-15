package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.gun.event.BulletMoveEvent;

public final class BulletParticleListener implements Listener {
    @EventHandler
    public void onBulletMove(BulletMoveEvent event) {
        drawLine(event.getBullet().getShooter(), event.getBullet().getParticle().getParticle(), event.getFrom(), event.getTo(), 0.5, event.getBullet().getParticle().getExtra());
    }

    private void drawLine(Player player, Particle particle, Location point1, Location point2, double space, int extra) {
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            player.spawnParticle(particle, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, extra);
            length += space;
        }
    }
}
