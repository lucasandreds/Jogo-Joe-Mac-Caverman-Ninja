package com.mygdx.joemac.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FundoMenu extends Cenario {
	private final float LARGURA_TELA = 1280;
	private final float ALTURA_TELA = 720;

	private Sprite sprite;
	private Texture background;
	
	@Override
	/** Responsavel por criar o fundo que aparece na tela de menu **/
	public void criaCenario() {
		background = new Texture(Gdx.files.internal("Imagens/menu.png"));
		sprite = new Sprite(background);
		sprite.setPosition(0,0);
		sprite.setSize(LARGURA_TELA,ALTURA_TELA);
	}

	@Override
	/** Responsavel por desenhar esse fundo **/
	public void desenhaCenario(SpriteBatch batch) {
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}
}