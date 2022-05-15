package Quadtree;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class QuadTree {

    private Nodo raiz;
    private int anchoOriginal, altoOriginal, blanco;
	
	public QuadTree(){//Se le pasa la imagen como parámetro
		this.raiz= new Nodo();
		this.blanco= Color.WHITE.getRGB();
		this.altoOriginal= 0;
		this.anchoOriginal= 0;
	}
	
	public void compresion(File imagejpg) throws EInfo {
		//Se lee la imagen
		BufferedImage image = null;
		try {
			image= ImageIO.read(imagejpg);
			this.altoOriginal= image.getHeight();
			this.anchoOriginal= image.getWidth();
			image= ajustarImagen(image);

		} catch (IOException e) {
			System.out.println("No existe el archivo con dirección: " + imagejpg);
		}

		//Se consigue el alto de la imagen
		int[] alto = {0, image.getHeight() - 1};
		//Se consigue el ancho de la imagen
		int[] ancho = {0, image.getWidth() - 1};
		//Comienza la compresion en el metodo recursivo
		compresion(image, alto, ancho, raiz);
	}

	public void compresion(BufferedImage image, int[] alto, int[] ancho, Nodo actual) throws EInfo {
		//Se coge el pixel en las primeras cordenadas del alto y ancho
		int pixel = image.getRGB(alto[0], ancho[0]);
		//Se verifica que no haya solo un pixel
		if(alto[0] == alto[1] && ancho[0] == ancho[1]) {
			if(pixel == blanco) actual.setInfo(1);
			else actual.setInfo(-1);
		} else {
			int fila = alto[0];
			int columna = ancho[0];
			//Se busca en todo el area de pixeles si se tiene un color distinto del primer pixel
			boolean interrupcion = false;
			while(fila <= alto[1] && !interrupcion) {
				columna = ancho[0];
				while(columna <= ancho[1] && !interrupcion) {
					if(image.getRGB(fila, columna) != pixel) {
						interrupcion = true;
						fila--; columna--;
					}
					columna++;
				}
				fila++;
			}
			//En caso de que se salga del area que tenemos, se define la hoja, si no, se crean los hijos
			if(fila > alto[1] && columna > ancho[1]) {
				//Se define la hoja con el color del pixel
				if(pixel == blanco) actual.setInfo(1);
				else actual.setInfo(-1);
			}else {
				//Se crean los hijos
				int mitadAlto = (alto[0] + alto[1])/2;
				int mitadAncho = (ancho[0] + ancho[1])/2;

				actual.crearHijos();
				//Arriba izquierda
				compresion(image, new int[] {alto[0], mitadAlto}, new int[] {ancho[0], mitadAncho}, actual.getHijo(0));
				//Arriba derecha
				compresion(image, new int[] {alto[0], mitadAlto}, new int[] {mitadAncho + 1, ancho[1]}, actual.getHijo(1));
				//Abajo derecha
				compresion(image, new int[] {mitadAlto + 1, alto[1]}, new int[] {mitadAncho + 1, ancho[1]}, actual.getHijo(2));
				//Abajo izquierda
				compresion(image, new int[] {mitadAlto + 1, alto[1]}, new int[] {ancho[0], mitadAncho}, actual.getHijo(3));
			}
		}
    }
	
	public BufferedImage ajustarImagen(BufferedImage image) throws IOException{
		int i=0;
		int pot;
		//Se encuentra la potencia de 2 más cercana y mayor o igual a la medida más grande (ancho o alto)
		while((pot= (int) Math.pow(2, i++))<anchoOriginal || pot<altoOriginal);

		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		//Se crea un nuevo lienzo con las dimensiones originales más lo que se debe agregar para que sea 1x1 y con dimensiones de 2^n
        image = config.createCompatibleImage(pot,pot);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, altoOriginal, anchoOriginal, pot-altoOriginal);
        g2.fillRect(anchoOriginal, 0, pot-anchoOriginal, pot);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

		return image;
    }

	public BufferedImage limpiarImagen(BufferedImage image){
		return image.getSubimage(0, 0, anchoOriginal, altoOriginal);
	}

    //metodo recursivo de reconstruir

	public static void main(String[] args) {
		File archivo_imagen = new File("C:/Users/Alejandro/Downloads/Untitled2.png");
		QuadTree a = new QuadTree();
		try {
			a.compresion(archivo_imagen);
			
		} catch (EInfo e) {
			e.printStackTrace();
		}
	}
}
