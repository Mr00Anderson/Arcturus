package aurumvorax.arcturus.artemis.systems.collision;

import aurumvorax.arcturus.artemis.components.CollisionRadius;
import aurumvorax.arcturus.artemis.components.Physics2D;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

class TestCC{

    private static Vector2 contact = new Vector2();

    private static ComponentMapper<Physics2D> mPosition;
    private static ComponentMapper<CollisionRadius> mCollidable;


    static void test(int entityA, int entityB, Collision.Manifold m){
        int radiusA = mCollidable.get(entityA).radius;
        float radii = radiusA + mCollidable.get(entityB).radius;
        Vector2 positionA = mPosition.get(entityA).p;
        Vector2 positionB = mPosition.get(entityB).p;

        m.penetration[0] = radii - positionA.dst(positionB);
        if(m.penetration[0] < 0)
            return;
        m.normal.set(positionB).sub(positionA).nor();
        contact.set(m.normal).scl(radiusA + (m.penetration[0] * 0.5f)).add(positionA);
        m.contactPoints.add(contact);
        m.contacts = 1;
    }

}
