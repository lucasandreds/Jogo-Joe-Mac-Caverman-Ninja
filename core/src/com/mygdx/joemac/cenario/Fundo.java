package com.mygdx.joemac.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fundo extends Cenario {
	private final float LARGURA_TELA = 3060;
	private final float ALTURA_TELA = 720;

	private Sprite sprite;
	private Texture background,menina;
	private TextureRegion[][] frames;
	private TextureRegion [] framesMenina;

	@Override
	/** Responsavel por criar o cenario de fundo, como a fase inicial e a menina**/
	public void criaCenario() {
		framesMenina = new TextureRegion[9]; // Vai receber os frames ja divididos
		background = new Texture(Gdx.files.internal("Imagens/firstLevel.png"));
		sprite = new Sprite(background);
		sprite.setPosition(0, 0);
		sprite.setSize(LARGURA_TELA, ALTURA_TELA);
		
		menina = new Texture(Gdx.files.internal("Imagens/Menina.png"));
		
		frames = TextureRegion.split(menina, menina.getWidth() / 9, menina.getHeight() / 1);
		separaFrames();
		
	}

	@Override
	/** Responsavel por desenhar o cenario de fundo**/
	public void desenhaCenario(SpriteBatch batch) {
		batch.begin();
		sprite.draw(batch);
		batch.draw(framesMenina[0],2900,40,200,400);
		batch.end();
	}
	
	/** Responsavel por separar os frames da menina **/
	private void separaFrames() {
		int index = 0;

		for (int linha = 0; linha < 1; linha++) {
			for (int coluna = 0; coluna < 9; coluna++) {
				framesMenina[index++] = frames[linha][coluna];
			}
		}
	}
}