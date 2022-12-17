package com.mygdx.joemac.joeMacCavermanNinja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.joemac.cenario.FundoMenu;

public class Menu implements Screen{
	
	final JoeMac game;
	OrthographicCamera camera;
	FundoMenu menu;
	
	/** Responsavel por pegar e inicializar os valores utilizados para mostrar o menu**/
	public Menu(final JoeMac gam) {
		game = gam;
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false);
        menu = new FundoMenu();
        menu.criaCenario();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	/** Responsavel por renderizar a tela de Menu e chamar as suas funcoes**/
	public void render(float delta) {
		// TODO Auto-generated method stub
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        
        menu.desenhaCenario(game.getBatch());
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "PRESS START ", 550, 200);
        game.getFont().draw(game.getBatch(), "PRESSIONE ENTER PARA INICIAR", 300, 150);
        game.getBatch().end();

        if (Gdx.input.isKeyPressed(Keys.ENTER )) {
            game.setScreen(new Jogo(game));
            dispose();
        }       
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
