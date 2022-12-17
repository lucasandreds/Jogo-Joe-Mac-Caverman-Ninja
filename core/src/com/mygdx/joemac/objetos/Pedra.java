package com.mygdx.joemac.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Pedra extends Objetos{

	private int x, y, xMorte = 0, yMorte = 0;

	private Texture pedra;
	private TextureRegion frames[][];
	private TextureRegion pedraFrames[];
	
	private boolean quebrou;

	/**
	 * Inicializa variaveis e chama as funcoes para a criacao da hitbox e da
	 * separacao de frames
	 **/
	
	public void cria() {
		this.x = 80;
		this.y = 140;

		pedraFrames = new TextureRegion[4];
		pedra = new Texture(Gdx.files.internal("Imagens/pedra.png"));
		frames = TextureRegion.split(pedra, pedra.getWidth() / 4, pedra.getHeight() / 1);

		separaFrames();
		criarHitBox(pedra.getHeight() / 1, pedra.getWidth() / 4, 500, 500);

	}
	
	/**
	 * Desenha as pedras
	 **/
	
	public void desenha(SpriteBatch batch, int x, int y) {
		batch.begin();
		batch.draw(pedraFrames[0], this.x + x, this.y + y, 100, 100);
		batch.end();
		
		setarPosicao((x + this.x),(y + this.y));
	}
	

	/**
	 * Cuida da movimentacao
	 **/
	
	public void movimenta(Rectangle arma, char sinal) {
		
		switch(sinal) {
		case '+':
			x += 400 * Gdx.graphics.getDeltaTime();
			// Movimentacao horizontal para a esquerda
			if((pegarHitBox().y + yMorte) >= 50) { 
				// Se o inimigo morrer, a pedra cai 
				y -= 400 * Gdx.graphics.getDeltaTime();
			}
			
			break;
		case '-':
			x -= 400 * Gdx.graphics.getDeltaTime();
			
			if((pegarHitBox().y + yMorte) >=50) { 
				y -= 400 * Gdx.graphics.getDeltaTime();
			}
			break;
		}
		
		if(pegarHitBox().overlaps(arma)) {// Se o usuario a atingir com a arma, a pedra quebra
			quebrou = true;
		}

		limiteBorda();
		
	}
	
	/**
	 * Impede que a pedra ultrapasse a tela
	 **/
	private void limiteBorda() {

		if (pegarHitBox().x >= 3080) {
			quebrou = true;
		}
		if(pegarHitBox().x  <= 0) {
			quebrou = true;
		}
		
		
	}
	
	/**
	 * Separa os frames da pedra
	 **/
	private void separaFrames() {
		int index = 0;

		for (int j = 0; j < 4; j++) {
			pedraFrames[index++] = frames[0][j];
		}

	}
	
	/**
	 * Overrides
	 **/
	
	@Override
	public int getPosicaoMorteX() {
		return xMorte;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPosicaoMorteY() {
		return yMorte;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosicaoMorte(int xMorte, int yMorte) {
		this.xMorte = xMorte;
		this.yMorte = yMorte;
	}

	@Override
	public boolean isQuebrou() {
		return quebrou;
	}
	
	public void setPosicao() {
		this.x = 80;
		this.y = 140;
	}
	
	public void setQuebrou(boolean quebrou) {
		this.quebrou = quebrou;
	}	
	
	/**
	 * Dispose
	 **/
	public void dispose() {
		pedra.dispose();

	}

}