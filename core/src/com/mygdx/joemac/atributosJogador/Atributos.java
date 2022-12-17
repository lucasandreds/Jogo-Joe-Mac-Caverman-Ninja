package com.mygdx.joemac.atributosJogador;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Atributos {
	
	private final int Y_ESTIPULADO = 570,X_ESTIPULADO = 0;
	
	private boolean morreu = false, perdeuVidas = false;
	
	public int getYEstipulado() {
		return Y_ESTIPULADO;
	}

	public int getXEstipulado() {
		return X_ESTIPULADO;
	}
	
	public boolean isMorreu() {
		return morreu;
	}

	public void setMorreu(boolean morreu) {
		this.morreu = morreu;
	}

	public boolean isPerdeuVidas() {
		return perdeuVidas;
	}

	public void setPerdeuVidas(boolean perdeuVidas) {
		this.perdeuVidas = perdeuVidas;
	}

	public abstract void criaAtributo();
	public abstract void desenhaAtributo(SpriteBatch batch, int cameraX, int POSICAO_INICIAL_CAMERA);
	public abstract void recebeAtributos(int atributos );
	public abstract void dispose();
	
	
}
