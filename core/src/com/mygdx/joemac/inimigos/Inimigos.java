package com.mygdx.joemac.inimigos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.joemac.joeMacCavermanNinja.HitBox;
import com.mygdx.joemac.personagens.Jogador;

public abstract class Inimigos implements HitBox {

	public abstract void cria(int x, int y);
	public abstract void desenha(SpriteBatch batch);
	public abstract void movimenta(Jogador jogador);
	public abstract boolean isMorre();
	public abstract int getX();
	public abstract int getY();
	
	private Rectangle Hitbox;
	@Override
	public void criarHitBox(int tamanho, int largura, int x, int y) {
		// TODO Auto-generated method stub
		Hitbox = new Rectangle();
		Hitbox.x = x; 
		Hitbox.y = y; 
		Hitbox.width = largura;
		Hitbox.height = tamanho; 
	}

	@Override
	public void setarPosicao(int X, int Y) {
		// TODO Auto-generated method stub
		Hitbox.x = X; 
		Hitbox.y = Y;
		
	}

	@Override
	public Rectangle pegarHitBox() {
		// TODO Auto-generated method stub
		return Hitbox;
		
	}
	
	public abstract void dispose();
}