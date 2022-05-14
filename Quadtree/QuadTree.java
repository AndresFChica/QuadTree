package Quadtree;

public class QuadTree {

    Nodo raiz;
    int anchoOriginal, altoOriginal;
	
	public QuadTree(){//Imagen imagen) { //Se le pasa la imagen como parámetro
		raiz= new Nodo();
		raiz.crearHijos();
        // this.altoOriginal= imagen.getAlto
        // this.anchoOriginal= imagen.getAcho
	}
	
		public void compresion(File imagejpg) throws EInfo {
		//Se lee la imagen
		BufferedImage image = null;
		try {
			image = ImageIO.read(imagejpg);
			
		} catch (IOException e) {
			System.out.println("No existe ell archivo con dirección: " + imagejpg);
		}
		//Se consigue el alto de la imagen
		int[] alto = {0, image.getHeight()};
		//Se consigue el ancho de la imagen
		int[] ancho = {0, image.getWidth()};   //¿Porque el ancho y alto son arrays?
		//Comienza la compresion en el metodo recursivo
		compresion(image, alto, ancho, raiz);
	}
	
	public void compresion(BufferedImage image, int[] alto, int[] ancho, Nodo actual) throws EInfo {
		//Se coge el pixel en las primeras cordenadas del alto y ancho
		int pixel = image.getRGB(alto[0], ancho[0]);
		//Se verifica que no haya solo un pixel
		if(alto[0] == alto[1] && ancho[0] == ancho[1]) {
			//Crea el hijo
		} else {
			//Creamos el color blanco para poder compararlo con los pixeles
			Color match = new Color(255,255,255);
			int altoX = alto[0];
			int anchoY = ancho[0];
			//Si es blanco, entra
			if(pixel == match.getRGB()) {
				//Se busca en todo el area de pixeles si se tiene blanco
				//Puede que se necesite restar 1 vez el alto al salir de ambos ciclos
				while(altoX < alto[1] && image.getRGB(altoX, anchoY) == match.getRGB()) {
					anchoY = 0;
					while(anchoY < ancho[1] && image.getRGB(altoX, anchoY) == match.getRGB()) {
						anchoY++;
					}
					altoX++;
				}
				//En caso de que se salga del area que tenemos, se define la hoja, si no, se crean los hijos
				if(altoX >= alto[1] && anchoY >= ancho[1]) {
					//Se define la hoja con el color blanco
					actual.setInfo(1);
				} else {
					//Se crean los hijos
					int mitadAlto = alto[1]/2;
					int mitadAncho = ancho[1]/2;
					//Arriba izquierda
					compresion(image, new int[] {alto[0], mitadAlto}, new int[]{ancho[0], mitadAncho}, actual.getHijo(1));
					//Abajo izquierda
					compresion(image, new int[] {mitadAlto, alto[1]}, new int[]{ancho[0], mitadAncho}, actual.getHijo(2));
					//Arriba derecha
					compresion(image, new int[] {alto[0], mitadAlto}, new int[]{mitadAncho, ancho[1]}, actual.getHijo(3));
					//Abajo derecha
					compresion(image, new int[] {mitadAlto, alto[1]}, new int[]{mitadAncho, ancho[1]}, actual.getHijo(4));
				}
			} else {
				//Se busca en todo el area de pixeles si no se tiene el blanco
				//Puede que se necesite restar 1 vez el alto al salir de ambos ciclos
				while(altoX < alto[1] && image.getRGB(altoX, anchoY) != match.getRGB()) {
					anchoY = 0;
					while(anchoY < ancho[1] && image.getRGB(altoX, anchoY) != match.getRGB()) {
						anchoY++;
					}
					altoX++;
				}
				//En caso de que se salga del area que tenemos, se define la hoja, si no, se crean los hijos
				if(altoX >= alto[1] && anchoY >= ancho[1]) {
					//Se define la hoja con el color blanco
					actual.setInfo(-1);
				} else {
					//Se crean los hijos
					int mitadAlto = alto[1]/2;
					int mitadAncho = ancho[1]/2;
					
					//Problema con la recursividad, nullpointer exception cuando se llaman a los hijos del nodo actual
					//Puede que se tenga que sumar 1 an algunos sectores para no repetir pixel
					//Arriba izquierda
					compresion(image, new int[] {alto[0], mitadAlto}, new int[]{ancho[0], mitadAncho}, actual.getHijo(1));
					//Abajo izquierda
					compresion(image, new int[] {mitadAlto, alto[1]}, new int[]{ancho[0], mitadAncho}, actual.getHijo(2));
					//Arriba derecha
					compresion(image, new int[] {alto[0], mitadAlto}, new int[]{mitadAncho, ancho[1]}, actual.getHijo(3));
					//Abajo derecha
					compresion(image, new int[] {mitadAlto, alto[1]}, new int[]{mitadAncho, ancho[1]}, actual.getHijo(4));
				}
			}
			
		}
		
	}

    // Metodo que convierta la imagen no cuadrada a cuadrada por medio de pixeles negros
    // tambien retorna una imagen

    // Recorte una imagen y dado los atributos de la clase (medidas originales) retorne
    // una subimagen


    //metodo recursivo de compresión


    //metodo recursivo de reconstruir

}


//NOTA: Cuando la imagen no sea cuadrada, se le agregan pixeles para hacerla cuadrada
// y así poder hacer las subdivisiones, una vez para la reconstrucción, teniendo el
// alto y ancho original se puede recortar al tamaño original.
