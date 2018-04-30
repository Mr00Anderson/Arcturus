package aurumvorax.arcturus.artemis.factories;

import aurumvorax.arcturus.artemis.components.*;
import aurumvorax.arcturus.artemis.components.shipComponents.Mount;
import aurumvorax.arcturus.artemis.systems.render.Renderer;
import com.artemis.*;

public class WeaponFactory{

    private static final WeaponFactory INSTANCE = new WeaponFactory();
    private static World world;
    private static Archetype protoCannon;
    private static Archetype protoBeam;

    private static ComponentMapper<Mounted> mMounted;
    private static ComponentMapper<SimpleSprite> mSprite;
    private static ComponentMapper<Turret> mTurret;
    private static ComponentMapper<Cannon> mCannon;
    private static ComponentMapper<Beam> mBeam;


    public static void init(World world){
        WeaponFactory.world = world;
        world.inject(INSTANCE);

        Archetype protoWeapon = new ArchetypeBuilder()
                .add(Mounted.class)
                .add(SimpleSprite.class)
                .add(Turret.class)
                .build(world);
        protoCannon = new ArchetypeBuilder(protoWeapon)
                .add(Cannon.class)
                .build(world);
        protoBeam = new ArchetypeBuilder(protoWeapon)
                .add(Beam.class)
                .build(world);
    }

    static int create(String name, int ship, Mount.Weapon mount, int slot){
        WeaponData data = EntityData.getWeaponData(name);

        switch(data.type){
            case CANNON:
                int cannon = world.create(protoCannon);
                buildTurret(cannon, data, ship, mount);
                Cannon c = mCannon.get(cannon);
                c.name = name;
                c.slot = slot;
                c.launches = data.launches;
                c.burstTime = data.delay;
                c.reloadTime = data.reload;
                c.barrels = data.barrels;
                ProjectileFactory.setWeaponData(c, data.launches);
                return cannon;

            case BEAM:
                int beam = world.create(protoBeam);
                buildTurret(beam, data, ship, mount);
                Beam b = mBeam.get(beam);
                b.name = name;
                b.slot = slot;
                b.imgName = data.beamImgName;
                b.offsetY = data.beamImgCenter.y;
                b.maxRange = data.range;
                b.barrels = data.barrels;
                b.dps = data.dps;
                return beam;

            case LAUNCHER:
                //return launcher;

            default:
                throw new IllegalArgumentException(data.type + " is not a known type");
        }
    }

    private static void buildTurret(int entityID, WeaponData data, int ship, Mount.Weapon mount){
        Mounted m = mMounted.get(entityID);
        m.parent = ship;
        m.location = mount.location;
        m.theta = mount.angle;

        SimpleSprite s = mSprite.get(entityID);
        s.name = data.imgName;
        s.offsetX = data.imgCenter.x;
        s.offsetY = data.imgCenter.y;
        s.layer = Renderer.Layer.DETAIL;

        Turret t = mTurret.get(entityID);
        t.omegaMax = data.rotationSpeed;
        t.setArcs(mount.angle, mount.arc);
    }
}
