package com.mygdx.joemac.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.joemac.joeMacCavermanNinja.HitBox;

public abstract class Objetos implements HitBox {
	private Rectangle hitbox;
	
	public abstract void cria();
	public abstract void desenha(SpriteBatch batch, int x, int y);
	public abstract void setPosicaoMorte(int xMorte, int yMorte);
	public abstract int getPosicaoMorteX();
	public abstract int getPosicaoMorteY();
	public abstract boolean isQuebrou(); 
	
	@Override
	public void criarHitBox(int tamanho, int largura, int x, int y) {
		hitbox = new Rectangle();
		hitbox.x = x; 
		hitbox.y = y; 
		hitbox.width = largura;
		hitbox.height = tamanho; 
	}

	@Override
	public void setarPosicao(int X, int Y) {
		hitbox.x = X; 
		hitbox.y = Y;
		
	}

	@Override
	public Rectangle pegarHitBox() {
		return hitbox;
		
	}
}