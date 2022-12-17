package com.mygdx.joemac.consumiveis;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.joemac.joeMacCavermanNinja.HitBox;

public abstract class Consumiveis implements HitBox {
	private Rectangle hitbox;
	
	public abstract void desenharConsumiveis(SpriteBatch batch);
	public abstract void criarConsumiveis(int x, int y);
	public abstract void controlarConsumiveis(Rectangle jogador);
	public abstract boolean getJogadorPegou();
	public abstract int recebeAtributos();

	@Override
	/** Responsavel por criar a hitbox dos consumiveis**/
	public void criarHitBox(int tamanho, int largura, int x, int y) {
		hitbox = new Rectangle();
		hitbox.x = x;
		hitbox.y = y;
		hitbox.width = largura;
		hitbox.height = tamanho;
	}

	@Override
	/** Responsavel por mudar a posicao da hitbox dos consumiveis**/
	public void setarPosicao(int y, int x) {
		hitbox.x = x;
		hitbox.y = y;
	}

	@Override
	/** Responsavel por pegar a hitbox dos consumiveis**/
	public Rectangle pegarHitBox() {
		return hitbox;
	}
}