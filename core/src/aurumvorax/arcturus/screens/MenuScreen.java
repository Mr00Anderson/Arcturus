package aurumvorax.arcturus.screens;

import aurumvorax.arcturus.Core;
import aurumvorax.arcturus.menus.MenuFramework;
import aurumvorax.arcturus.services.Services;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen extends ScreenAdapter{

    private Core core;
    private Stage stage;
    private Table rootTable;
    private TextureRegion background = Services.getTexture("MainMenuBackground");

    public MenuScreen(Core core){
        this.core = core;
        this.stage = new Stage(new ScreenViewport(), Services.batch);

        rootTable = new Table();
        rootTable.setFillParent(true);

        rootTable.setBackground(new TextureRegionDrawable(background));
        stage.addActor(rootTable);
    }


    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);

        MenuFramework frame = MenuFramework.INSTANCE;

        if(core.getGameMode() == Core.GameMode.Docked)
            frame.openToPage(MenuFramework.Page.Shipyard);
        else
            frame.openToPage(MenuFramework.Page.Start);

        stage.clear();
        stage.addActor(frame);
        frame.setupFrame(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Services.batch.setColor(1,1,1,1);

        if(background != null){
           // Services.batch.begin();
          //  Services.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
           // Services.batch.end();
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}
