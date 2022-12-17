package com.mygdx.joemac.joeMacCavermanNinja;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.joemac.atributosJogador.ConectaAtributos;
import com.mygdx.joemac.cenario.Cenario;
import com.mygdx.joemac.cenario.Dinossauro;
import com.mygdx.joemac.cenario.Fundo;
import com.mygdx.joemac.cenario.Morro;
import com.mygdx.joemac.inimigos.CriaInimigos;
import com.mygdx.joemac.personagens.Jogador;

public class Jogo implements Screen {
	final JoeMac game;

	private final int POSICAO_INICIAL_CAMERA = 640;

	private ArrayList<Cenario> cenarios = new ArrayList<Cenario>();
	private CriaInimigos inimigos;
	private ConectaAtributos atributos;
	private Jogador jogador;
	private Music musicaFundo;
	private OrthographicCamera camera;

	private int danos = 0, pontosGanhos = 0;

	/*
	 * Atualiza-se os inteiros para coloca-los no menu de atributos, mas coloca-se o
	 * dano sofrido pelo personagem na variavel 1, os pontos ganhos por consumives +
	 * homemCavernas mortos na 2 e quando ele morrer somar um no 3;
	 */

	/**
	 * Metodo que cria os elementos do jgofo
	 **/
	public Jogo(final JoeMac gam) {
		this.game = gam;
		jogador = new Jogador();
		atributos = new ConectaAtributos();
		inimigos = new CriaInimigos();

		/* Camera */
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.translate(0, 0);
		Gdx.graphics.setContinuousRendering(true);
		Gdx.graphics.requestRendering();

		/* Musica de fundo */
		musicaFundo = Gdx.audio.newMusic(Gdx.files.internal("Sons/BackgroundMusic.mp3"));
		musicaFundo.setLooping(true);
		musicaFundo.play();

		/* Mobs */
		jogador.criarJogador();
		atributos.criaAtributos();
		inimigos.criaInimigos();

		/* Cenario */
		cenarios.add(new Fundo());
		cenarios.add(new Morro());
		cenarios.add(new Dinossauro());

		for (int i = 0; i < cenarios.size(); i++) {
			cenarios.get(i).criaCenario();
		}

	}

	/**
	 * Metodo de renderizacao
	 **/

	@Override
	public void render(float delta) {
		/* Define a cor do fundo */
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		danos = 0;
		pontosGanhos = 0;

		game.getBatch().setProjectionMatrix(camera.combined); // Define o tipo da matriz de projecao

		camera.update(); // Atualiza a camera
		for (int i = 0; i < cenarios.size(); i++) {
			cenarios.get(i).desenhaCenario(game.getBatch());
		}

		/* Chama as funcoes para desenhar e movimentar/controlar o jogador */
		jogador.desenhaJogador(game.getBatch());
		jogador.movimentaJogador(camera, POSICAO_INICIAL_CAMERA, inimigos.pegaInimigos(), inimigos.pegaPedras());
		jogador.ataqueJogador(game.getBatch());
		jogador.pulaJogador(((Morro) cenarios.get(1)).pegarHitBox(), ((Dinossauro) cenarios.get(2)).pegarHitBox());

		inimigos.implementaInimigos(game.getBatch(), jogador); // Implementa os inimigos e suas acoes
		
		/*
		 * Atualiza as variaveis inteiras, status de dano (HP) do jogador verifica se
		 * ele continua vivo
		 */
		danos += inimigos.pegaDanoRecebido();
		pontosGanhos += inimigos.pegaPontosGanhos();

		atributos.setPerdeuVidas(jogador.isMorreu());

		danos += jogador.getDanoTomado();
		jogador.setDanoTomado(0);

		/* Desenha os atributos e recebe seus valores */
		atributos.desenhaAtributos(game.getBatch(), (int) camera.position.x, POSICAO_INICIAL_CAMERA);
		atributos.recebeAtributos(danos, pontosGanhos);

		jogador.setMorreu(atributos.isPerdeuVidas()); // Define se o jogador morreu
		
		/* Caso o jogador tenha morrido ou vencido a fase, o jogo volta para a tela de menu */
		if (jogador.isRetornar() || inimigos.isRetornar()) { //Substituir isso pelo metodo de saber que morreu | ganhou a fase
			jogador.setRetornar(false);
			inimigos.setRetornar(false);
            game.setScreen(new Menu(game));
            dispose(); // Chama o metodo de dispose
        } 
	}

	/**
	 * Metodo de dispose
	 **/
	@Override
	public void dispose() {
		jogador.dispose();
		atributos.dispose();
		musicaFundo.dispose();
		
		for(int i = 0; i < inimigos.pegaInimigos().size(); i++) {
			inimigos.pegaInimigos().get(i).dispose();
		}
	}

	/**
	 * Metodo de resize
	 **/
	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = 720;
		camera.viewportWidth = 1280;
	}

	@Override
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}