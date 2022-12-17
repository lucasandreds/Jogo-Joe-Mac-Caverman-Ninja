package com.mygdx.joemac.atributosJogador;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pontuacao extends Atributos {
	private final int VARICAO_POSICAO_Y = 90, VARICAO_POSICAO_X = 135;
	
	private int x, y;
	private BitmapFont font;
	private int pontos = 0;
	
	@Override
	/** Responsavel por criar a Pontuacao **/
	public void criaAtributo() {
		font = new BitmapFont();
		font.getData().setScale(3,3); 
		
		x = getXEstipulado() + VARICAO_POSICAO_X;
		y = getYEstipulado() + VARICAO_POSICAO_Y;
	}

	@Override
	/** Responsavel por desenhar a pontuacao na tela **/
	public void desenhaAtributo(SpriteBatch batch, int cameraX, int POSICAO_INICIAL_CAMERA) {
		batch.begin();
		String pontos = String.format("%06d", this.pontos);
		font.draw(batch, "1 PL    " + pontos, x + (cameraX - POSICAO_INICIAL_CAMERA),y);
		batch.end();
	}

	@Override
	/** Responsavel por receber os novos pontos para somar com os anteriores **/
	public void recebeAtributos(int atributos) {
		pontos += atributos;
		if(atributos < 0) {
			pontos = 0;
		}
	}
	
	/**
	 * Dispose 
	 **/
	public void dispose() {
		font.dispose();

	}
}