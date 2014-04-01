package simulagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Interface de animaçao de objetos dentro do objeto JAnimationPanel
 * @author DaniloReis
 *
 */
public interface IActionTick {
	/**
	 * Apaga na imagem o objeto que se deseja animar
	 * @param image - imagem que sera apagada
	 */
	void clearImage(BufferedImage image);
	/**
	 * Desenha o objeto que se deseja animar na imagem
	 * @param image imagem a ser desenhada
	 * @param color cor do objeto a ser desenhado
	 */
	void drawImage(Graphics graphics,Color color);
	/**
	 * Movimenta os parametros do modelo para efetuar uma movimentação
	 */
	void moveImage();

}
