package simulagent;

//Fig. 21.2: LogoAnimatorJPanel.java
//Animação de uma série de imagens.
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import location.geoengine.GeoPosition;
import trilaceration.ElectronicFence;
import trilaceration.LocationArea;
import trilaceration.ScaleConverter;
import trilaceration.SensorPosition;
/**
 * Classe filha do JPanel do swing que permite chamar a cada 50 ms a interface IActionTicket
 * que desenha e faz a animação de um objeto geerico dentro de uma imagem gerada neste objeto
 * 
 * @author DaniloReis
 *
 */
public class JAnimationPanel extends JPanel 
{
	private final static String IMAGE_NAME = "deitel"; // nome básico de imagem
	protected ImageIcon                      images[]; // array de imagens
	private final int                TOTAL_IMAGES = 2; // número de imagens
	private int                      currentImage = 0; // índice de imagem atual
	private int                        paintImage = 0; // índice de imagem atual
	private final int            ANIMATION_DELAY = 50; // retardo em milissegundos 
	private int                                 width; // largura da imagem
	private int                                height; // altura da imagem
	private BufferedImage                      image0; // Imagem utilizada para animacao
	private BufferedImage                      image1; // imagem utilizada para animacao
	private Timer                      animationTimer; // O timer guia a animação
	private IActionTick                    actionTick; // interface de animaçao do objeto

	
	/**
	 * construtor inicializa LogoAnimatorJPanel definindo o tamanho da imagem e interface da
	 * animaçao do objeto ou coleçao deste
	 * @param width largura da imagem em pixels
	 * @param height altura da imagem em pixels
	 * @param actionTick interface de animaçao do objeto
	 */
	public JAnimationPanel(int width,int height,IActionTick actionTick)
	{
		Graphics  g;
		
		this.width      = width;
		this.height     = height;
		this.actionTick = actionTick;
		
		image0 = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
		image1 = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
		
		// Limpa imagens
		g = image0.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		g = image1.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		
		images = new ImageIcon[ TOTAL_IMAGES ];
	
	    images[ 0 ] = new ImageIcon( image0);         
	    images[ 1 ] = new ImageIcon( image1);         
	
	} // fim do construtor LogoAnimatorJPanel
	
	/**
	 * Desenha o componente acrescentando as chamadas da animaçao
	 */
	public void paintComponent( Graphics g )
	{
	   actionTick.clearImage(this.getPaintImage());
	   actionTick.moveImage();
	   actionTick.drawImage(this.getDrawImage(),Color.CYAN);
	   //this.getGraphics().drawImage(this.getDrawImage(), 0, 0, this);
	   this.updateImage();
	   
	   super.paintComponent( g ); // chama a superclasse paintComponent
	
	   images[ currentImage ].paintIcon( this, g, 0, 0 );
	   
	   // configura a próxima imagem a ser desenhada apenas se o timer estiver executando
	   if (animationTimer.isRunning()){  
	      currentImage = ( currentImage + 1 ) % TOTAL_IMAGES;
	      paintImage   = ( currentImage + 1 ) % TOTAL_IMAGES;
	   }
	} // fim do método paintComponent
	
	/**
	 * inicia a animação ou reinicia se a janela for reexibida
	 */
	public void startAnimation()
	{
	   if ( animationTimer == null ) 
	   {
	      currentImage = 0; // exibe a primeira imagem
	      paintImage   = 1;
	      // cria o timer                                     
	      animationTimer = new Timer( ANIMATION_DELAY, new TimerHandler() );
	
	      animationTimer.start(); // inicia o timer
	   } // fim do if
	   else // animationTimer já existe, reinicia animação
	   {
	      if ( ! animationTimer.isRunning())
	         animationTimer.restart();
	   } // fim de else
	} // fim do método startAnimation 
	
	/**
	 * para o timer de animação
	 */
	public void stopAnimation()
	{
	   animationTimer.stop();
	} // fim do método stopAnimation 
	
	/**
	 *  retorna o tamanho mínimo de animação
	 */
	public Dimension getMinimumSize()  
	{                                  
	   return getPreferredSize();      
	} // fim do método getMinimumSize     
	
	/**
	 * retorna tamanho preferido da animação    
	 */
	public Dimension getPreferredSize()      
	{                                        
	   return new Dimension( width, height );
	} // fim do método getPreferredSize         
	/**
	 * Pega a imagem corrente que deverá ser mostrada no panel, isto é, feito para minimizar efeito de cintilamento
	 * a imagem que é desenhada não é mostrada so mostrando uma imagem completamente desenhada
	 * @return imagem corrente
	 */
	private BufferedImage getDrawImage(){
		switch(currentImage){
		case 0:
			return image0;
		case 1:
			return image1;
		default:
			return null;
		}
	}
	/**
	 * Retorna a imagem que sera desenhada
	 * @return imagem a ser desenhada
	 */
	private BufferedImage getPaintImage(){
		switch(paintImage){
		case 0:
			return image0;
		case 1:
			return image1;
		default:
			return null;
		}
	}	
	/**
	 * Atualiza no array de imagens as novas imagem na forma de icon
	 */
	private void updateImage(){
	    images[ 0 ] = new ImageIcon( image0);         
	    images[ 1 ] = new ImageIcon( image1);         
	}
	
	/**
	 *  classe interna para tratar eventos de ação do Timer chamada a cada 50 ms
	 * @author DaniloReis
	 *
	 */
	private class TimerHandler implements ActionListener 
	{
	   // responde ao evento do Timer
	   public void actionPerformed( ActionEvent actionEvent )
	   {
	      repaint(); // pinta o animator novamente
	   } // fim do método actionPerformed
	} // fim da classe TimerHandler 
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
	    BufferedImage        image;
	    DrawAnimation    animation;
	    ModelEnviroment      model;

	    ScaleConverter.latIni = 0.0;
	    ScaleConverter.longIni = 0.0;
	    ScaleConverter.latEnd = 4.0;
	    ScaleConverter.longEnd = 4.0;
	    ScaleConverter.height = 400;
	    ScaleConverter.width  = 400;
	    
	    
	    JFrame frm = new JFrame("Teste Imagem");
	    animation  = new DrawAnimation(400,400);
	    model      =  ModelEnviroment.getInstance();
	    model.createMobileAgents(10, 5);
	    model.createStaticAgents(5);
	    model.setScaleEnviroment(ScaleConverter.latIni , ScaleConverter.longIni, 
	    		                 ScaleConverter.latEnd,ScaleConverter.longEnd,
	    		                 ScaleConverter.width, ScaleConverter.height);
	    
	    JAnimationPanel pan = new JAnimationPanel(400,400,model);
	    
	    //JLabel lbl = new JLabel( area.getImagem() );
	    JLabel lbl = new JLabel();
	    pan.add( lbl );
	    pan.startAnimation();
	    frm.getContentPane().add( pan );
	    frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    frm.pack();
	    frm.show();		
	
	}

} // fim da classe LogoAnimatorJPanel


/**************************************************************************
* (C) Copyright 1992-2005 by Deitel & Associates, Inc. and               *
* Pearson Education, Inc. All Rights Reserved.                           *
*                                                                        *
* DISCLAIMER: The authors and publisher of this book have used their     *
* best efforts in preparing the book. These efforts include the          *
* development, research, and testing of the theories and programs        *
* to determine their effectiveness. The authors and publisher make       *
* no warranty of any kind, expressed or implied, with regard to these    *
* programs or to the documentation contained in these books. The authors *
* and publisher shall not be liable in any event for incidental or       *
* consequential damages in connection with, or arising out of, the       *
* furnishing, performance, or use of these programs.                     *
*************************************************************************/
