package aurumvorax.arcturus.screens;

import aurumvorax.arcturus.Core;
import aurumvorax.arcturus.artemis.factories.EntityData;
import aurumvorax.arcturus.Services;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class SplashScreen extends ScreenAdapter{

    private Core core;

    public SplashScreen(Core core){
        this.core = core;
        Services.queueTextureAssets();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Services.loadAssets()){
            finalLoad();
        }
    }

    private void finalLoad(){
        Services.initJson();
        Services.initAssets();
        EntityData.load();
        core.initialize();
        core.switchScreen(Core.ScreenType.Game);
    }


}
