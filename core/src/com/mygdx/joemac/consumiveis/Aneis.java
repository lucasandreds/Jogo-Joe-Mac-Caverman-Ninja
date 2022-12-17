package com.mygdx.joemac.consumiveis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Aneis extends Consumiveis {
	
	private boolean jogadorPegou = false;
	private int armaSelecionada = 0;
	
	private Texture aneis;
	private TextureRegion[] framesAneis;
	private TextureRegion[][] frames;
	
	/** Recebe o numero do anel que vai aparecer na tela **/
	public Aneis(int armaSelecionada) {
		this.armaSelecionada = armaSelecionada;
	}
	
	/**
	 * Inicializa variaveis e chama as funcoes para a criacao da hitbox e da
	 * separacao de frames
	 **/
	@Override
	public void criarConsumiveis(int x, int y) {
		// TODO Auto-generated method stub
		framesAneis = new TextureRegion[3]; // Vai receber os frames ja divididos
		aneis = new Texture(Gdx.files.internal("Imagens/AneisArmas.png")); // Pega a imagem

		frames = TextureRegion.split(aneis, aneis.getWidth() / 3, aneis.getHeight());

		separaFrames();
		criarHitBox(aneis.getHeight(), aneis.getWidth() / 3, x, y ); // cria a hitbox		
	}

	/**
	 * Desenha os consumiveis
	 **/
	@Override
	public void desenharConsumiveis(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.begin();
		if(armaSelecionada == 1)batch.draw(framesAneis[1], pegarHitBox().x, pegarHitBox().y, pegarHitBox().width, pegarHitBox().height);
		else if(armaSelecionada == 2)batch.draw(framesAneis[0], pegarHitBox().x, pegarHitBox().y, pegarHitBox().width, pegarHitBox().height);
		else if(armaSelecionada == 3)batch.draw(framesAneis[2], pegarHitBox().x, pegarHitBox().y, pegarHitBox().width, pegarHitBox().height);
		else batch.draw(framesAneis[0], pegarHitBox().x, pegarHitBox().y, pegarHitBox().width, pegarHitBox().height);
		batch.end();	
	}
	
	/**
	 * Controla se o jogador pegou o anel e troca sua arma
	 **/
	@Override
	public void controlarConsumiveis(Rectangle jogador) {
		// TODO Auto-generated method stub
		if (pegarHitBox().overlaps(jogador)) {
			jogadorPegou = true;
			//Ativar vari�vel para sumir com o alimento
		}
	}
	/**
	 * Getters
	 **/
	@Override
	public boolean getJogadorPegou() {
		// TODO Auto-generated method stub
		return jogadorPegou;
	}

	@Override
	public int recebeAtributos() {
		// TODO Auto-generated method stub
		return armaSelecionada;
	}
	/**
	 * Separa os frames da pedra
	 **/
	private void separaFrames() {
		int index = 0;
		for (int coluna = 0; coluna < 3; coluna++) {
			framesAneis[index++] = frames[0][coluna];
		}
	}
	

}
