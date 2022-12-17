package com.mygdx.joemac.inimigos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.joemac.consumiveis.Alimentos;
import com.mygdx.joemac.consumiveis.Aneis;
import com.mygdx.joemac.objetos.Ovo;
import com.mygdx.joemac.objetos.Pedra;
import com.mygdx.joemac.personagens.Jogador;

public class CriaInimigos {
	private ArrayList<Alimentos> alimentos = new ArrayList<Alimentos>();
	private int IndexInimigosMortos = 0, IndexInimigosMortosComOvo = 0, IndexOvosQuebrados = 0, IndexInimigosMortosComPedra = 0;
	private ArrayList<Ovo> ovo = new ArrayList<Ovo>();
	private ArrayList<Aneis> aneis = new ArrayList<Aneis>();
	private ArrayList<Inimigos> inimigos = new ArrayList<Inimigos>();
	private TiranossauroRex boss = new TiranossauroRex();
	private ArrayList<Pedra> pedra = new ArrayList<Pedra>();
	private ArrayList<Integer> elementosInimigos = new ArrayList<Integer>(7);
	private int [] posicoesX  = {400,800,1200,1600,2000,2400,2000};
	private int [] posicoesY  = {50,50,300,50,100,200,50};
	private int indexPedra = 0;
	private int armasAneis[] = {2,1,3,3,3,3,3};
	private boolean lutaBoss = false, retornar = false;
	/*
	 * Elementos que os inimigos possuem:
	 * 0: Cont�m apenas comida
	 * 1: Cont�m ovo
	 * 2: Cont�m pedra
	 */
	
	private int pontosGanhos = 0, danos = 0;
	
	/** Responsavel por criar os inimigos que vao aparecer na tela **/
	public void criaInimigos() {
		elementosInimigos.add(0);
		elementosInimigos.add(2);
		elementosInimigos.add(1);
		elementosInimigos.add(1);
		elementosInimigos.add(0);
		elementosInimigos.add(1);
		elementosInimigos.add(1);
		for(int i=0;i<3;i++) {
			inimigos.add(new HomemCavernas());
		}
		
		for(int i=0;i<3;i++) {
			inimigos.add(new Pterodactilo());
		}
		for(int i=0;i<inimigos.size()+1;i++) {
			if(i!= inimigos.size()) {
				inimigos.get(i).cria(posicoesX[i],posicoesY[i]);
				alimentos.add(new Alimentos());		
				ovo.add(new Ovo());
				ovo.get(i).cria();	
				aneis.add(new Aneis(armasAneis[i]));	
			}
			if(elementosInimigos.get(i) == 1) {
				pedra.add(new Pedra());
				pedra.get(indexPedra).cria();
				indexPedra++;
			}
		}
		
		boss.inicializaDinossauro();
		
	}
	
	/** Responsavel por implementar os inimigos, tanto desenhando e movimentando eles **/
	public void implementaInimigos(SpriteBatch batch, Jogador jogador) {
		pontosGanhos = 0; danos = 0;
		indexPedra = 0;
		for(int i=0;i<inimigos.size();i++) { //For responsavel por rodar os inimigos que aparecem no jogo
			if (inimigos.get(i).isMorre() == false) {
				inimigos.get(i).desenha(batch);
				inimigos.get(i).movimenta(jogador);
				if(elementosInimigos.get(i) == 1) {
					ovo.get(i).desenha(batch,inimigos.get(i).getX(),inimigos.get(i).getY());
				}
				if(elementosInimigos.get(i) == 2) {
					pedra.get(indexPedra).desenha(batch,inimigos.get(i).getX(),inimigos.get(i).getY());
				}
			}else {
				IndexInimigosMortos++;
				if(elementosInimigos.get(i) == 1) {
					IndexInimigosMortosComOvo++;
				}
				if(elementosInimigos.get(i) == 2) {
					IndexInimigosMortosComPedra++;
				}
				
				pontosGanhos += 300;
				alimentos.get(0).criarConsumiveis(inimigos.get(i).getX(),50);
				
				if(elementosInimigos.get(i) == 2) {
					pedra.get(indexPedra).setPosicaoMorte(inimigos.get(i).getX(),inimigos.get(i).getY());
				}
				ovo.get(i).setPosicaoMorte(inimigos.get(i).getX(),inimigos.get(i).getY());
				inimigos.remove(i);
				elementosInimigos.remove(i);
			}
		}
		for(int i=0;i<IndexInimigosMortos;i++) { //Roda todos os inimigos que morreram, para aparecer as comidas
			if(!alimentos.get(i).getJogadorPegou()) {
				alimentos.get(i).desenharConsumiveis(batch);
				alimentos.get(i).controlarConsumiveis(jogador.pegarHitBox());
			}else {
				danos = alimentos.get(i).recebeAtributos();
				alimentos.remove(i);
				IndexInimigosMortos--;
				pontosGanhos += 100;
			}
		}
		
		for(int i=0;i<IndexInimigosMortosComOvo;i++) { //Roda todos os inimigos que possuem ovo, para mostrar o ovo quando os que tem morrerem
			
			if(!ovo.get(i).isQuebrou()){
				ovo.get(i).desenha(batch,ovo.get(i).getPosicaoMorteX(),ovo.get(i).getPosicaoMorteY());
				ovo.get(i).movimenta(jogador.getArma(), aneis.get(i));				
			}else {
				ovo.remove(i);
				IndexInimigosMortosComOvo--;
				pontosGanhos += 100;	
				IndexOvosQuebrados ++;
			}
		}
		
		for(int i=0;i<IndexInimigosMortosComPedra;i++) { //Roda todos os inimigos que possuem pedra, para mostrar a pedra quando eles morrerem
			if(!pedra.get(i).isQuebrou()){
				pedra.get(i).desenha(batch,pedra.get(i).getPosicaoMorteX(),pedra.get(i).getPosicaoMorteY());
				pedra.get(i).movimenta(jogador.getArma(),'-');				
			}else {
				pedra.remove(i);
				IndexInimigosMortosComPedra--;
				pontosGanhos += 100;	
			}	
		}
		
		for(int i=0;i<IndexOvosQuebrados;i++) { //Roda todos os ovos quebrados, para mostrar o anel quando eles forem quebrados
			if(!aneis.get(i).getJogadorPegou()){
				aneis.get(i).desenharConsumiveis(batch);
				aneis.get(i).controlarConsumiveis(jogador.pegarHitBox());				
			}else {
				jogador.setArmaSelecionada(aneis.get(i).recebeAtributos());
				aneis.remove(i);
				IndexOvosQuebrados--;
				pontosGanhos += 100;	
			}
		}
		
		if(jogador.getX() >= 2550) {
			boss.cria(posicoesX[6],posicoesY[6]);
			lutaBoss = true;
			pedra.get(pedra.size()-1).setPosicaoMorte(0, 0);
		}
		
		if(lutaBoss) { //Responsavel pela luta do boss, desenhando e movimentando ele
			boss.desenha(batch);
			boss.movimentaBoss(jogador,pedra.get(pedra.size()-1));
			retornar = boss.isMorre();
		}
		
	}
	
	/** 
	 * GETTERS AND SETTERS
	 * **/
	
	public ArrayList<Pedra> pegaPedras() {
		return pedra;
	}
	
	public ArrayList<Inimigos> pegaInimigos(){
		return inimigos;
	}
	
	public int pegaPontosGanhos() {
		return pontosGanhos;
	}
	
	public int pegaDanoRecebido() {
		return danos;
	}

	public boolean isRetornar() {
		return retornar;
	}

	public void setRetornar(boolean retornar) {
		this.retornar = retornar;
	}
	
	
	
}
