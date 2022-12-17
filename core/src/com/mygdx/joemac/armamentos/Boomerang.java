package com.mygdx.joemac.armamentos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Boomerang extends Armas {

	//Constantes utilizadas para evitar o uso de numeros maxigos durante o codigo, sendo nelas especificado dados como a posicao das imagens e o ajuste das posicoes das armas
	private final float DURACAO_FRAME = 0.000000025f;
	private final double AUMENTA_DELAY = 0.00000030;
	private final int QUANTIDADE_IMAGENS_BOOMERANGS = 8, POSICAO_IMAGEM_BOOMERANGS = 16, AJUSTE_POSICAO_X = 64,
	AJUSTE_POSICAO_Y = 44, VELOCIDADE = 750, DISTANCIA_GRANDE = 500, DISTANCIA_PEQUENO = 350, VARIACAO_HITBOX = 15;

	
	
	private int x, y, yminimo, xminimo;
	private float delayGif = 0f;
	private boolean subindo = true, grande;
	private char direcao;
	private Animation<TextureRegion> boomerangPequeno, boomerangGrande;
	private TextureRegion[] boomerangFrames;
	private Texture boomerangImagensPequeno, boomerangImagensGrande;

	
	@Override
	/** Responsavel por inicializar os boomerangs pesquenas, como as suas poscioes e as suas animacoes **/
	public void inicializaPequeno() {
		boomerangImagensPequeno = getarmaspequenas(); //Define que a arma � pequena
		boomerangFrames = new TextureRegion[QUANTIDADE_IMAGENS_BOOMERANGS];

		for (int i = 0; i < QUANTIDADE_IMAGENS_BOOMERANGS; i++) {
			boomerangFrames[i] = getarmaspequenasframes()[i + POSICAO_IMAGEM_BOOMERANGS];
		}

		boomerangPequeno = new Animation<TextureRegion>(DURACAO_FRAME, boomerangFrames);
		
		criarHitBox(0,0,0,0);

	}

	@Override
	/** Responsavel por inicializar as armas grandes, como as suas posicoes e as suas animacoes **/
	public void inicializaGrande() {
		boomerangImagensGrande = getarmasgrandes();  //Define que a arma � grande
		boomerangFrames = new TextureRegion[QUANTIDADE_IMAGENS_BOOMERANGS];

		for (int i = 0; i < QUANTIDADE_IMAGENS_BOOMERANGS; i++) {
			boomerangFrames[i] = getarmaspequenasframes()[i + POSICAO_IMAGEM_BOOMERANGS];
		}

		boomerangGrande = new Animation<TextureRegion>(DURACAO_FRAME, boomerangFrames);
		
		criarHitBox(0,0,0,0);

	}

	@Override
	/** Responsavel por criar o boomerang pequeno **/
	public void criaPequeno(int x, int y) {
		this.x = x + (AJUSTE_POSICAO_X * 2);
		this.y = y + (AJUSTE_POSICAO_Y * 2);
		yminimo = this.y;
		xminimo = this.x;

		criarHitBox(boomerangImagensPequeno.getHeight() / (getQuantidadeLinhas() + (getQuantidadeLinhas()/4)),
				boomerangImagensPequeno.getWidth() / (getQuantidadeColunas() + (getQuantidadeColunas()/4)), this.x + VARIACAO_HITBOX, this.y + VARIACAO_HITBOX);

		grande = false;
	}

	@Override
	/** Responsavel por criar o boomerang grande **/
	public void criaGrande(int x, int y) {
		this.x = x + AJUSTE_POSICAO_X;
		this.y = y + AJUSTE_POSICAO_Y;
		yminimo = this.y;
		xminimo = this.x;

		criarHitBox(boomerangImagensGrande.getHeight() / (getQuantidadeLinhas() + (getQuantidadeLinhas()/4)),
				boomerangImagensGrande.getWidth() / (getQuantidadeColunas() + (getQuantidadeColunas()/4)), this.x + (VARIACAO_HITBOX * 2), this.y +  (VARIACAO_HITBOX * 2));

		grande = true;
	}

	@Override
	/** Responsavel por desenhar o boomerang na tela **/
	public void desenha(char direcao,SpriteBatch batch) {
		delayGif += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY; // Aplicando o delay de passagem de frames

		this.direcao = direcao;

		batch.begin();
		if (grande == true)
			batch.draw(boomerangGrande.getKeyFrame(delayGif, true), x, y,
					boomerangImagensGrande.getWidth() / getQuantidadeColunas(),
					boomerangImagensGrande.getHeight() / getQuantidadeLinhas());
		else
			batch.draw(boomerangPequeno.getKeyFrame(delayGif, true), x, y,
					boomerangImagensPequeno.getWidth() / getQuantidadeColunas(),
					boomerangImagensPequeno.getHeight() / getQuantidadeLinhas());
		batch.end();
	}

	@Override
	/** Responsavel por movimentar o boomerang na tela **/
	public boolean movimenta() {
		// TODO Auto-generated method stub
		int distanciaFinal;

		switch (direcao) {
		case '-':

			if (subindo) {
				x -= VELOCIDADE * Gdx.graphics.getDeltaTime();

				if (grande) {
					distanciaFinal = DISTANCIA_GRANDE;
				} else {
					distanciaFinal = DISTANCIA_PEQUENO;
				}

				if (x <= (xminimo - distanciaFinal)) {
					subindo = false;
				}
			} else {
				x += VELOCIDADE * Gdx.graphics.getDeltaTime();
			}
			
			if(grande)setarPosicao(x  + (VARIACAO_HITBOX * 2), y + (VARIACAO_HITBOX*2));
			else setarPosicao(x  + VARIACAO_HITBOX, y + VARIACAO_HITBOX);
			if (x < xminimo)
				return true;
			else {
				subindo = true;
				return false;
			}
		case '+':

			if (subindo) {
				x += VELOCIDADE * Gdx.graphics.getDeltaTime();

				if (grande) {
					distanciaFinal = DISTANCIA_GRANDE;
				} else {
					distanciaFinal = DISTANCIA_PEQUENO;
				}

				if (x >= (xminimo + distanciaFinal)) {
					subindo = false;
				}
			} else {
				x -= VELOCIDADE * Gdx.graphics.getDeltaTime();
			}

			if(grande)setarPosicao(x  + (VARIACAO_HITBOX * 2), y + (VARIACAO_HITBOX*2));
			else setarPosicao(x  + VARIACAO_HITBOX, y + VARIACAO_HITBOX);
			if (x > xminimo)
				return true;
			else {
				subindo = true;
				return false;
			}
		case '.':
			if (subindo) {
				y += VELOCIDADE * Gdx.graphics.getDeltaTime();
				if (y >= (yminimo + DISTANCIA_PEQUENO)) {
					subindo = false;
				}
			} else {
				y -= VELOCIDADE * Gdx.graphics.getDeltaTime();
			}
			if(grande)setarPosicao(x  + (VARIACAO_HITBOX * 2), y + (VARIACAO_HITBOX*2));
			else setarPosicao(x  + VARIACAO_HITBOX, y + VARIACAO_HITBOX);

			if (y > yminimo)
				return true;
			else {
				subindo = true;
				return false;
			}
		}

		return true;
	}

}