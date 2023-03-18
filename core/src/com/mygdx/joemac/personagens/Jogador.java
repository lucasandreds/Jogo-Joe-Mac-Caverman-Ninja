package com.mygdx.joemac.personagens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.joemac.armamentos.Armas;
import com.mygdx.joemac.armamentos.Boomerang;
import com.mygdx.joemac.armamentos.Machado;
import com.mygdx.joemac.armamentos.Osso;
import com.mygdx.joemac.armamentos.Roda;
import com.mygdx.joemac.inimigos.Inimigos;
import com.mygdx.joemac.objetos.Pedra;

public class Jogador {

	/**
	 * Valores constantes utilizados durante o codigo, sendo implementados para
	 * evitar o uso de n�meros magicos e explicar cada um desses dados utilizados no
	 * c�digo, sendo nesse os valores inteiros, tendo a posi��o e quantidade de
	 * frames de anima��es do personagem e alguns elementos importantes de
	 * quantidades de linhas e colunas no SpriteSheet e valores de pressao e pulo
	 **/

	private final int QUANTIDADE_LINHAS = 8, QUANTIDADE_COLUNAS = 16, VELOCIDADE_X = 300, MINIMO_PRESSAO = 8,
			VARIACAO_X_HITBOX = 50, MAXIMO_PRESSIONADO = 250, MINIMO_ATAQUE_FORTE = 80, ALTURA_PULADA_GRANDE = 300,
			ALTURA_PULADA_PEQUENO = 200, MINIMO_Y_INICIAL = 50, VELOCIDADE_Y = 700, TAMANHO_INICIO_PULADA = 50,
			QUANTIDADE_IMAGENS_CAMBALHOTA = 8, VARIACAO_X_BRACO = 36, VARIACAO_Y_BRACO = 28,
			POSICAO_IMAGEM_CAMBALHOTA = 48, QUANTIDADE_IMAGENS_ANDAR = 4, POSICAO_IMAGEM_ANDAR = 34,
			LARGURA_TELA = 3060, ALTURA_TELA = 720, QUANTIDADE_IMAGENS_CANSAR = 4, POSICAO_IMAGEM_CANSACO = 92,
			QUANTIDADE_IMAGENS_BATER_CIMA = 2,
			POSICAO_IMAGEM_BATE_CIMA = 32, QUANTIDADE_IMAGENS_BATER_LADO = 2, POSICAO_IMAGEM_BATE_LADO = 38,
			QUANTIDADE_IMAGENS_BATER_BAIXO = 2, POSICAO_IMAGEM_BATE_BAIXO = 40, QUANTIDADE_IMAGENS_BATER_PULANDO = 2,
			POSICAO_IMAGEM_BATE_PULANDO = 42, QUANTIDADE_IMAGENS_MORRENDO_ATRAS = 8, POSICAO_IMAGEM_MORRE_ATRAS = 56,
			QUANTIDADE_IMAGENS_MORRENDO_FRENTE = 7, POSICAO_IMAGEM_MORRE_FRENTE = 64, QUANTIDADE_IMAGENS_FANTASMA = 4,
			POSICAO_IMAGEM_FANTASMA = 71, QUANTIDADE_IMAGENS_ANDANDO_RODANDO = 2, POSICAO_IMAGEM_ANDANDO_RODANDO = 98,
			QUANTIDADE_IMAGENS_BRACO_RODANDO = 8, POSICAO_IMAGEM_BRACO_RODANDO = 112,
			QUANTIDADE_IMAGENS_BRACO_RAPIDO = 4, POSICAO_IMAGEM_BRACO_RAPIDO = 120, VELOCIDADE_Y_ANJO = 200,
			TEMPO_SPAWN_LANCAMENTO = 200, POSICAO_MORTE_FRENTE = 14, POSICAO_MORTE_TRAS = 15,
			TEMPO_INVENCIBILIDADE_DANO = 3000, TEMPO_ANIMACAO_DANO = 200, DANO_PADRAO = 6, POSICAO_PULO_SEM_BRACO = 96,
			POSICAO_ABAIXADO_SEM_BRACO = 103, POSICAO_OLHANDO_CIMA_SEM_BRACO = 97, POSICAO_PARADO_CIMA_SEM_BRACO = 102,
			POSICAO_ABAIXADO = 2, POSICAO_OLHANDO_CIMA = 1, POSICAO_PARADO = 0, POSICAO_LANCAR_ATAQUE_FORTE = 28,
			POSICAO_PULO_SUBINDO = 3, POSICAO_PULO_BAIXO = 4, POSICAO_PULO_DESCENDO = 5;

	// Valores de variacao de delay utilizados nas animacoes dos personagens, para
	// definir a varia��o de frames de cada uma
	private final double AUMENTA_DELAY_CAMBALHOTA = 0.00000061, AUMENTA_DELAY_ANDAR = 0.00000020,
			AUMENTA_DELAY_FANTASMA = 0.00000020, AUMENTA_DELAY_CANSACO = 0.00000009, AUMENTA_DELAY_GOLPE = 0.00000025,
			AUMENTA_DELAY_BRACO_RODANDO = 0.00000016, AUMENTA_DELAY_MORRENDO = 0.00000009;

	private final float DURACAO_FRAME = 0.000000025f; // Duracao geral de todos os frames de animacao do c�digo

	// Valores de delay de cada anima��o, utilizados para a implementa��o dos delays
	// das animacoes, alem da velocidade X da camera
	private float delayGifcambalhota = 0f, delayGifAndar = 0f, delayGifCansaco = 0f,
			delayGifMorrendo = 0f, delayGifFantasma = 0f, delayGifAndandoBatendo = 0f, delayGifRodandoBraco = 0f,
			delayGifBatendo = 0f, VELOCIDADE_CAMERA = 3f;

	// Valores inteiros modificados durante o codigo, como a posi��o x e y, os
	// valores de pressionaamento, o dano tomado a cada passagem e a altura dos
	// pulos
	private int x = 0, yminimo = MINIMO_Y_INICIAL, y = yminimo, alturaPulo, xinicial = x, yinicial = y,
			pressionando = 0, armaSelecionada = 0, danoTomado = 0, alturaPuloGrande = ALTURA_PULADA_GRANDE + yminimo,
			alturaPuloPequeno = ALTURA_PULADA_PEQUENO + yminimo, tamanhoInicioPulo = TAMANHO_INICIO_PULADA + yminimo;

	/*
	 * Valores utilizados para definir qual e a arma selecionada pelo jogador: 0:
	 * Machado 1: Boomerang 2: Osso 3: Roda
	 */

	private char direcao = '+', direcaoAtual; // Valores que definem a direcao que o jogador esta se movendo, com '+'
												// para virado para a direita, '-' para virado para a esquerda e '.'
												// para virado para cima

	// Variaveis booleanas que definem a a��o do jogo, como pular, bater, usar arma
	// pequena, andar ...
	private boolean pulou, subindo = true, bateu = false, pressionouEsquerda = true, pressionouCima, andando = false,
			tomouDano = false, cansou = false, pressionou = false, armaPequena = true, lancou = false, abaixou = false,
			morreu = false, virouFantasma = false, subiuBloco = false,jogou = false, retornar = false;

	// Valores utilizados para inicializar o tempo que determinadas a��es v�o
	// demorar para acontecer
	private long lancamentospawn, cansacoTempo, tempoDano = 0;

	// Variaveis das anima��es do jogo, sendo que todas as animacoes s�o do tipo
	// TextureRegion
	private Animation<TextureRegion> cambalhota, andar, cansar, bateCima, bateLado, bateBaixo, batePulando,
			morrendoAtras, bracoRapido, morrendoFrente, fantasma, andandoBatendo, rodandoBraco;

	// Variaveis de frames, utilizados para separar o SpriteSheet em diversos frames
	private TextureRegion[] jogadorFrames;
	private TextureRegion frames[][];

	private Texture jogador; // Texture unica da imagem principal do jogador
	private Array<Armas> armas; // Array com as armas que o jogador pode utilizar
	private TextureRegion pulo, ataque, bracoRodando; // Variaveis de Texture utilizado para definir qual frame vai ser
														// passado de determinadas acoes

	private Sound somAtaque, somSuperPulo, somCaindoChao; // Sons utilizados para o personagem

	/**
	 * Funcao responsavel por criar o jogador, separando o seu frame e inicializando
	 * todas os recursos necessarios
	 **/
	public void criarJogador() {
		jogador = new Texture(Gdx.files.internal("desktop/Imagens/joe.png"));
		frames = TextureRegion.split(jogador, jogador.getWidth() / QUANTIDADE_COLUNAS,
				jogador.getHeight() / QUANTIDADE_LINHAS);
		separaFrames();

		criarHitBox(jogador.getHeight() / (QUANTIDADE_LINHAS + (QUANTIDADE_LINHAS / 2)),
				jogador.getWidth() / (QUANTIDADE_COLUNAS * 2), x + VARIACAO_X_HITBOX, y); 
		// Respons�vel por chamar a fun��o da classe pai que vai criar a hitbox do jogador

		armas = new Array<com.mygdx.joemac.armamentos.Armas>();

		inicializaArmas();

		inicializaAnimacoes();

		inicializaMusicas();
	}

	/**
	 * Funcao responsavel por separar o SpriteSheet em diversos frames, para
	 * utiliza-los nas animacoes
	 **/
	private void separaFrames() {
		jogadorFrames = new TextureRegion[QUANTIDADE_LINHAS * QUANTIDADE_COLUNAS];
		int index = 0;
		for (int i = 0; i < QUANTIDADE_LINHAS; i++) {
			for (int j = 0; j < QUANTIDADE_COLUNAS; j++) {
				jogadorFrames[index++] = frames[i][j];
			}
		}
	}

	/** Funcao responsavel por inicializar as animacoes feitas pelo jogador **/
	private void inicializaAnimacoes() {

		cambalhota = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_CAMBALHOTA, POSICAO_IMAGEM_CAMBALHOTA));
		andar = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_ANDAR, POSICAO_IMAGEM_ANDAR));
		cansar = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_CANSAR, POSICAO_IMAGEM_CANSACO));
		bateCima = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_BATER_CIMA, POSICAO_IMAGEM_BATE_CIMA));
		bateLado = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_BATER_LADO, POSICAO_IMAGEM_BATE_LADO));
		bateBaixo = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_BATER_BAIXO, POSICAO_IMAGEM_BATE_BAIXO));
		batePulando = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_BATER_PULANDO, POSICAO_IMAGEM_BATE_PULANDO));
		morrendoAtras = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_MORRENDO_ATRAS, POSICAO_IMAGEM_MORRE_ATRAS));
		morrendoFrente = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_MORRENDO_FRENTE, POSICAO_IMAGEM_MORRE_FRENTE));
		fantasma = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_FANTASMA, POSICAO_IMAGEM_FANTASMA));
		andandoBatendo = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_ANDANDO_RODANDO, POSICAO_IMAGEM_ANDANDO_RODANDO));
		rodandoBraco = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_BRACO_RODANDO, POSICAO_IMAGEM_BRACO_RODANDO));
		bracoRapido = new Animation<TextureRegion>(DURACAO_FRAME,
				pegaSprites(QUANTIDADE_IMAGENS_BRACO_RAPIDO, POSICAO_IMAGEM_BRACO_RAPIDO));
	}

	/** Responsavel por pegar os sprites utilizados para cada animacao **/
	private TextureRegion[] pegaSprites(int tamanho, int posicao) {
		TextureRegion texture[] = new TextureRegion[tamanho];

		for (int i = 0; i < tamanho; i++) {
			texture[i] = jogadorFrames[i + posicao];
		}

		return texture;
	}

	/**
	 * Funcao responsavel por inicializar as armas utilizadas durante o codigo,
	 * introduzindo elas por polimorfismo
	 **/
	private void inicializaArmas() {

		Armas arma = new Machado();
		arma.inicializaPequeno();
		arma.inicializaGrande();
		armas.add(arma);

		arma = new Boomerang();
		arma.inicializaPequeno();
		arma.inicializaGrande();
		armas.add(arma);

		arma = new Osso();
		arma.inicializaPequeno();
		arma.inicializaGrande();
		armas.add(arma);

		arma = new Roda();
		arma.inicializaPequeno();
		arma.inicializaGrande();
		armas.add(arma);

	}

	/** Funcao respons�vel por inicializar os sons feitos pelo jogador **/
	private void inicializaMusicas() {
		somAtaque = Gdx.audio.newSound(Gdx.files.internal("desktop/Sons/JoeAtaque.wav"));
		somSuperPulo = Gdx.audio.newSound(Gdx.files.internal("desktop/Sons/JoeSuperPulo.wav"));
		somCaindoChao = Gdx.audio.newSound(Gdx.files.internal("desktop/Sons/JoeCaindoChao.wav"));
	}

	/** Funcao responsavel por desenhar **/
	public void desenhaJogador(SpriteBatch batch) {
		TextureRegion jogadorAtual = definirFrameMostrado();

		batch.begin();
		batch.draw(jogadorAtual, x, y, jogador.getWidth() / QUANTIDADE_COLUNAS,
				jogador.getHeight() / QUANTIDADE_LINHAS);

		// Define as variacao de X e Y do Braco dependendo da situacao atual do jogador,
		// como por exemplo uma diferenca quando ele esta andando
		if (pressionou == true && alturaPulo != alturaPuloGrande) {
			int bracoX = x - VARIACAO_X_BRACO, bracoY = y - VARIACAO_Y_BRACO;

			if (!pressionouEsquerda) {
				bracoX += (VARIACAO_X_BRACO * 2);

				if (andando) {
					bracoX -= (VARIACAO_Y_BRACO / 2);
				}

			}
			if (andando) {
				bracoX += (VARIACAO_X_BRACO / 5);
				bracoY -= (VARIACAO_Y_BRACO / 10);
			} else if (abaixou) {
				bracoY -= (jogador.getHeight() / ((QUANTIDADE_LINHAS * 6)));
			}
			batch.draw(bracoRodando, bracoX, bracoY, jogador.getWidth() / QUANTIDADE_COLUNAS,
					jogador.getHeight() / QUANTIDADE_LINHAS);
		}

		batch.end();

		// Responsavel por definir que, se o jogador abaixar, a sua hitbox vai diminuir
		if (Gdx.input.isKeyPressed(Keys.S) && !pulou) {
			setarAltura(jogador.getHeight() / (QUANTIDADE_LINHAS * 2));
		} else {
			setarAltura(jogador.getHeight() / (QUANTIDADE_LINHAS + (QUANTIDADE_LINHAS / 2)));
		}
	}

	/** Funcao respons�vel por selecionar qual vai ser o sprite mostrado na tela **/
	private TextureRegion definirFrameMostrado() {
		if (morreu) { // Selecionado quando o jogador morrer

			if (!virouFantasma) { // Definido quando o jogador tiver no inicio da morte
				delayGifMorrendo += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_MORRENDO;
				if (pressionouEsquerda) { // Morte para a esquerda
					if ((morrendoAtras.getKeyFrameIndex(delayGifMorrendo) == (QUANTIDADE_IMAGENS_MORRENDO_ATRAS - 1))) {
						virouFantasma = true;
					}
					return morrendoAtras.getKeyFrame(delayGifMorrendo, true);
				} else { // Morte para a direita
					if ((morrendoFrente
							.getKeyFrameIndex(delayGifMorrendo) == (QUANTIDADE_IMAGENS_MORRENDO_FRENTE - 1))) {
						virouFantasma = true;
					}
					return morrendoFrente.getKeyFrame(delayGifMorrendo, true);
				}
			} else { // Definido quando ele ja tiver virado fantasma

				delayGifFantasma += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_FANTASMA;
				y += VELOCIDADE_Y_ANJO * Gdx.graphics.getDeltaTime();

				if (y >= (ALTURA_TELA - jogador.getHeight() / QUANTIDADE_LINHAS)) {
					morreu = false;
					retornar = true;
					y = yminimo;
				}
				return fantasma.getKeyFrame(delayGifFantasma, true);
			}

		} else if (cansou) { // Selecionado quando o jogador cansar
			return cansar.getKeyFrame(delayGifCansaco, true);
		} else if (tomouDano) { // Selecionado quando toma dano
			abaixou = false;
			pressionouCima = false;
			if (TimeUtils.millis() - tempoDano > TEMPO_ANIMACAO_DANO) {
				tomouDano = false;
			}
			if (pressionouEsquerda) {
				return jogadorFrames[POSICAO_MORTE_FRENTE];
			} else {
				return jogadorFrames[POSICAO_MORTE_TRAS];
			}

		} else if (pressionou && (alturaPulo != alturaPuloGrande)) { // Selecionado quando o jogador pressiona o ataque
																		// especial
			pressionouCima = false;
			if (pulou && (alturaPulo != alturaPuloGrande)) { // Pressionando e pulando
				abaixou = false;
				return jogadorFrames[POSICAO_PULO_SEM_BRACO];
			} else if (Gdx.input.isKeyPressed(Keys.S) && !pulou) { // Pressionando e abaixando
				abaixou = true;
				return jogadorFrames[POSICAO_ABAIXADO_SEM_BRACO];
			} else if (Gdx.input.isKeyPressed(Keys.W) && !pulou) { // Pressionando e levantando a cabeca
				abaixou = false;
				return jogadorFrames[POSICAO_OLHANDO_CIMA_SEM_BRACO];
			} else if (andando && !pulou) { // Pressionando e andando
				abaixou = false;
				return andandoBatendo.getKeyFrame(delayGifAndandoBatendo, true);
			} else if (!pulou) { // Pressionando e parado
				abaixou = false;
				return jogadorFrames[POSICAO_PARADO_CIMA_SEM_BRACO];
			}
		}

		else if (lancou && alturaPulo != alturaPuloGrande) { // Selecionando quando o jogador lanca uma arma
			if (TimeUtils.millis() - lancamentospawn > TEMPO_SPAWN_LANCAMENTO) {
				lancou = false;
			}
			animacaoAtaque();
			return ataque;

		} else if (pulou) { // Selecionando quando o jogador pula
			abaixou = false;
			pressionouCima = false;
			return pulo;
		} else if (andando) { // Selecionado quando o jogador anda
			pressionouCima = false;
			abaixou = false;
			return andar.getKeyFrame(delayGifAndar, true);
		} else if (Gdx.input.isKeyPressed(Keys.S) && pulou == false) { // Selecionado quando o jogador abaixa
			abaixou = true;
			return jogadorFrames[POSICAO_ABAIXADO];
		} else if (Gdx.input.isKeyPressed(Keys.W) && pulou == false) { // Selecionado quando o jogador levanta a cabeca
			pressionouCima = true;
			return jogadorFrames[POSICAO_OLHANDO_CIMA];
		} else { // Selecionado quando o jogador est� parado
			pressionouCima = false;
			abaixou = false;
			return jogadorFrames[POSICAO_PARADO];
		}
		return null;
	}

	/**
	 * Funcao responsavel por movimentado o jogador para a direita e para a esquerda
	 **/
	public void movimentaJogador(OrthographicCamera camera, int POSICAO_INICIAL_CAMERA, ArrayList<Inimigos> inimigos,
			ArrayList<Pedra> pedra) {

		if (Gdx.input.isKeyPressed(Keys.A) && cansou != true && morreu != true) { // Andou para tr�s
			x -= VELOCIDADE_X * Gdx.graphics.getDeltaTime();
			if (pressionouEsquerda) {
				direcao = '-';
				invertePersonagem();
				pressionouEsquerda = false;
			}
			delayGifAndar += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_ANDAR; // Aplicando o delay de passagem de
																				// frames
			andando = true;
		} else if (Gdx.input.isKeyPressed(Keys.D) && cansou != true && morreu != true) { // Andou para frente
			x += VELOCIDADE_X * Gdx.graphics.getDeltaTime();
			if (pressionouEsquerda == false) {
				direcao = '+';
				invertePersonagem();
				pressionouEsquerda = true;
			}
			delayGifAndar += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_ANDAR; // Aplicando o delay de passagem de
																				// frames
			andando = true;
		} else {
			andando = false;

		}
		verificaContato(inimigos, pedra);
		limiteborda(camera, POSICAO_INICIAL_CAMERA);
	}

	/**
	 * Funcao responsavel por verificar se o jogador encostou em algo, dando dano se
	 * tiver encostado
	 **/
	private void verificaContato(ArrayList<Inimigos> inimigos, ArrayList<Pedra> pedra) {
		if (TimeUtils.millis() - tempoDano > TEMPO_INVENCIBILIDADE_DANO) {
			for (int i = 0; i < inimigos.size(); i++) {
				if (pegarHitBox().overlaps(inimigos.get(i).pegarHitBox())) {
					danoTomado = DANO_PADRAO;
					tempoDano = TimeUtils.millis();
					tomouDano = true;
				}
			}
			for (int i = 0; i < pedra.size(); i++) {
				if (pegarHitBox().overlaps(pedra.get(i).pegarHitBox())) {
					danoTomado = DANO_PADRAO;
					tempoDano = TimeUtils.millis();
					tomouDano = true;
				}
			}
		}
	}

	/** Funcao responsavel por inverter os sprites quando o jogador virasse **/
	private void invertePersonagem() {
		for (int i = 0; i < jogadorFrames.length; i++) {
			jogadorFrames[i].flip(true, false);
		}
	}

	/**
	 * Funcao responsavel por realizar os ataques do jogador, sendo o normal e o
	 * pressionado
	 **/
	public void ataqueJogador(SpriteBatch batch) {
		if (Gdx.input.isKeyPressed(Keys.I) && bateu != true && morreu != true) { // Ataque basico
			xinicial = x;
			if (!abaixou) {
				yinicial = y;
			} else {
				yinicial = y - (jogador.getHeight() / ((QUANTIDADE_LINHAS * 4)));
			}

			if (!pressionouCima)
				direcaoAtual = direcao;
			else
				direcaoAtual = '.';

			armas.get(armaSelecionada).criaPequeno(xinicial, yinicial); // Cria uma arma pequena
			jogou = true;
			bateu = true;

			armaPequena = true;
			lancou = true;
			lancamentospawn = TimeUtils.millis();
			somAtaque.play();
		}
		if (Gdx.input.isKeyPressed(Keys.K) && bateu != true && morreu != true) { //
			/* Ataque com possibilidade de presisonado */
			pressionando++;
			if (pressionando > MINIMO_PRESSAO) {
				pressionou = true;
				delayGifRodandoBraco += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_BRACO_RODANDO;
				delayGifAndandoBatendo += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_ANDAR;
			}
			if (pressionando > MAXIMO_PRESSIONADO) {
				cansacoTempo = TimeUtils.millis();
				cansou = true;
			}
			if (!pressionouCima)
				direcaoAtual = direcao;
			else
				direcaoAtual = '.';

		} else if (pressionando >= 1 && !cansou) { // Define que o jogador pressionou
			xinicial = x;
			if (!abaixou) {
				yinicial = y;
			} else {
				yinicial = y - (jogador.getHeight() / ((QUANTIDADE_LINHAS * 4)));
			}
			if (pressionando <= MAXIMO_PRESSIONADO) {
				somAtaque.play();
				bateu = true;
				if ((pressionando >= MINIMO_ATAQUE_FORTE)) {
					direcaoAtual = direcao;
					armaPequena = false;
					lancamentospawn = TimeUtils.millis();
					lancou = true;
					jogou = true;
					armas.get(armaSelecionada).criaGrande(xinicial, yinicial); // Cria uma arma grande
				} else if (pressionando <= MINIMO_PRESSAO) {

					armaPequena = true;
					lancamentospawn = TimeUtils.millis();
					lancou = true;
					jogou = true;
					armas.get(armaSelecionada).criaPequeno(xinicial, yinicial); // Cria uma arma pequena
				} else {
					bateu = false;
					pressionou = false;
					armaPequena = true;
					lancamentospawn = TimeUtils.millis();
					somAtaque.stop();
					pressionando = 0;
				}
			}
		}
		condicoesBater(batch);
	}

	/**
	 * Funcao responsavel por realizar as condicoes para bater, mudando o
	 * pressionando, o cansaco e o desenho e movimentacao das armas
	 **/
	private void condicoesBater(SpriteBatch batch) {
		if (!pressionou) {
			delayGifRodandoBraco = 0f;
		} else { // Pressionou
			if ((rodandoBraco.getKeyFrameIndex(delayGifRodandoBraco) != (QUANTIDADE_IMAGENS_BRACO_RODANDO - 1))) {
				bracoRodando = rodandoBraco.getKeyFrame(delayGifRodandoBraco, true);
			} else {
				bracoRodando = bracoRapido.getKeyFrame(delayGifRodandoBraco, true);
			}
		}

		if (cansou) { // Cansou
			bateu = false;
			pressionou = false;
			delayGifCansaco += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_CANSACO;

			if (TimeUtils.millis() - cansacoTempo > TEMPO_INVENCIBILIDADE_DANO) {
				cansar.getKeyFrame(delayGifCansaco, false);
				cansou = false;
				pressionando = 0;
				delayGifCansaco = 0f;
				danoTomado += DANO_PADRAO;
			}
		}

		if (bateu) { // Bateu com alguma arma
			pressionou = false;
			armas.get(armaSelecionada).desenha(direcaoAtual, batch);
			delayGifBatendo += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_GOLPE;
			bateu = armas.get(armaSelecionada).movimenta();
			pressionando = 0;
		} else {
			delayGifBatendo = 0f;
		}

		if (pressionouEsquerda)
			setarPosicao(x + VARIACAO_X_HITBOX, y);
		else
			setarPosicao(x + (VARIACAO_X_HITBOX + (VARIACAO_X_HITBOX / 2)), y);
	}

	/** Funcao respons�vel por criar a animacao de bater **/
	private void animacaoAtaque() {
		if (armaPequena) {
			if ((pulou == true) && (alturaPulo != alturaPuloGrande)) {
				pressionouCima = false;
				ataque = batePulando.getKeyFrame(delayGifBatendo, true);
			} else if (Gdx.input.isKeyPressed(Keys.S) && !pulou) {
				pressionouCima = false;
				ataque = bateBaixo.getKeyFrame(delayGifBatendo, true);
			} else if (Gdx.input.isKeyPressed(Keys.W) && !pulou) {
				ataque = bateCima.getKeyFrame(delayGifBatendo, true);
			} else if (!pulou) {
				pressionouCima = false;
				ataque = bateLado.getKeyFrame(delayGifBatendo, true);
			}
		} else {
			if ((pulou == true) && (alturaPulo != alturaPuloGrande)) {
				pressionouCima = false;
				ataque = batePulando.getKeyFrame(delayGifBatendo, true);
			} else if (Gdx.input.isKeyPressed(Keys.W) && !pulou) {
				ataque = jogadorFrames[POSICAO_LANCAR_ATAQUE_FORTE];
			} else {
				pressionouCima = false;
				ataque = jogadorFrames[POSICAO_LANCAR_ATAQUE_FORTE];
			}
		}
	}

	/**
	 * Funcao responsavel por realizar o pulo jogador, sendo o pulo b�sico e o pulo
	 * alto
	 **/
	public void pulaJogador(Rectangle blocoMorro, Rectangle blocoDinossauro) {
		if (Gdx.input.isKeyPressed(Keys.J) && pulou == false && cansou != true && morreu != true) { // Pulo basico
			pulou = true;
			alturaPulo = alturaPuloPequeno;
		} else if (Gdx.input.isKeyPressed(Keys.L) && pulou == false && cansou != true && morreu != true) { // Pulo alto
			pulou = true;

			if (!pressionou)
				alturaPulo = alturaPuloGrande;
			else
				alturaPulo = alturaPuloPequeno;
		} else if (pulou == false) {
			alturaPulo = 0;
		}

		/* Define qual bloco que podera ser "subido" */
		if (pegarHitBox().x <= 2700) {
			processoPulo(blocoDinossauro, 1);

		} else {
			processoPulo(blocoMorro, 2);
		}
	}

	/**
	 * Funcao responsavel por realizar o movimento do pulo, com o movimento de
	 * subida e descida
	 **/
	private void processoPulo(Rectangle bloco, int identificador) {
		if (pulou == true) {
			if (subindo) { // Chamada enquanto o jogador estive subindo no pulo
				y += VELOCIDADE_Y * Gdx.graphics.getDeltaTime();

				if (alturaPulo == alturaPuloPequeno) {
					if (y >= tamanhoInicioPulo) {
						pulo = jogadorFrames[POSICAO_PULO_BAIXO];

					} else {
						pulo = jogadorFrames[POSICAO_PULO_SUBINDO];
					}
				}

				if (alturaPulo == alturaPuloGrande) {
					somSuperPulo.play();
					if (y >= tamanhoInicioPulo + (alturaPuloPequeno / 2)) {
						/* Aplicando o delay de passagem de frames */
						delayGifcambalhota += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_CAMBALHOTA;
						pulo = cambalhota.getKeyFrame(delayGifcambalhota, true);

					} else {
						pulo = jogadorFrames[POSICAO_PULO_SUBINDO];
					}
				}

				if (y >= alturaPulo) {
					subindo = false;
				}

			} else { // Chamada quando o jogador estiver descendo no pulo
				y -= VELOCIDADE_Y * Gdx.graphics.getDeltaTime();
				if (alturaPulo == alturaPuloPequeno) {
					if (y <= tamanhoInicioPulo) {
						pulo = jogadorFrames[POSICAO_PULO_DESCENDO];
					} else {
						pulo = jogadorFrames[POSICAO_PULO_BAIXO];
					}
				}

				if (alturaPulo == alturaPuloGrande) {
					if (y <= tamanhoInicioPulo + (alturaPuloPequeno / 2)) {
						pulo = jogadorFrames[POSICAO_PULO_DESCENDO];

					} else {
						/* Aplicando o delay de passagem de frames */
						delayGifcambalhota += Gdx.graphics.getDeltaTime() * AUMENTA_DELAY_CAMBALHOTA;
						pulo = cambalhota.getKeyFrame(delayGifcambalhota, true);
					}
				}
				if (y <= yminimo) {
					subindo = true;
					pulou = false;
					subiuBloco = false;
					somCaindoChao.play();
				}
			}

			if (bloco.overlaps(pegarHitBox())) { // Responsavel por verificar se no caminho o jogador pisou no bloco
				if (y >= bloco.y + bloco.height) {
					y = (int) (bloco.y + bloco.height);
					yminimo = y;
					pulou = false;
					subiuBloco = true;
					subindo = true;

					if (alturaPulo == alturaPuloGrande) {
						alturaPuloGrande = yminimo + ALTURA_PULADA_GRANDE;
						alturaPulo = alturaPuloGrande;

					} else {
						alturaPuloPequeno = yminimo + ALTURA_PULADA_PEQUENO;
						alturaPulo = alturaPuloPequeno;
					}
				}
			}

		} else {
			delayGifcambalhota = 0f;
		}

		/* Controlando o movimento em cima do bloco de acordo com o retangulo */
		switch (identificador) {
		case 1: // Caso se refira a hitbox do dinossauro
			if ((1235 <= x && subiuBloco == true) || (760 > x && subiuBloco == true)) {
				/*
				 * Se o jogador estiver numa posicao maior ou menor que onde a de uma das
				 * extremidades da hitbox os valores do pulo e da coordenada Y do jogador serao
				 * redefinidos
				 */
				yminimo = MINIMO_Y_INICIAL;
				alturaPuloGrande = yminimo + ALTURA_PULADA_GRANDE;
				alturaPuloPequeno = yminimo + ALTURA_PULADA_PEQUENO;
				tamanhoInicioPulo = TAMANHO_INICIO_PULADA + yminimo;

				if (alturaPulo == alturaPuloGrande) {
					yminimo = MINIMO_Y_INICIAL;
					alturaPuloGrande = yminimo + ALTURA_PULADA_GRANDE;
					alturaPulo = alturaPuloGrande;
				} else {
					alturaPuloPequeno = yminimo + ALTURA_PULADA_PEQUENO;
					alturaPulo = alturaPuloPequeno;
				}
				subindo = false;
				pulou = true;
			}
			break;

		case 2: // Caso se refira a hitbox do morro
			/*
			 * Se o jogador estiver numa posicao maior ou menor que onde a de uma das
			 * extremidades da hitbox os valores do pulo e da coordenada Y do jogador serao
			 * redefinidos
			 */
			if (2400 > x && subiuBloco == true) {
				yminimo = MINIMO_Y_INICIAL;
				alturaPuloGrande = yminimo + ALTURA_PULADA_GRANDE;
				alturaPuloPequeno = yminimo + ALTURA_PULADA_PEQUENO;
				tamanhoInicioPulo = TAMANHO_INICIO_PULADA + yminimo;

				if (alturaPulo == alturaPuloGrande) {
					yminimo = MINIMO_Y_INICIAL;
					alturaPuloGrande = yminimo + ALTURA_PULADA_GRANDE;
					alturaPulo = alturaPuloGrande;
				} else {
					alturaPuloPequeno = yminimo + ALTURA_PULADA_PEQUENO;
					alturaPulo = alturaPuloPequeno;
				}

				subindo = false;
				pulou = true;
			}
			break;
		}
	}

	/** Funcao responsavel por definir o limite da camera e do jogador na tela **/
	private void limiteborda(OrthographicCamera camera, int POSICAO_INICIAL_CAMERA) {
		if (x < camera.position.x - POSICAO_INICIAL_CAMERA) {
			x = (int) (camera.position.x - POSICAO_INICIAL_CAMERA);
			camera.translate(0f, 0f);
		} else if (x > LARGURA_TELA - jogador.getWidth() / (QUANTIDADE_COLUNAS * 2)) {
			x = LARGURA_TELA - jogador.getWidth() / (QUANTIDADE_COLUNAS * 2);
			camera.translate(0f, 0f);
		} else if (y < 0) {
			y = 0;
			camera.translate(0f, 0f);
		} else if (andando) {
			if (pressionouEsquerda && (camera.position.x + POSICAO_INICIAL_CAMERA) <= LARGURA_TELA)
				camera.translate(VELOCIDADE_CAMERA, 0f);
		}

		if (pressionouEsquerda)
			setarPosicao(x + VARIACAO_X_HITBOX, y);
		else
			setarPosicao(x + (VARIACAO_X_HITBOX + (VARIACAO_X_HITBOX / 2)), y);
	}

	/**
	 * Getters e Setters
	 **/
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDanoTomado() {
		return danoTomado;
	}

	public void setDanoTomado(int danoTomado) {
		this.danoTomado = danoTomado;
	}

	public boolean isMorreu() {
		return morreu;
	}

	public void setMorreu(boolean morreu) {
		this.morreu = morreu;
	}

	public Rectangle getArma() {
		return armas.get(armaSelecionada).pegarHitBox();
	}

	public void setArmaSelecionada(int armaSelecionada) {
		this.armaSelecionada = armaSelecionada;
	}

	public boolean isJogou() {
		return jogou;
	}

	public void setJogou(boolean jogou) {
		this.jogou = jogou;
	}

	public void setBateu(boolean bateu) {
		this.bateu = bateu;
	}
	
	public boolean isRetornar() {
		return retornar;
	}

	public void setRetornar(boolean retornar) {
		this.retornar = retornar;
	}

	/**
	 * Dispose
	 **/

	public void dispose() {
		jogador.dispose();
	
		for(Armas arma : armas) {
			arma.dispose();
		}

		somAtaque.dispose();
		somCaindoChao.dispose();
		somSuperPulo.dispose();

	}
	/**
	 *  HitBox
	 **/
		private Rectangle Hitbox;
		public void criarHitBox(int tamanho, int largura, int x, int y) {
			Hitbox = new Rectangle();
			Hitbox.x = x; 
			Hitbox.y = y; 
			Hitbox.width = largura;
			Hitbox.height = tamanho; 
		}
		public void setarPosicao(int X, int Y) {
			Hitbox.x = X; 
			Hitbox.y = Y;
			
		}

		public Rectangle pegarHitBox() {
			return Hitbox;
			
		}
		
		public void setarAltura(int tamanho) {
			Hitbox.height = tamanho; 
		}	

}