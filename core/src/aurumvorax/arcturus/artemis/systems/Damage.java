package aurumvorax.arcturus.artemis.systems;

import aurumvorax.arcturus.artemis.Destructor;
import aurumvorax.arcturus.artemis.factories.EffectFactory;
import aurumvorax.arcturus.artemis.components.Health;
import aurumvorax.arcturus.artemis.components.Physics2D;
import aurumvorax.arcturus.screens.MenuScreen;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Damage extends BaseSystem{

    private static ComponentMapper<Health> mHealth;
    private static ComponentMapper<Physics2D> mPhysics;

    public static void hull(int entityID, Vector2 location, float damage){
        if(mHealth.has(entityID)){
            Health h = mHealth.get(entityID);
            h.hull -= damage;
            if(h.hull <= 0){
                EffectFactory.createExplosion("ShipExplosion1", mPhysics.get(entityID));

                if(entityID == PlayerShip.getID())
                    TransitionManager.setTransition(MenuScreen.MenuType.Dead);

                Destructor.safeRemove(entityID);
            }
        }
    }

    @Override
    protected void processSystem(){}
}
