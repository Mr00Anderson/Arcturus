package aurumvorax.arcturus.artemis.systems.ai.pilot;

import aurumvorax.arcturus.artemis.components.Physics2D;
import aurumvorax.arcturus.artemis.components.PoweredMotion;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Stop{

    private static Vector2 stop = new Vector2();

    private static ComponentMapper<Physics2D> mPhysics;
    private static ComponentMapper<PoweredMotion> mPowered;


    public static Vector2 calc(int owner){
        Physics2D physics = mPhysics.get(owner);
        if(!physics.v.isZero())
            stop.set(physics.v).scl(-1).setLength(mPowered.get(owner).thrust);
        return stop;
    }
}
