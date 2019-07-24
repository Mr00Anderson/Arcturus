package aurumvorax.arcturus.artemis.systems.ai.utility_tree.inputs;

import aurumvorax.arcturus.artemis.components.Health;
import aurumvorax.arcturus.artemis.components.AIData;
import aurumvorax.arcturus.artemis.systems.ai.utility_tree.Input;
import com.artemis.ComponentMapper;

public class MyHealth extends Input{

    private ComponentMapper<Health> mHealth;


    @Override
    protected float getData(AIData bb){
        Health h = mHealth.get(bb.selfID);
        return h.hull / h.maxHull;
    }
}