package net.kyrin.air.hive.file.beeconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyrin.air.hive.bees.Bee;
import net.kyrin.air.lib.utils.file.container.Config;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class BeeConfig extends Config {

    private ArrayList<Bee> bees;

    public void addBee(Bee bee) {
        bees.add(bee);
    }

    public void removeBee(int id) {
        bees.stream().filter(bee -> bee.getId() == id).forEach(bee -> bees.remove(bee));
    }
}
