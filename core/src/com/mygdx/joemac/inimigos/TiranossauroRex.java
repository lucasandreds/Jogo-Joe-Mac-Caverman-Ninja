package com.mygdx.joemac.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.joemac.objetos.Pedra;
import com.mygdx.joemac.personagens.Jogador;

public class TiranossauroRex extends Inimigos{
	SpriteBatch batch;
	/** 
	 * Variaveis de criacao do personagem 
	 **/
	private int x = 0, y = 50, HP = 50;

	private Texture tiranossauro;
	private TextureRegion[] framesTiranossauro;
	private TextureRegion[][] frames;

	/* Animacoes */
	private Animation<TextureRegion> animacaoAndar;

	/* Frames de cada acao */
	private TextureRegion[] framesAndar;
	
	/* Aumento do delay */
	
	/* Velocidade de passagem dos frames */
	private float delayFramesAndar;
	

	/** 
	 * Variaveis booleanas 
	 **/
	private boolean anda = true;
	private boolean ataca = false;
	private boolean morre = false;

	public void inicializaDinossauro() {
		framesTiranossauro = new TextureRegion[6]; // Vai receber os frames ja divididos
		tiranossauro = new Texture(Gdx.files.internal("Imagens/trex.png")); // Pega a imagem

		frames = TextureRegion.split(tiranossauro, tiranossauro.getWidth() / 3, tiranossauro.getHeight() / 2);

		// Delay dos frames
		delayFramesAndar = 0f;

		separaFrames();
	}
	
	/**
	 *  Metodo de criacao 
	 **/
	public void cria(int x, int y) {
		this.x = x;
		this.y = y;
		
		criarHitBox(tiranossauro.getHeight() / 2, tiranossauro.getWidth() / 3, x,y); // cria a hitbox
	}

	/** 
	 * Metodo de movimentacao e acao 
	**/
	public void movimentaBoss(Jogador jogador,Pedra pedra) {
			if (pegarHitBox().overlaps(jogador.getArma())) {
				if(jogador.isJogou()) {
					if(jogador.getArma().width == 256) {
						HP -= 5;
					}else {
						HP -= 1;
					}
					jogador.setJogou(false);
				}
				
				delayFramesAndar = 0f;
		

			}else if (ataca != true) {
				// Se o jogador estiver a frente do tiranossauro ele ataca
				ataca = true;
				anda = morre = false;
			
				delayFramesAndar = 0f;

			} else {
				// Caso nenhuma das alternativas seja verdadeira ele fica parado
				anda = true;
				
			}
			if(ataca == true) {
				pedra.desenha(batch,x + 200,y + 400);
				pedra.movimenta(jogador.getArma(),'+');
				if(pedra.isQuebrou()){
					ataca = false;
					pedra.setPosicao();
					pedra.setQuebrou(false);
				}
			}
			
			if(HP <= 0) {
				morre = true;
			}
	}

	/** 
	 * Desenha o tiranossauro 
	**/
	public void desenha(SpriteBatch batch) {
		TextureRegion frameAtual;

		delayFramesAndar += Gdx.graphics.getDeltaTime() * 0.000000020;

		if (anda == true) {
			frameAtual = andar();

		} else if (ataca == true) {
			frameAtual = andar();

		} else {
			frameAtual = framesTiranossauro[0];

		}
		batch.begin();
		batch.draw(frameAtual, x, y, 800, 600);
		batch.end();
		
		this.batch = batch;
	}
	/**
	 * 
	 * Metodos relacionados as animacoes
	 * 
	 **/

	/* Separa os frames e os coloca em um unico vetor */
	private void separaFrames() {
		int index = 0;

		for (int linha = 0; linha < 2; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				framesTiranossauro[index++] = frames[linha][coluna];
			}
		}
	}

	/* Andar */
	private TextureRegion andar() {
		framesAndar = new TextureRegion[3];

		for (int i = 0; i < 3; i++) {
			framesAndar[i] = framesTiranossauro[i];
		}

		animacaoAndar = new Animation<TextureRegion>(0.00000001f, framesAndar);

		return animacaoAndar.getKeyFrame(delayFramesAndar, true);
	}

	/** 
	 * Elimina a imagem 
	**/
	public void dispose() {
		tiranossauro.dispose();

	}

	/** 
	 * Getters e setters
	 **/
	
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

	@Override
	public void movimenta(Jogador jogador) {
		// TODO Auto-generated method stub
		
	}
}