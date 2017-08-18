package aurumvorax.arcturus.artemis;

import aurumvorax.arcturus.Services;
import aurumvorax.arcturus.artemis.components.*;
import com.artemis.ComponentMapper;
import com.artemis.World;

public enum EntityFactory{
    INSTANCE;

    private static World world;
    private static ComponentMapper<Position> mPosition;
    private static ComponentMapper<Velocity> mVelocity;
    private static ComponentMapper<SimpleSprite> mSprite;
    private static ComponentMapper<CollisionSimple> mCollidable;
    private static ComponentMapper<Inertia>  mInertia;

    public static void init(World world){
        EntityFactory.world = world;
        mPosition = world.getMapper(Position.class);
        mVelocity = world.getMapper(Velocity.class);
        mSprite = world.getMapper(SimpleSprite.class);
        mCollidable = world.getMapper(CollisionSimple.class);
        mInertia = world.getMapper(Inertia.class);
    }

    public static int createShip(float x, float y, float theta){
        int ship = world.create();
        mPosition.create(ship).p.set(x, y);
        mPosition.get(ship).theta = theta;
        mVelocity.create(ship);
        mSprite.create(ship).name = Services.SHIP_IMG_PATH + "TestShip";
        mSprite.get(ship).offsetX = 64;
        mSprite.get(ship).offsetY = 32;
        mCollidable.create(ship).radius = 70;
        mInertia.create(ship);





        return ship;
    }
}
