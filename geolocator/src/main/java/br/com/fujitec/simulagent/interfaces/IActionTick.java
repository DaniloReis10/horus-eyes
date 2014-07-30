package br.com.fujitec.simulagent.interfaces;

import java.awt.Graphics;

/**
 * Interface de animaçao de objetos dentro do objeto JAnimationPanel
 * @author DaniloReis
 *
 */
public interface IActionTick {
	/**
	 * Desenha o objeto que se deseja animar na imagem
	 * @param image imagem a ser desenhada
	 * @param color cor do objeto a ser desenhado
	 */
	void draw(Graphics graphics);

	/**
	 * Movimenta os parametros do modelo para efetuar uma movimentação
	 */
	void move();
}
