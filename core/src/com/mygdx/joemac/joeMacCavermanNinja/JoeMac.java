package com.mygdx.joemac.joeMacCavermanNinja;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JoeMac extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
	@Override
	/** Responsavel por criar as telas que v�o aparecer no jogo**/
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3,3);
		
		this.setScreen(new Menu(this));
	}
	
	
	/** Responsavel por renderizar o game**/
    public void render() {
        super.render();
    }
    
    /**  
     * DISPOSE
     **/
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    /**
     * GETTERS AND SETTERS
     */

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}
    
    

}