package com.mygdx.joemac.atributosJogador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Vida extends Atributos {
	private final int VARICAO_POSICAO = 10, VARIACAO_FONTE_Y = 30;
	
	private Texture vida;
	private int x, y;
	private BitmapFont font;
	private int quantidadeVidas = 1;
	@Override
	/** Responsavel por criar a vida e seu desenho **/
	public void criaAtributo() {
		vida = new Texture("desktop/Imagens/Vidas.png");
		font = new BitmapFont();
		font.getData().setScale(3,2); 
		
		x = getXEstipulado() + VARICAO_POSICAO;
		y = VARICAO_POSICAO;		
	}

	@Override
	/** Responsavel por desenhar na tela a quantidade de vidas que o jogador possui **/
	public void desenhaAtributo(SpriteBatch batch,int cameraX, int POSICAO_INICIAL_CAMERA) {
		batch.begin();
		String vidas = String.valueOf(quantidadeVidas);
		batch.draw(vida,x + (cameraX - POSICAO_INICIAL_CAMERA),y,vida.getWidth(), vida.getHeight());
		font.draw(batch, vidas + "x", (x + vida.getWidth() + VARICAO_POSICAO + (cameraX - POSICAO_INICIAL_CAMERA)), (y + VARIACAO_FONTE_Y)) ;
		batch.end();
	}

	/** Responsavel por receber os atributos da perda de vida **/
	@Override
	public void recebeAtributos(int atributos) {
		quantidadeVidas -= atributos;
		if(quantidadeVidas < 0) {
			setPerdeuVidas(true);
		}
	}
	
	/**
	 * Dispose 
	 **/
	public void dispose() {
		vida.dispose();

	}

}