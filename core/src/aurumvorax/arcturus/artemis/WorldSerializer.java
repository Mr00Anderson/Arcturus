package aurumvorax.arcturus.artemis;

import aurumvorax.arcturus.savegame.ArrayKryoSerializer;
import aurumvorax.arcturus.savegame.SaveManager;
import aurumvorax.arcturus.savegame.SaveObserver;
import aurumvorax.arcturus.screens.GameScreen;
import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.io.KryoArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.utils.IntBag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class WorldSerializer implements SaveObserver{

    private KryoArtemisSerializer backend;
    private World world;

    public WorldSerializer(World world){
        this.world = world;
        backend = new KryoArtemisSerializer(world);
        ArrayKryoSerializer.registerArraySerializer(backend.getKryo());
    }

    @Override
    public void onNotify(SaveManager saveManager, SaveEvent saveEvent){
        switch(saveEvent){
            case SAVING:
                IntBag entityIDs = world.getAspectSubscriptionManager()
                    .get(Aspect.all())
                    .getEntities();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                backend.save(os, new SaveFileFormat(entityIDs));
                saveManager.saveElement("Artemis", os.toByteArray());
                break;

            case LOADING:
                GameScreen.resetWorld();
                ByteArrayInputStream is = new ByteArrayInputStream(saveManager.loadElement("Artemis", byte[].class));
                backend.load(is, SaveFileFormat.class);
                break;
        }
    }
}
