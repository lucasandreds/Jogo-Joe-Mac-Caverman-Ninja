package com.mygdx.joemac.inimigos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.joemac.personagens.Jogador;

public class Pterodactilo extends Inimigos {
	private final int LARGURA_TELA = 3080, ALTURA_TELA = 720;
	/**
	 * Variaveis de cria��o do personagem
	 **/
	private int x = 1800, y = 24;

	private Texture pterodactilo;
	private TextureRegion[] framesPterodactilo;
	private TextureRegion[][] frames;

	/* Animacoes */
	private Animation<TextureRegion> animacaoMovimenta;

	/* Frames de cada acao */
	private TextureRegion[] framesMovimenta;

	/* Velocidade de passagem dos frames */
	private float delayFramesMovimenta;

	/**
	 * Variaveis booleanas
	 **/
	private boolean anda = false;
	private boolean ataca = false;
	@SuppressWarnings("unused")
	private boolean parado = false;
	private boolean morre = false;

	/**
	 * Metodo de criacao
	 **/
	public void cria(int x, int y) {
		this.x = x;
		this.y = y;

		framesPterodactilo = new TextureRegion[7]; // Vai receber os frames ja divididos
		pterodactilo = new Texture(Gdx.files.internal("desktop/Imagens/pterodactilo.png")); // Pega a imagem

		frames = TextureRegion.split(pterodactilo, pterodactilo.getWidth() / 7, pterodactilo.getHeight() / 1);

		// Delay dos frames
		delayFramesMovimenta = 0f;

		separaFrames();
		criarHitBox(pterodactilo.getHeight() / 1, pterodactilo.getWidth() / 7, x, y); // cria a hitbox
	}

	/**
	 * Metodo de movimentacao e acao
	 **/
	public void movimenta(Jogador jogador) {

			if (pegarHitBox().overlaps(jogador.getArma())) {
				jogador.setBateu(true);
				morre = true;
				anda = ataca = parado = false;
				delayFramesMovimenta = 0f;
				
				setarPosicao(-100,100);

			}else if ((jogador.pegarHitBox().x < (x - 10)) && ((jogador.pegarHitBox().x + 500) > x)) {
				// Caso o jogador esteja a uma determinada distancia o pterodactilo se
				// desloca para chegar perto
				anda = true;
				ataca = parado = morre = false;

				x -= 100 * Gdx.graphics.getDeltaTime();
				y += 100 * Gdx.graphics.getDeltaTime();

			} else if (jogador.pegarHitBox().x >= (x - 10)) {
				// Se o jogador estiver a frente do pterodactilo ele ataca
				ataca = true;
				
				anda = parado = morre = false;

			} else {
				// Caso nenhuma das alternativas seja verdadeira ele fica parado
				parado = true;
				anda = ataca = morre = false;

			} 

		limiteBorda();
	}

	/**
	 * Desenha o pterodactilo
	 **/
	public void desenha(SpriteBatch batch) {
		TextureRegion frameAtual;

		delayFramesMovimenta += Gdx.graphics.getDeltaTime() * 0.00000015;

		if (anda == true) {
			frameAtual = movimenta();

		} else if (ataca == true) {
			frameAtual = framesPterodactilo[3];;
			
		} else {
			frameAtual = movimenta();

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

		for (int coluna = 0; coluna < 7; coluna++) {
			framesPterodactilo[index++] = frames[0][coluna];
		}
	}

	/* Frames para quando eles esta parado ou voando */
	private TextureRegion movimenta() {
		framesMovimenta = new TextureRegion[4];
		
		for (int i = 0; i < 4; i++) {
			framesMovimenta[i] = framesPterodactilo[i];
		}

		animacaoMovimenta = new Animation<TextureRegion>(0.0000001f, framesMovimenta);

		return (TextureRegion) animacaoMovimenta.getKeyFrame(delayFramesMovimenta, true);
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
			
		}

		if (y < 0) {
			y = 0;
		}
		
		if (y > ALTURA_TELA - 300) {
			y= ALTURA_TELA - 300;
			
		}

		setarPosicao(x, y);
	}


	/**
	 * Elimina a imagem
	 **/
	
	public void dispose() {
		pterodactilo.dispose();

	}
	
	/**
	 * GETTERS AND SETTERS
	**/
	
	public boolean isMorre() {
		return morre;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public boolean isAtaca() {
		return ataca;
	}
	
	public boolean isAnda() {
		return anda;
	}

	public void setMorre(boolean morre) {
		this.morre = morre;
	}
}
