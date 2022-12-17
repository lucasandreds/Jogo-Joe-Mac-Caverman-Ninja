package com.mygdx.joemac.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.joemac.consumiveis.Aneis;

public class Ovo extends Objetos  {

	private int x, y, xMorte, yMorte;
	private Texture ovo;
	private TextureRegion frames[][];
	private TextureRegion ovoFrames[];
	
	private boolean quebrou;
	
	/**
	 * Inicializa variaveis e chama as funcoes para a criacao da hitbox e da
	 * separacao de frames
	 **/
	
	@Override
	public void cria() {
		this.x = 80;
		this.y = 0;
		
		ovoFrames = new TextureRegion[6];
		ovo = new Texture(Gdx.files.internal("Imagens/ovo.png"));
		frames = TextureRegion.split(ovo, ovo.getWidth() / 6, ovo.getHeight() / 1);
		separaFrames();
		criarHitBox(ovo.getHeight() / 1, ovo.getWidth() / 6, this.x, this.y);

	}
	
	/**
	 * Desenha os ovos
	 **/
	
	@Override
	public void desenha(SpriteBatch batch, int x, int y) {
		batch.begin();
		batch.draw(ovoFrames[0], this.x + x, this.y + y, 100, 100);
		batch.end();
		
		setarPosicao(this.x + x,this.y + y + 20);

	}
	
	/**
	 * Cuida da movimentacao
	 **/
	
	public void movimenta(Rectangle arma, Aneis aneis) {
		
		if((y + yMorte) >= 50) {
			y -= 250 * Gdx.graphics.getDeltaTime();
		}
		
		if(pegarHitBox().overlaps(arma)) {
			/* Caso seja quebrado, o ovo dropa um consumivel */
			quebrou = true;
			aneis.criarConsumiveis(xMorte,y + yMorte);
		}
		
		setarPosicao(x,y + 20);
	}
	
	/**
	 * Separa os frames do ovo
	 **/
	
	protected void separaFrames() {
		int index = 0;

		for (int j = 0; j < 1; j++) {
			ovoFrames[index++] = frames[0][j];
		}

	}
	
	/**
	 * Gettes e setters
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
	
	/**
	 * Dispose
	 **/
	public void dispose() {
		ovo.dispose();

	}
	
}