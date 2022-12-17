package com.mygdx.joemac.armamentos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Osso extends Armas {

	private final int AJUSTE_POSICAO_Y_GRANDE = 54, AJUSTE_POSICAO_X = 64, AJUSTE_POSICAO_Y_PEQUENO = 48,
			VELOCIDADE = 750, DISTANCIA_GRANDE = 700, DISTANCIA_PEQUENA = 350, LARGURA_TELA = 3060, ALTURA_TELA = 720;;

	private int x, y, xminimo, yminimo, passagens;
	private boolean grande, esquerdaGrande = false, esquerdaPequeno = false;
	private char direcao;
	TextureRegion osso;

	@Override
	/** Responsavel por inicializar os ossos pequenos, como as suas posicoes **/
	public void inicializaPequeno() {
		criarHitBox(0,0,0,0);
	}

	@Override
	/** Responsavel por inicializar os ossos grandes, como as suas posicoes **/
	public void inicializaGrande() {
		criarHitBox(0,0,0,0);
	}

	@Override
	/** Responsavel por criar o osso pequeno **/
	public void criaPequeno(int x, int y) {
		this.x = x + (AJUSTE_POSICAO_X * 2);
		this.y = y + AJUSTE_POSICAO_Y_PEQUENO;
		yminimo = this.y;
		xminimo = this.x;

		criarHitBox(getarmaspequenas().getHeight() / (getQuantidadeLinhas() * 4),
				getarmaspequenas().getWidth() / (getQuantidadeColunas() * 2), this.x, this.y * 2);
		osso = getarmaspequenasframes()[1];
		grande = false;

		passagens = 0;
	}

	@Override
	/** Responsavel por criar o osso grande **/
	public void criaGrande(int x, int y) {
		this.x = x + AJUSTE_POSICAO_X;
		this.y = y + AJUSTE_POSICAO_Y_GRANDE;
		yminimo = this.y;
		xminimo = this.x;

		criarHitBox(getarmaspequenas().getHeight() / (getQuantidadeLinhas() * 2),
				getarmaspequenas().getWidth() / getQuantidadeColunas(), this.x, (2 * this.y) - (this.y/4));
		osso = getarmasgrandesframes()[0];
		grande = true;

		passagens = 0;
	}

	@Override
	/** Responsavel por desenhar o osso na tela **/
	public void desenha(char direcao,SpriteBatch batch) {
		this.direcao = direcao;

		if (grande) {
			if ((direcao == '.') && (grande == false))
				osso = getarmaspequenasframes()[0];
			else if ((direcao == '-') && (passagens == 0) && (esquerdaGrande == false)) {
				osso.flip(true, false);
				esquerdaGrande = true;
			} else if (direcao == '+' && (esquerdaGrande == true)) {
				osso.flip(true, false);
				esquerdaGrande = false;
			}
		} else {
			if ((direcao == '.') && (grande == false))
				osso = getarmaspequenasframes()[0];
			else if ((direcao == '-') && (passagens == 0) && (esquerdaPequeno == false)) {
				osso.flip(true, false);
				esquerdaPequeno = true;
			} else if (direcao == '+' && (esquerdaPequeno == true)) {
				osso.flip(true, false);
				esquerdaPequeno = false;
			}
		}
		passagens++;

		batch.begin();
		batch.draw(osso, x, y, getarmaspequenas().getWidth() / getQuantidadeColunas(),
				getarmaspequenas().getHeight() / getQuantidadeLinhas());
		batch.end();
	}

	@Override
	/** Responsavel por movimentar o osso na tela **/
	public boolean movimenta() {
		int distanciaFinal;

		switch (direcao) {
		case '-':
			x -= VELOCIDADE * Gdx.graphics.getDeltaTime();

			if (x < -(getarmaspequenas().getWidth() / getQuantidadeColunas())) {
				return false;
			}

			if (grande) {
				distanciaFinal = DISTANCIA_GRANDE;

			} else {
				distanciaFinal = DISTANCIA_PEQUENA;

			}

			if(grande)setarPosicao(x,(2 * y) - (y/4));
			else setarPosicao(x, y * 2);
			if (x >= (xminimo - distanciaFinal)) {
				return true;
			}
			break;
		case '+':

			x += VELOCIDADE * Gdx.graphics.getDeltaTime();

			if (x > LARGURA_TELA) {
				return false;
			}

			if (grande) {
				distanciaFinal = DISTANCIA_GRANDE;
			} else {
				distanciaFinal = DISTANCIA_PEQUENA;
			}

			if(grande)setarPosicao(x,(2 * y) - (y/4));
			else setarPosicao(x, y * 2);
			if (x <= (xminimo + distanciaFinal)) {
				return true;
			}

			break;
		case '.':
			y += VELOCIDADE * Gdx.graphics.getDeltaTime();

			if (y > ALTURA_TELA) {
				return false;
			}

			if(grande)setarPosicao(x,(2 * y) - (y/4));
			else setarPosicao(x, y * 2);
			if (y <= (yminimo + DISTANCIA_PEQUENA)) {
				return true;
			}

			break;
		}
		return false;
	}
}