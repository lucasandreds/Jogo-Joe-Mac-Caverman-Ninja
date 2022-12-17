package com.mygdx.joemac.armamentos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Machado extends Armas {

	private final float DURACAO_FRAME = 0.000000025f;
	private final double AUMENTA_DELAY = 0.00000030, VARIACAO_VELOCIDADE_GRANDE = 0.1,
			VARIACAO_VELOCIDADE_PEQUENO = 0.2, AUMENTO_VELOCIDADE = 1.0;
	private final int QUANTIDADE_IMAGENS_MACHADOS = 8, POSICAO_IMAGEM_MACHADO = 8, AJUSTE_POSICAO = 64,
			VELOCIDADE = 750, AJUSTE_VELOCIDADE_Y = 50, DISTANCIA_Y = 350, LIMITE_GRANDE = -50, LARGURA_TELA = 3060,
			VARIACAO_X_HITBOX = 20, VARIACAO_Y_HITBOX = 40;

	private int x, y, yminimo;
	private double aumentaVelocidade = AUMENTO_VELOCIDADE;
	private float delayGif = 0f;
	private boolean subindo = true, grande;
	private char direcao;
	private Animation<TextureRegion> machadoPequeno, machadoGrande;
	private TextureRegion[] machadoFrames;
	private Texture machadoImagensPequeno, machadoImagensGrande;

	@Override
	/** Responsavel por inicializar os machados pequenos, como as suas posicoes e as suas animacoes **/
	public void inicializaPequeno() {
		machadoImagensPequeno = getarmaspequenas();
		machadoFrames = new TextureRegion[QUANTIDADE_IMAGENS_MACHADOS];
		
		for (int i = 0; i < QUANTIDADE_IMAGENS_MACHADOS; i++) {
			machadoFrames[i] = getarmaspequenasframes()[i + POSICAO_IMAGEM_MACHADO];
		}

		machadoPequeno = new Animation<TextureRegion>(DURACAO_FRAME, machadoFrames);
		
		criarHitBox(0,0,0,0);
	}

	@Override
	/** Responsavel por inicializar os machados grandes, como as suas posicoes e as suas animacoes **/
	public void inicializaGrande() {
		machadoImagensGrande = getarmasgrandes();
		machadoFrames = new TextureRegion[QUANTIDADE_IMAGENS_MACHADOS];

		for (int i = 0; i < QUANTIDADE_IMAGENS_MACHADOS; i++) {
			machadoFrames[i] = getarmasgrandesframes()[i + POSICAO_IMAGEM_MACHADO];
		}

		machadoGrande = new Animation<TextureRegion>(DURACAO_FRAME, machadoFrames);

		criarHitBox(0,0,0,0);
	}

	@Override
	/** Responsavel por criar o machado pequeno **/
	public void criaPequeno(int x, int y) {
		this.x = x + (AJUSTE_POSICAO * 2);
		this.y = y + (AJUSTE_POSICAO * 2);
		yminimo = 0 + y;
		criarHitBox(machadoImagensPequeno.getHeight() / (getQuantidadeLinhas() + (getQuantidadeLinhas() / 2)),
				machadoImagensPequeno.getWidth() / (getQuantidadeColunas() + (getQuantidadeColunas()/2)), this.x + VARIACAO_X_HITBOX, this.y);

		grande = false;

		aumentaVelocidade = AUMENTO_VELOCIDADE;

	}

	@Override
	/** Responsavel por criar o machado grande **/
	public void criaGrande(int x, int y) {
		this.x = x + AJUSTE_POSICAO;
		this.y = y + AJUSTE_POSICAO;
		yminimo = LIMITE_GRANDE + y;

		criarHitBox(machadoImagensGrande.getHeight() / (getQuantidadeLinhas() + (getQuantidadeLinhas() / 2)),
				machadoImagensGrande.getWidth() / (getQuantidadeColunas() + (getQuantidadeColunas()/2)), this.x + (VARIACAO_X_HITBOX * 2), this.y + VARIACAO_Y_HITBOX);

		grande = true;

		aumentaVelocidade = AUMENTO_VELOCIDADE;
	}

	@Override
	/** Responsavel por desenhar o  machado na tela **/
	public void desenha(char direcao,SpriteBatch batch) {
		delayGif += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY; // Aplicando o delay de passagem de frames

		this.direcao = direcao;

		batch.begin();
		
		if (grande == true)
			batch.draw(machadoGrande.getKeyFrame(delayGif, true), x, y,
					machadoImagensGrande.getWidth() / getQuantidadeColunas(),
					machadoImagensGrande.getHeight() / getQuantidadeLinhas());
		else
			batch.draw(machadoPequeno.getKeyFrame(delayGif, true), x, y,
					machadoImagensPequeno.getWidth() / getQuantidadeColunas(),
					machadoImagensPequeno.getHeight() / getQuantidadeLinhas());

		batch.end();

	}

	@Override
	/** Responsavel por movimentar o machado na tela **/
	public boolean movimenta() {
		switch (direcao) {
		case '-':
			x -= (VELOCIDADE + AJUSTE_POSICAO) * Gdx.graphics.getDeltaTime();
			y -= aumentaVelocidade * AJUSTE_VELOCIDADE_Y * Gdx.graphics.getDeltaTime();

			if (x < -machadoImagensPequeno.getWidth() / getQuantidadeColunas()) {
				return false;
			}

			break;
		case '+':
			x += VELOCIDADE * Gdx.graphics.getDeltaTime();
			y -= aumentaVelocidade * AJUSTE_VELOCIDADE_Y * Gdx.graphics.getDeltaTime();

			if (x > LARGURA_TELA) {
				return false;
			}

			break;
		case '.':
			if (subindo) {
				y += VELOCIDADE * Gdx.graphics.getDeltaTime();
				if (y >= DISTANCIA_Y) {
					subindo = false;
				}
			} else {
				y -= VELOCIDADE * Gdx.graphics.getDeltaTime();
			}

			break;
		}
		if (grande) {
			aumentaVelocidade += VARIACAO_VELOCIDADE_GRANDE;
			setarPosicao(x + (VARIACAO_X_HITBOX * 2), y + (VARIACAO_Y_HITBOX));
		}else {
			aumentaVelocidade += VARIACAO_VELOCIDADE_PEQUENO;
			setarPosicao(x + VARIACAO_X_HITBOX, y);
		}
		if (y >= yminimo)
			return true;
		else {
			aumentaVelocidade = AUMENTO_VELOCIDADE;
			subindo = true;
			return false;
		}
	}

}