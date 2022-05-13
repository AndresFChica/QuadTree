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