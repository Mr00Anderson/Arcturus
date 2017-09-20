package aurumvorax.arcturus.savegame;

import com.badlogic.gdx.utils.Array;

public class SaveSubject {

    private Array<SaveObserver> observers = new Array<SaveObserver>();

    public void addObserver(SaveObserver observer){ observers.add(observer); }
    public void removeObserver(SaveObserver observer){ observers.removeValue(observer, true); }
    public void removeAllObservers(){ observers.removeAll(observers, true); }

    protected void notify(final SaveSubject saveManager, SaveObserver.SaveEvent event){
        for(SaveObserver observer: observers){
            observer.onNotify(saveManager, event);
        }
    }
}