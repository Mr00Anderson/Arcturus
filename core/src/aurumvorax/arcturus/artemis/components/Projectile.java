package aurumvorax.arcturus.artemis.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

public class Projectile extends Component{

    @EntityId public int firedFrom;
    public float damage;
}
