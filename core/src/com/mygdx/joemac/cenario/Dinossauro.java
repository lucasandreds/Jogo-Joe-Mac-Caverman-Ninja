package com.mygdx.joemac.cenario;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Dinossauro extends Cenario implements com.mygdx.joemac.joeMacCavermanNinja.HitBox {
	private Rectangle hitbox;

	@Override
	/** Responsavel por criar o dinossauro, inicializando a hitbox **/
	public void criaCenario() {
		criarHitBox(1, 490, 900, 350);
		
	}

	@Override
	public void desenhaCenario(SpriteBatch batch) {}

	@Override
	/** Responsavel por criar a hitbox do dinossauro **/
	public void criarHitBox(int altura, int largura, int x, int y) {
		hitbox = new Rectangle();

		hitbox.x = x;
		hitbox.y = y;
		
		hitbox.width = largura;
		hitbox.height = altura;
	}

	@Override
	public void setarPosicao(int X, int Y) {}

	@Override
	/** Responsavel por pegar a hitbox do dinossauro**/
	public Rectangle pegarHitBox() {
		return hitbox;
	}

}

