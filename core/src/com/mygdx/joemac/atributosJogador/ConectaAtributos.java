package com.mygdx.joemac.atributosJogador;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class ConectaAtributos {
	private Array<Atributos> atributos;
	private boolean morreu = false, perdeuVidas = false;

	/** Responsavel por criar os atributos que vao aparecer na tela**/
	public void criaAtributos(){
		atributos = new Array<Atributos>();
		
		int index = 0;
		
		Atributos atributo = new Retrato();
		atributos.add(atributo);
		atributos.get(index).criaAtributo();
		index++;
		atributo = new HP();
		atributos.add(atributo);
		atributos.get(index).criaAtributo();
		index++;
		atributo = new Pontuacao();
		atributos.add(atributo);
		atributos.get(index).criaAtributo();
		index++;
		atributo = new Vida();
		atributos.add(atributo);
		atributos.get(index).criaAtributo();
	}
	
	/** Responsavel por desenhar os atributos que vao aparecer na tela**/
	public void desenhaAtributos(SpriteBatch batch, int cameraX, int POSICAO_INICIAL_CAMERA) {
		atributos.get(0).desenhaAtributo(batch,cameraX,POSICAO_INICIAL_CAMERA);
		atributos.get(1).desenhaAtributo(batch,cameraX,POSICAO_INICIAL_CAMERA);
		atributos.get(2).desenhaAtributo(batch,cameraX,POSICAO_INICIAL_CAMERA);
		atributos.get(3).desenhaAtributo(batch,cameraX,POSICAO_INICIAL_CAMERA);
	}
	
	/** Responsavel por receber os atributos que vao mudar os atributos **/
	public void recebeAtributos(int danos, int pontosGanhos) {
		int vidasPerdidas = 0;
		
		atributos.get(1).recebeAtributos(danos);
		
		if(atributos.get(1).isMorreu() == true) {
			atributos.get(1).setMorreu(false);
			vidasPerdidas++;
			morreu = true;
		}
		
		atributos.get(3).recebeAtributos(vidasPerdidas);
		
		if(atributos.get(3).isPerdeuVidas() == true) {
			atributos.get(3).setPerdeuVidas(false);
			pontosGanhos = -1;
			atributos.get(3).recebeAtributos(-2);
			setPerdeuVidas(true);
		}	
		
		atributos.get(2).recebeAtributos(pontosGanhos);
	}
	
	/** 
	 * GETTERS AND SETTERS
	 * **/
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
	
	/**
	 * DISPOSE
	 */
	public void dispose() {
		for(int i = 0; i < 4; i++) {
			atributos.get(i).dispose();
		}
	}
}
