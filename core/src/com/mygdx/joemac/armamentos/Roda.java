package com.mygdx.joemac.armamentos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Roda extends Armas {
	
	private final float DURACAO_FRAME= 0.000000025f;
	private final double AUMENTA_DELAY = 0.00000020, VARIACAO_VELOCIDADE = 0.2,AUMENTO_VELOCIDADE = 1.0;
	private final int QUANTIDADE_IMAGENS_RODAS = 4, POSICAO_IMAGEM_RODAS = 24,
	AJUSTE_POSICAO_Y = 64, AJUSTE_POSICAO_X = 54,VELOCIDADE = 750, 
	AJUSTE_VELOCIDADE_Y = 50,DISTANCIA_Y = 350;	
	
	private int x,y,yminimo,xJogador = 0;
	private boolean subindo = true, grande;
	private char direcao;
	private double aumentaVelocidade = AUMENTO_VELOCIDADE;
	private float delayGif = 0f;
	private Animation<TextureRegion> rodaPequena, rodaGrande;
	private TextureRegion [] rodaFrames;
	private Texture rodaImagensPequeno,rodaImagensGrande ;
	
	@Override
	/** Responsavel por inicializar as rodas pequenas, como as suas posicoes e suas animacoes **/
	public void inicializaPequeno() {
		// TODO Auto-generated method stub
		rodaImagensPequeno = getarmaspequenas();
		
		rodaFrames = new TextureRegion[QUANTIDADE_IMAGENS_RODAS];
		
		for(int i=0;i<QUANTIDADE_IMAGENS_RODAS;i++) {
			rodaFrames[i] = getarmaspequenasframes()[i + POSICAO_IMAGEM_RODAS];
		}
		
		rodaPequena = new Animation<TextureRegion>(DURACAO_FRAME,rodaFrames);
		
		criarHitBox(0,0,0,0);
	}

	@Override
	/** Responsavel por inicializar as rodas grandes, como as suas posicoes e suas animacoes **/
	public void inicializaGrande() {
		// TODO Auto-generated method stub
		rodaImagensGrande = getarmasgrandes();
		
		rodaFrames = new TextureRegion[QUANTIDADE_IMAGENS_RODAS];
		
		for(int i=0;i<QUANTIDADE_IMAGENS_RODAS;i++) {
			rodaFrames[i] = getarmasgrandesframes()[i + POSICAO_IMAGEM_RODAS];
		}
		
		rodaGrande = new Animation<TextureRegion>(DURACAO_FRAME,rodaFrames);
		
		criarHitBox(0,0,0,0);
		
	}
	
	@Override
	/** Responsavel por criar as rodas pequenas**/
	public void criaPequeno(int x, int y) {
		this.x = x + (AJUSTE_POSICAO_X * 2);this.y = y + (AJUSTE_POSICAO_Y * 2);yminimo = y;
		
		criarHitBox(rodaImagensPequeno.getHeight()/getQuantidadeLinhas(), rodaImagensPequeno.getWidth()/getQuantidadeColunas(),this.x, this.y);

		aumentaVelocidade = AUMENTO_VELOCIDADE;
		
		grande = false;
		xJogador = x;
	}

	@Override
	/** Responsavel por criar as rodas grandes**/
	public void criaGrande(int x, int y) {
		this.x = x + AJUSTE_POSICAO_X;this.y = y + AJUSTE_POSICAO_Y; yminimo = y;
		
		criarHitBox(rodaImagensGrande.getHeight()/getQuantidadeLinhas(),rodaImagensGrande.getWidth()/getQuantidadeColunas(),this.x, this.y);
		
		aumentaVelocidade = AUMENTO_VELOCIDADE;
		
		grande = true;
		
		xJogador = x;
	}

	@Override
	/** Responsavel por desenhar as rodas na tela**/
	public void desenha(char direcao,SpriteBatch batch) {
		this.direcao = direcao;
		
		delayGif += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY; //Aplicando o delay de passagem de frames
		
		
		batch.begin();
		if(grande)batch.draw(rodaGrande.getKeyFrame(delayGif, true),x,y,rodaImagensGrande.getWidth()/getQuantidadeColunas(), rodaImagensGrande.getHeight()/getQuantidadeLinhas() );
		else batch.draw(rodaPequena.getKeyFrame(delayGif, true),x,y,rodaImagensPequeno.getWidth()/getQuantidadeColunas(), rodaImagensPequeno.getHeight()/getQuantidadeLinhas() );
		batch.end();
	}

	@Override
	/** Responsavel por movimentar as rodas na tela **/
	public boolean movimenta() {
		switch(direcao) {
		case '-': 
			x -= (VELOCIDADE + AJUSTE_POSICAO_Y) * Gdx.graphics.getDeltaTime();
			
			if(y >= 50) { 
				y -= aumentaVelocidade * AJUSTE_VELOCIDADE_Y * Gdx.graphics.getDeltaTime();
			}
			
			aumentaVelocidade += VARIACAO_VELOCIDADE;
			setarPosicao(x,y);
			
			if(x >=(xJogador - 1280)) {
				return true;
				
			} else{
				aumentaVelocidade = AUMENTO_VELOCIDADE;
				subindo = true;
				return false;
			
			}
		case '+': 
			x += VELOCIDADE * Gdx.graphics.getDeltaTime();
			if(y >= 50) {
				y -= aumentaVelocidade * AJUSTE_VELOCIDADE_Y * Gdx.graphics.getDeltaTime();
			}
			
			aumentaVelocidade += VARIACAO_VELOCIDADE;
			setarPosicao(x,y);
			
			if(x <= (xJogador + 1280) ) {
				return true;
			
			} else {
				aumentaVelocidade = AUMENTO_VELOCIDADE;
				subindo = true;
				return false;
			}
			
		case '.': 
		    if(subindo) {
		    	y += VELOCIDADE * Gdx.graphics.getDeltaTime();
	    		if(y >= DISTANCIA_Y) {
	    			subindo = false;
	    		}
		    }else {
		    	y -= VELOCIDADE * Gdx.graphics.getDeltaTime();
		    }
		    
		    setarPosicao(x,y);
		    
			if(y >= yminimo ) {
				return true;
			} else{
				aumentaVelocidade = AUMENTO_VELOCIDADE;
				subindo = true;
				return false;
			}
		}
		return false;
	}

}