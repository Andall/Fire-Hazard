package firehazard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class core extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont font;
    private ParticleEffect effect;
    Music dub1;
    Music dub2;
    boolean dub = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        /* Creating a particle */
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/explode.particle"), Gdx.files.internal("effects"));
        dub1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/dub1.ogg"));
        dub2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/dub2.ogg"));
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        /* Dubstep gun prototype/mock up */
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (!dub1.isPlaying() && !dub2.isPlaying() && dub) {
                dub2.play();
                for (int i = 0; i < effect.getEmitters().size; i++) {
                    effect.getEmitters().get(i).getVelocity().setHigh(1000);
                    /*
                    effect.getEmitters().get(i).getAngle().setHigh(0); // Directional dubstep effects vs radial
                    effect.getEmitters().get(i).getAngle().setLow(0);
                    */
                }
            } else if (!dub1.isPlaying() && !dub2.isPlaying()) {
                dub1.play();
                dub = true;
                for (int i = 0; i < effect.getEmitters().size; i++) {
                    effect.getEmitters().get(i).getVelocity().setHigh(100);
                    /*
                    effect.getEmitters().get(i).getAngle().setHigh(0); // Directional dubstep effects vs radial
                    effect.getEmitters().get(i).getAngle().setLow(0);
                    */
                }
            }
            effect.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            effect.setDuration(0);
            effect.start();
        } else {
            dub1.stop();
            dub2.stop();
            dub = false;
        }

        /* Drawing the FPS counter */
        effect.draw(batch, Gdx.graphics.getRawDeltaTime()); // Draw the particle
        effect.update(Gdx.graphics.getRawDeltaTime()); // Update the particle
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        batch.end();
    }
}
