package com.mygdx.joemac.atributosJogador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Retrato extends Atributos {
	private final int VARIACAO = 10;

	private Texture retrato;
	private int x, y;
	
	@Override
	/** Responsavel por criar o retrato **/
	public void criaAtributo() {
		retrato = new Texture("Imagens/Retrato.png");
		
		x = getXEstipulado() - VARIACAO;
		y = getYEstipulado() - VARIACAO;
	}
	
	@Override
	/** Responsavel por desenhar o retrato na tela **/
	public void desenhaAtributo(SpriteBatch batch, int cameraX, int POSICAO_INICIAL_CAMERA) {
		batch.begin();
		batch.draw(retrato,x + (cameraX - POSICAO_INICIAL_CAMERA),y,retrato.getWidth(), retrato.getHeight());
		batch.end();
	}

	@Override
	public void recebeAtributos(int atributos){}
	
	/**
	 * Dispose 
	 **/
	public void dispose() {
		retrato.dispose();

	}
}