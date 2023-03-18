package com.mygdx.joemac.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.joemac.personagens.Jogador;

public class HomemCavernas extends Inimigos{
	private final int LARGURA_TELA = 3080;
	/** 
	 * Variaveis de cria��o do personagem 
	 **/
	private int x = 600, y = 24;

	private Texture homemCavernas;
	private TextureRegion[] framesHomemDasCavernas;
	private TextureRegion[][] frames;

	/* Animacoes */
	private Animation<TextureRegion> animacaoAndar;
	private Animation<TextureRegion> animacaoAtacar;
	private Animation<TextureRegion> animacaoMorrer;
	private Animation<TextureRegion> animacaoParado;

	/* Frames de cada acao */
	private TextureRegion[] framesAndar;
	private TextureRegion[] framesAtacar;
	private TextureRegion[] framesMorrer;
	private TextureRegion[] framesParado;
	
	/* Velocidade de passagem dos frames */
	private float delayFramesAndar;
	private float delayFramesAtacar;
	private float delayFramesMorrer;
	private float delayFramesParado;
	

	/** 
	 * Variaveis booleanas 
	 **/
	private boolean anda = false;
	private boolean ataca = false;
	@SuppressWarnings("unused")
	private boolean parado = false;
	private boolean morre = false;

	/**
	 *  Metodo de criacao 
	 **/
	public void cria(int x, int y) {
		this.x = x;
		this.y = y;

		framesHomemDasCavernas = new TextureRegion[48]; // Vai receber os frames ja divididos
		homemCavernas = new Texture(Gdx.files.internal("desktop/Imagens/homemCavernas.png")); // Pega a imagem

		frames = TextureRegion.split(homemCavernas, homemCavernas.getWidth() / 8, homemCavernas.getHeight() / 6);

		// Delay dos frames
		delayFramesAndar = 0f;
		delayFramesAtacar = 0f;
		delayFramesMorrer = 0f;

		separaFrames();
		criarHitBox(homemCavernas.getHeight() / 6, homemCavernas.getWidth() / 8, x, y); // cria a hitbox
	}

	/** 
	 * Metodo de movimentacao e acao 
	**/
	public void movimenta(Jogador jogador) {
			
			if (pegarHitBox().overlaps(jogador.getArma())) {
				jogador.setBateu(true);
				morre = true;
				anda = ataca = parado = false;
				delayFramesAndar = 0f;
				delayFramesAtacar = 0f;
				delayFramesParado = 0f;
				
				setarPosicao(-100,100);
		
			}else if ((jogador.pegarHitBox().x < (x - 120)) && ((jogador.pegarHitBox().x + 500) > x)) {
				// Caso o jogador esteja a uma determinada distancia o homem das cavernas se
				// desloca para chegar perto
				anda = true;
				ataca = parado = morre = false;
				
				x -= 100 * Gdx.graphics.getDeltaTime();
				
				delayFramesAtacar = 0f;
				delayFramesParado = 0f;
				delayFramesMorrer = 0f;

			} else if ((jogador.pegarHitBox().x >= (x - 120)) && (jogador.pegarHitBox().x < (x + 100))) {
				// Se o jogador estiver a frente do homem das cavernas ele ataca
				ataca = true;
				anda = parado = morre = false;
				
				delayFramesAndar = 0f;
				delayFramesParado = 0f;
				delayFramesMorrer = 0f;

			} else {
				// Caso nenhuma das alternativas seja verdadeira ele fica parado
				parado = true;
				anda = ataca = morre = false;
				
				delayFramesAndar = 0f;
				delayFramesAtacar = 0f;
				delayFramesMorrer = 0f;
			}
		limiteBorda();
	}

	/** 
	 * Desenha o homem das cavernas 
	**/
	public void desenha(SpriteBatch batch) {
		TextureRegion frameAtual;

		delayFramesAndar += Gdx.graphics.getDeltaTime() * 0.000000020;
		delayFramesAtacar += Gdx.graphics.getDeltaTime() * 0.000000020;
		delayFramesParado += Gdx.graphics.getDeltaTime() * 0.00000015;
		delayFramesMorrer += Gdx.graphics.getDeltaTime() * 0.0000001;

		if (anda == true) {
			frameAtual = andar();

		} else if (ataca == true) {
			frameAtual = atacar();

		} else if (morre == true) {
			frameAtual = morto();

		} else {
			frameAtual = parado();

		}

		batch.begin();
		batch.draw(frameAtual, x, y, 300, 300);
		batch.end();
	}

	/**
	 * 
	 * Metodos relacionados as animacoes
	 * 
	 **/

	/* Separa os frames e os coloca em um unico vetor */
	private void separaFrames() {
		int index = 0;

		for (int linha = 0; linha < 6; linha++) {
			for (int coluna = 0; coluna < 8; coluna++) {
				framesHomemDasCavernas[index++] = frames[linha][coluna];
			}
		}
	}

	/* Andar */
	private TextureRegion andar() {
		framesAndar = new TextureRegion[4];

		for (int i = 0; i < 4; i++) {
			framesAndar[i] = framesHomemDasCavernas[i];
		}

		animacaoAndar = new Animation<TextureRegion>(0.00000001f, framesAndar);

		return (TextureRegion) animacaoAndar.getKeyFrame(delayFramesAndar, true);
	}

	/* Atacar */
	private TextureRegion atacar() {
		framesAtacar = new TextureRegion[3];

		for (int i = 0; i < 3; i++) {
			framesAtacar[i] = framesHomemDasCavernas[i + 16];
		}

		animacaoAtacar = new Animation<TextureRegion>(0.000000008f, framesAtacar);

		return (TextureRegion) animacaoAtacar.getKeyFrame(delayFramesAtacar, true);
	}

	/* Parado */
	private TextureRegion parado() {
		framesParado = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			framesParado[i] = framesHomemDasCavernas[i + 32];
		}

		animacaoParado = new Animation<TextureRegion>(0.0000001f, framesParado);

		return (TextureRegion) animacaoParado.getKeyFrame(delayFramesParado, true);
	}

	/* Morto */
	private TextureRegion morto() {
		framesMorrer = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			framesMorrer[i] = framesHomemDasCavernas[i + 26];
		}

		animacaoMorrer = new Animation<TextureRegion>(0.00000004f, framesMorrer);

		return (TextureRegion) animacaoMorrer.getKeyFrame(delayFramesMorrer, true);
	}

	/** 
	 * Funcao responsavel por nao deixar o inimigo sair dos limites da tela 
	**/
	private void limiteBorda() {

		if (x < 0) {
			x = 0;
		}

		if (x > LARGURA_TELA - 300) {
			x = LARGURA_TELA - 300;
			anda = false;
		}

		if (y < 0) {
			y = 0;
		}

		setarPosicao(x, y);
	}


	/** 
	 * Elimina a imagem 
	**/
	public void dispose() {
		homemCavernas.dispose();

	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public boolean isMorre() {
		return morre;
	}

	public void setMorre(boolean morre) {
		this.morre = morre;
	}

}