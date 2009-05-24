//package mmi;
/**
 * Klasse zur Modellierung eines Tunnels
 */

public class Tunnel {

	private int width;
	private int length;
	private int direction;
	
	/**
	 * Erzeugt einen Tunnel mit angegebener L&auml;nge, Breite und Ausrichtung
	 * 
	 * @param width die Breite des Tunnels
	 * @param length die L&auml; des Tunnels
	 * @param direction die Ausrichtung des Tunnels
	 */
	public Tunnel(int width, int length, int direction){
		this.width = width;
		this.length = length;
		this.direction = direction;
	}
	
	/**
	 * Gibt die Breite des Tunnels zur&uuml;ck
	 * 
	 * @return Breite des Tunnels
	 */
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * Gibt die L&auml;nge des Tunnels zur&uuml;ck
	 * 
	 * @return L&auml;nge des Tunnels
	 */
	public int getLength(){
		return this.length;
	}
	
	/**
	 * Gibt die Ausrichtung des Tunnels zur&uuml;
	 * 
	 * @return Ausrichtung des Tunnels
	 */
	public int getDirection(){
		return this.direction;
	}
	
	/**
	 * Gibt eine Stringrepr&auml;sentation des Tunnels zur&uuml;ck
	 */
	public String toString(){
		return "width: " + this.getWidth() + ", length: " + this.getLength() + ", direction: " + this.getDirection();
	}
}
