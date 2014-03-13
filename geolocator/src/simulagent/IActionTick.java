package simulagent;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
	void drawImage(BufferedImage image,Color color);
	/**
	 * Movimenta os parametros do modelo para efetuar uma movimentação
	 */
	void moveImage();

}
