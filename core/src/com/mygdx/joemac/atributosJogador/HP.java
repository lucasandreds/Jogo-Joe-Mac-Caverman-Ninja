package com.mygdx.joemac.atributosJogador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HP extends Atributos {
	public final int TOTAL_HP = 18, VARIACAO_Y = 10, VARIACAO_X = 125,
	QUANTIDADE_COLUNAS = 2;
	
	private Texture imagemHP;
	private int x, y, atualHP = TOTAL_HP;
	private TextureRegion[][] frames;
	private TextureRegion [] framesHP;
	
	@Override
	/** Responsavel por criar o HP na tela **/
	public void criaAtributo() {
		imagemHP = new Texture("Imagens/BarraVida.png");
		
		framesHP = new TextureRegion[QUANTIDADE_COLUNAS];
		frames = TextureRegion.split(imagemHP,imagemHP.getWidth()/QUANTIDADE_COLUNAS,imagemHP.getHeight()); 
		
		for (int j = 0; j < QUANTIDADE_COLUNAS; j++) {
			framesHP[j] = frames[0][j];
		}
		
		x = getXEstipulado() + VARIACAO_X;
		y = getYEstipulado() + VARIACAO_Y;
	}
	
	@Override
	/** Responsavel por desenhar o HP na tela **/
	public void desenhaAtributo(SpriteBatch batch,int cameraX, int POSICAO_INICIAL_CAMERA) {
		batch.begin();
		
		for(int i=0;i<TOTAL_HP;i++) {
			if(i < atualHP)batch.draw(framesHP[0],x + ((imagemHP.getWidth()/QUANTIDADE_COLUNAS) * i) + (cameraX - POSICAO_INICIAL_CAMERA),y,imagemHP.getWidth()/QUANTIDADE_COLUNAS, imagemHP.getHeight());
			else batch.draw(framesHP[1],x + ((imagemHP.getWidth()/QUANTIDADE_COLUNAS) * i) + (cameraX - POSICAO_INICIAL_CAMERA),y,imagemHP.getWidth()/QUANTIDADE_COLUNAS, imagemHP.getHeight());
		}
		
		batch.end();	
	}
	
	@Override
	/** Responsavel por receber os atributos de HP que vao mudar o HP na tela **/
	public void recebeAtributos(int atributos) {
		atualHP -= atributos;
		
		if(atualHP > 18) atualHP = TOTAL_HP;
		
		if(atualHP <= 0) {
			setMorreu(true);
			atualHP = TOTAL_HP;
			// Chamar a funcao para reiniciar e aparecer a imagem da pessoa morrendo
		}
	}
	
	/**
	 * Dispose 
	 **/
	public void dispose() {
		imagemHP.dispose();

	}
}
