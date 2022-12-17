package com.mygdx.joemac.joeMacCavermanNinja;

import com.badlogic.gdx.math.Rectangle;

public interface HitBox {
	public void criarHitBox(int tamanho, int largura, int x,int y);
	public void setarPosicao(int Y, int X);
	public Rectangle pegarHitBox();
	
}
