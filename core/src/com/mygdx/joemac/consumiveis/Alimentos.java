package com.mygdx.joemac.consumiveis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Alimentos extends Consumiveis {
	
	boolean jogadorPegou = false;
	int danos = 0;

	private Texture alimentos;
	private TextureRegion[] framesAlimentos;
	private TextureRegion[][] frames;
	
	/**
	 * Inicializa variaveis e chama as funcoes para a criacao da hitbox e da
	 * separacao de frames
	 **/
	public void criarConsumiveis(int x, int y) {
		framesAlimentos = new TextureRegion[48]; // Vai receber os frames ja divididos
		alimentos = new Texture(Gdx.files.internal("desktop/Imagens/comidas.png")); // Pega a imagem

		frames = TextureRegion.split(alimentos, alimentos.getWidth() / 8, alimentos.getHeight() / 6);

		separaFrames();
		criarHitBox(alimentos.getHeight() / 6, alimentos.getWidth() / 8, x, y ); // cria a hitbox
	}

	/**
	 * Desenha os alimentos
	 **/
	public void desenharConsumiveis(SpriteBatch batch) {
		batch.begin();
		batch.draw(framesAlimentos[33], pegarHitBox().x, pegarHitBox().y, 100, 100);
		batch.end();
	}

	/**
	 * Controla se o jogador pegou o consumivel e a restauracao do hp
	 **/
	public void controlarConsumiveis(Rectangle jogador) {

		if (pegarHitBox().overlaps(jogador)) {
			danos -= 2;
			jogadorPegou = true;
		}

	}
	
	/**
	 * Separa os frames da pedra
	 **/
	private void separaFrames() {
		int index = 0;

		for (int linha = 0; linha < 6; linha++) {
			for (int coluna = 0; coluna < 8; coluna++) {
				framesAlimentos[index++] = frames[linha][coluna];
			}
		}
	}

	/**
	 * Overrides
	 **/
	@Override
	public boolean getJogadorPegou() {
		return jogadorPegou;
	}

	@Override
	public int recebeAtributos() {
		return danos;
	}
	
	/**
	 * Dispose
	 **/
	public void dispose() {
		alimentos.dispose();

	}
}