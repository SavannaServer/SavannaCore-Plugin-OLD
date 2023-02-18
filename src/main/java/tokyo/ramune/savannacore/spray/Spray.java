package tokyo.ramune.savannacore.spray;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Spray {
    public Spray() {
    }

    public void foo () {
        ItemStack map = new ItemStack(Material.FILLED_MAP);

        MapMeta mapMeta = (MapMeta) map.getItemMeta();
        MapView mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
        mapView.setTrackingPosition(false);
        mapView.setScale(MapView.Scale.FARTHEST);
        for (MapRenderer renderer : mapView.getRenderers()) {
            mapView.removeRenderer(renderer);
        }
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
                try {
                    mapCanvas.drawImage(0, 0, ImageIO.read(new File("./png.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        mapMeta.setMapView(mapView);
        map.setItemMeta(mapMeta);
        Bukkit.getPlayer("ramuremo_chan").getInventory().addItem(map);
    }
}
