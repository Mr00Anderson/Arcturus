package aurumvorax.arcturus.artemis.systems;

import aurumvorax.arcturus.artemis.ProjectileFactory;
import aurumvorax.arcturus.artemis.components.Cannon;
import aurumvorax.arcturus.artemis.components.Mounted;
import aurumvorax.arcturus.artemis.components.Physics2D;
import aurumvorax.arcturus.artemis.components.Turret;
import aurumvorax.arcturus.artemis.components.shipComponents.Weapons;
import aurumvorax.arcturus.Utils;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class WeaponsUpdate extends IteratingSystem{


    private static transient Vector2 targetVector = new Vector2();
    private static transient Vector2 firePoint = new Vector2();

    private static ComponentMapper<Weapons> mWeapons;
    private static ComponentMapper<Mounted> mMounted;
    private static ComponentMapper<Turret> mTurret;
    private static ComponentMapper<Physics2D> mPhysics;
    private static ComponentMapper<Cannon> mCannon;


    public WeaponsUpdate(){
        super(Aspect.all(Weapons.class));
    }

    @Override
    protected void process(int entityID){
        Weapons w = mWeapons.get(entityID);
        for(int weapon : w.active.toArray())
            updateActive(weapon, w.target, w.fire);
        for(int weapon : w.all.toArray())
            updateWeapon(weapon);
    }

    private void updateActive(int weapon, Vector2 target, boolean fire){
        Turret t = mTurret.get(weapon);
        Mounted m = mMounted.get(weapon);

        if(target == null)
            t.target = t.arcMid;
        else{
            targetVector.set(target).sub(m.position);
            t.target = targetVector.angle();
        }
        if(mCannon.has(weapon))
            updateCannon(weapon, fire);
    }

    private void updateWeapon(int weapon){
        Mounted m = mMounted.get(weapon);
        Turret t = mTurret.get(weapon);
        Physics2D parent = mPhysics.get(m.parent);

        m.position.set(m.location).rotate(parent.theta).add(parent.p);
        float zeroAngle = parent.theta + t.arcMid;
        float targetAngle = Utils.normalize(t.target - zeroAngle);
        float thetaRelative = Utils.normalize(m.theta - zeroAngle);
        float omega = MathUtils.clamp((targetAngle - thetaRelative) * 10, -t.omegaMax, t.omegaMax);
        thetaRelative += omega * world.delta;
        thetaRelative = MathUtils.clamp(thetaRelative, t.arcMin, t.arcMax);
        m.theta = thetaRelative + zeroAngle;
    }

    private void updateCannon(int weapon, boolean fire){
        Cannon cannon = mCannon.get(weapon);
        cannon.timer -= world.delta;
        if(fire && cannon.timer <= 0){
            fire(weapon, cannon);
            if(cannon.barrel == cannon.barrels.size - 1){  //last barrel in sequence
                cannon.timer = cannon.reloadTime;
                cannon.barrel = 0;
            }else{
                cannon.timer = cannon.burstTime;
                cannon.barrel++;
            }
        }
    }

    private void fire(int weapon, Cannon cannon){
        Mounted m = mMounted.get(weapon);
        firePoint.set(cannon.barrels.get(cannon.barrel)).rotate(m.theta).add(m.position);
        if(cannon.launches != null)
            ProjectileFactory.create(cannon.launches, firePoint.x, firePoint.y, m.theta, m.parent);
    }
}