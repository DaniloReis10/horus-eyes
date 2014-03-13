package simulagent;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface IActionTick {
	void clearImage(BufferedImage image);
	void drawImage(BufferedImage image,Color color);
	void moveImage();

}
