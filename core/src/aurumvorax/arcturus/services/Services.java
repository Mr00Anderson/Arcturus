package aurumvorax.arcturus.services;

import aurumvorax.arcturus.options.IntMapSerializer;
import aurumvorax.arcturus.options.Keys;
import aurumvorax.arcturus.options.PreferenceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;

import java.util.Map;

public enum Services{
    INSTANCE;

    private static Map<String, TextureAtlas.AtlasRegion> regionsByName;
    private static Map<String, Animation<TextureRegion>> animationsByName;

    private static final String ANIMATION_ATLAS_PATH = "img/AnimationAtlas.atlas";
    private static final String SPRITE_ATLAS_PATH = "img/SpriteAtlas.atlas";
    private static final String BACKGROUND_ATLAS_PATH = "img/BackgroundAtlas.atlas";
    private static final String UI_ATLAS_PATH = "img/UIAtlas.atlas";

    private static final String MENU_SKIN_PATH = "skin/sgx/sgx-ui.json";

    public static final String KEY_PATH = "config/keys.cfg";
    public static final String SAVE_PATH = "saves/";

    public static final String EFFECT_PATH = "data/effects/";
    public static final String PROJECTILE_PATH = "data/projectiles/";
    public static final String SHIP_PATH = "data/ships/";
    public static final String TERRAIN_PATH = "data/systems/";
    public static final String WEAPON_PATH = "data/weapons/";

    private static final AssetManager assetManager = new AssetManager();
    public static final SpriteBatch batch = new SpriteBatch();
    public static final Json json = new Json();
    public static final Keys keys = new Keys();
    public static final PreferenceManager prefs = new PreferenceManager();

    
    public static void initJson(){
        Services.json.addClassTag("Command", Keys.Command.class);
        Services.json.addClassTag("Vector2", Vector2.class);
        Services.json.setSerializer(IntMap.class, new IntMapSerializer());
    }

    public static void queueAssets(){
        assetManager.load(SPRITE_ATLAS_PATH, TextureAtlas.class);
        assetManager.load(ANIMATION_ATLAS_PATH, TextureAtlas.class);
        assetManager.load(BACKGROUND_ATLAS_PATH, TextureAtlas.class);
        assetManager.load(UI_ATLAS_PATH, TextureAtlas.class);

        assetManager.load(MENU_SKIN_PATH, Skin.class);
    }

    public static boolean loadAssets(){ return assetManager.update(); }
    public static float loadProgress(){ return assetManager.getProgress(); }

    public static void initAssets(){
        regionsByName = AssetIndexer.loadImages(assetManager.get(SPRITE_ATLAS_PATH),
                                                assetManager.get(BACKGROUND_ATLAS_PATH),
                                                assetManager.get(UI_ATLAS_PATH));

        animationsByName = AssetIndexer.loadAnimations(assetManager.get(ANIMATION_ATLAS_PATH));

        new FontManager();
    }

    public static TextureAtlas.AtlasRegion getTexture(String name){
        TextureAtlas.AtlasRegion tex = regionsByName.get(name);
        if(tex == null){
            Gdx.app.error("Services", "Invalid/Missing texture - " + name);
            return regionsByName.get("NoTexture");
        }else
            return regionsByName.get(name);
    }

    public static Animation getAnimation(String name){ return animationsByName.get(name); }
    public static Skin getSkin(){ return assetManager.get(MENU_SKIN_PATH); }
}
