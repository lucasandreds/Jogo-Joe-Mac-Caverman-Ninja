package com.mygdx.joemac.personagens;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.joemac.joeMacCavermanNinja.HitBox;

public class Personagens implements HitBox {
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
	
	public void setarAltura(int tamanho) {
		Hitbox.height = tamanho; 
	}

}
