package com.mygdx.joemac.armamentos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.joemac.joeMacCavermanNinja.HitBox;

public abstract class Armas implements HitBox {
	
	private final int QUANTIDADE_LINHAS = 4, QUANTIDADE_COLUNAS = 8;
	
	private Rectangle hitbox;
	private TextureRegion framesPequenas[][],framesGrandes[][] ;
	private TextureRegion armasPequenasFrames[],armasGrandesFrames[] ;
	private Texture armasPequenas, armasGrandes;
	
	
	public abstract void inicializaPequeno();
	public abstract void inicializaGrande();
	public abstract void criaPequeno(int x, int y);
	public abstract void criaGrande(int x, int y);
	public abstract void desenha(char direcao,SpriteBatch batch);
	public abstract boolean movimenta();
	
	public Armas() {
		criaArmas();
	}
	
	@Override
	/** Resposanvel por criar a HitBox das armas**/
	public void criarHitBox(int tamanho, int largura, int x, int y) {
		// TODO Auto-generated method stub
		hitbox = new Rectangle();
		hitbox.x = x; 
		hitbox.y = y; 
		hitbox.width = largura;
		hitbox.height = tamanho; 
	}

	@Override
	/** Responsavel por modificar a posicao da HitBox**/
	public void setarPosicao(int X, int Y) {
		// TODO Auto-generated method stub
		hitbox.x = X; 
		hitbox.y = Y;
		
	}

	@Override
	/** Responsavel por pegar a HitBox, sendo assim possivel acessar ele de outros codigos **/
	public Rectangle pegarHitBox() {
		// TODO Auto-generated method stub
		return hitbox;
		
	}
	
	/** Responsavel por criar os frames das armas **/
	public void criaArmas() {
		armasPequenas = new Texture(Gdx.files.internal("desktop/Imagens/ArmasPequenas.png")); 
		framesPequenas = TextureRegion.split(armasPequenas,armasPequenas.getWidth()/QUANTIDADE_COLUNAS,armasPequenas.getHeight()/QUANTIDADE_LINHAS); 
		
		armasGrandes = new Texture(Gdx.files.internal("desktop/Imagens/ArmasGrandes.png")); 
		framesGrandes = TextureRegion.split(armasGrandes,armasGrandes.getWidth()/QUANTIDADE_COLUNAS,armasGrandes.getHeight()/QUANTIDADE_LINHAS); 
		separaframes();
	}
	
	/** Responsavel por separar os frames da animacao **/
	private void separaframes() {
		armasPequenasFrames = new TextureRegion[QUANTIDADE_LINHAS * QUANTIDADE_COLUNAS]; 
		armasGrandesFrames = new TextureRegion[QUANTIDADE_LINHAS * QUANTIDADE_COLUNAS]; 
		
		int index = 0; 
		for (int i = 0; i < QUANTIDADE_LINHAS; i++) {
			for (int j = 0; j < QUANTIDADE_COLUNAS; j++) {
				armasPequenasFrames[index] = framesPequenas[i][j];
				armasGrandesFrames[index++] = framesGrandes[i][j];
			}
		}
	}
	
	/**
	 * GETTERS AND SETTERS
	 */
	public TextureRegion [] getarmaspequenasframes() {
		return armasPequenasFrames;
		
	}
	
	public TextureRegion [] getarmasgrandesframes() {
		return armasGrandesFrames;
		
	}

	public Texture getarmaspequenas() {
		return armasPequenas;
	}
	
	public Texture getarmasgrandes() {
		return armasGrandes;
	}
	
	public int getQuantidadeLinhas() {
		return QUANTIDADE_LINHAS;
	}
	public int getQuantidadeColunas() {
		return QUANTIDADE_COLUNAS;
	}
	
	/**
	 * Dispose 
	 **/
	public void dispose() {
		armasGrandes.dispose();
		armasPequenas.dispose();

	}
}