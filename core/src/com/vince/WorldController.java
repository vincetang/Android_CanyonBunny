package com.vince;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Vince on 16-07-04.
 */
public class WorldController {
    private static final String TAG = WorldController.class.getName();

    public Sprite[] sprites;
    public int selectedSprite;

    public WorldController() {
        init();
    }

    public void init() {
        initSprites();
    }

    private void initSprites() {
        // Create new array for 5 sprites
        sprites = new Sprite[5];

        // Create empty POT-sized pixmap with 8 bit RGBA pixel data
        int width = 32;
        int height = 32;
        Pixmap pixmap = createProceduralPixmap(width, height);

        // Create a new texture from pixmap data
        Texture texture = new Texture(pixmap);

        // Create new sprites using the just created texture
        for (int i = 0; i < sprites.length; i++) {
            Sprite spr = new Sprite(texture);

            // Define sprite size to be 1m x 1m in the game world
            spr.setSize(1, 1);

            // Set origin to sprite's center
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);

            // Calculate random position for sprite
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);

            spr.setPosition(randomX, randomY);

            // put new Sprite into array
            sprites[i] = spr;
        }
        // Set first sprite as selected one
        selectedSprite = 0;
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        // Fill square with red color at 50% opacity
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();

        // Draw a yellow-colored X shape on the square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);

        // Draw a cyan-colored border around square
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    public void update (float deltaTime) {
        handleDebugInput(deltaTime);
        updateSprites(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        // Selected Sprite Controls
        float sprMoveSpeed = 5 * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            moveSelectedSprite(-sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            moveSelectedSprite(sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            moveSelectedSprite(0, sprMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            moveSelectedSprite(0, -sprMoveSpeed);
    }

    private void moveSelectedSprite(float x, float y) {
        sprites[selectedSprite].translate(x, y);
    }
    private void updateSprites(float deltaTime) {
        // Get currnet rotation from selected sprite
        float rotation = sprites[selectedSprite].getRotation();
        // rotate sprite by 90 degrees per second
        rotation += 90 * deltaTime;
        // Wrap around at 360 degrees
        rotation %= 360;
        // set new rotation value to selected sprite
        sprites[selectedSprite].setRotation(rotation);

    }

}
