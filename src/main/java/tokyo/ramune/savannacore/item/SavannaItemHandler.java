package tokyo.ramune.savannacore.item;

import java.util.HashSet;
import java.util.Set;

public final class SavannaItemHandler {
    private final Set<SavannaItem> registeredItem = new HashSet<>();

    public SavannaItemHandler() {
    }

    public void register(SavannaItem savannaItem) {
        registeredItem.add(savannaItem);
    }

    public void unregisterAll() {
        registeredItem.clear();
    }

    public Set<SavannaItem> getRegistered() {
        return registeredItem;
    }
}
