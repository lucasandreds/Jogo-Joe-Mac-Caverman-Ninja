package com.mygdx.joemac.cenario;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Morro extends Cenario implements com.mygdx.joemac.joeMacCavermanNinja.HitBox {
	private Rectangle hitbox;
	private final int LARGURA_TELA = 3060;

	@Override
	/** Responsavel por criar o cenario do Morro **/
	public void criaCenario() {
		criarHitBox(1, 390, LARGURA_TELA - 390, 350);
	}

	@Override
	/** Responsavel por criar a hitbox do cenario do morro **/
	public void criarHitBox(int tamanho, int largura, int x, int y) {
		hitbox = new Rectangle();
		hitbox.x = x;
		hitbox.y = y;
		
		hitbox.width = largura;
		hitbox.height = tamanho;
	}

	@Override
	/** Responsavel por pegar a hitbox do cenario do morro **/
	public Rectangle pegarHitBox() {
		return hitbox;
	}

	@Override public void setarPosicao(int X, int Y) {}
	@Override public void desenhaCenario(SpriteBatch batch) {}
}