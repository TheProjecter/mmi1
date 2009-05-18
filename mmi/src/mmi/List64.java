package mmi;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasse zur Erzeugung einer randomisierten Liste aus Tunneln
 *
 */
public class List64 {

	private	ArrayList<Tunnel> liste;
	
	/**
	 * Erzeugt eine ArrayList der L&auml;nge 64
	 */
	public List64(){
		this.liste = new ArrayList<Tunnel>(64);
		this.fill();
		this.randomize();
	}
	
	/**
	 * F&uuml;llt die Liste mit Tunneln aller Varianten
	 */
	private void fill(){
		
		this.liste.add(new Tunnel(16, 128, 0)); 
		this.liste.add(new Tunnel(16, 128, 1)); 
		this.liste.add(new Tunnel(16, 128, 2)); 
		this.liste.add(new Tunnel(16, 128, 3)); 
		
		this.liste.add(new Tunnel(16, 256, 0)); 
		this.liste.add(new Tunnel(16, 256, 1)); 
		this.liste.add(new Tunnel(16, 256, 2)); 
		this.liste.add(new Tunnel(16, 256, 3)); 
		
		this.liste.add(new Tunnel(16, 512, 0)); 
		this.liste.add(new Tunnel(16, 512, 1)); 
		this.liste.add(new Tunnel(16, 512, 2)); 
		this.liste.add(new Tunnel(16, 512, 3)); 
		
		this.liste.add(new Tunnel(16, 1024, 0)); 
		this.liste.add(new Tunnel(16, 1024, 1)); 
		this.liste.add(new Tunnel(16, 1024, 2)); 
		this.liste.add(new Tunnel(16, 1024, 3)); 
		
		this.liste.add(new Tunnel(32, 128, 0)); 
		this.liste.add(new Tunnel(32, 128, 1)); 
		this.liste.add(new Tunnel(32, 128, 2)); 
		this.liste.add(new Tunnel(32, 128, 3));  
		
		this.liste.add(new Tunnel(32, 256, 0)); 
		this.liste.add(new Tunnel(32, 256, 1)); 
		this.liste.add(new Tunnel(32, 256, 2)); 
		this.liste.add(new Tunnel(32, 256, 3)); 
		
		this.liste.add(new Tunnel(32, 512, 0)); 
		this.liste.add(new Tunnel(32, 512, 1)); 
		this.liste.add(new Tunnel(32, 512, 2)); 
		this.liste.add(new Tunnel(32, 512, 3)); 
		
		this.liste.add(new Tunnel(32, 1024, 0)); 
		this.liste.add(new Tunnel(32, 1024, 1)); 
		this.liste.add(new Tunnel(32, 1024, 2)); 
		this.liste.add(new Tunnel(32, 1024, 3)); 
		
		this.liste.add(new Tunnel(64, 128, 0)); 
		this.liste.add(new Tunnel(64, 128, 1)); 
		this.liste.add(new Tunnel(64, 128, 2)); 
		this.liste.add(new Tunnel(64, 128, 3)); 
		
		this.liste.add(new Tunnel(64, 256, 0)); 
		this.liste.add(new Tunnel(64, 256, 1)); 
		this.liste.add(new Tunnel(64, 256, 2)); 
		this.liste.add(new Tunnel(64, 256, 3)); 
		
		this.liste.add(new Tunnel(64, 512, 0)); 
		this.liste.add(new Tunnel(64, 512, 1)); 
		this.liste.add(new Tunnel(64, 512, 2)); 
		this.liste.add(new Tunnel(64, 512, 3)); 
		
		this.liste.add(new Tunnel(64, 1024, 0)); 
		this.liste.add(new Tunnel(64, 1024, 1)); 
		this.liste.add(new Tunnel(64, 1024, 2)); 
		this.liste.add(new Tunnel(64, 1024, 3)); 
		
		this.liste.add(new Tunnel(128, 128, 0)); 
		this.liste.add(new Tunnel(128, 128, 1)); 
		this.liste.add(new Tunnel(128, 128, 2)); 
		this.liste.add(new Tunnel(128, 128, 3)); 
		
		this.liste.add(new Tunnel(128, 256, 0)); 
		this.liste.add(new Tunnel(128, 256, 1)); 
		this.liste.add(new Tunnel(128, 256, 2)); 
		this.liste.add(new Tunnel(128, 256, 3)); 
		
		this.liste.add(new Tunnel(128, 512, 0)); 
		this.liste.add(new Tunnel(128, 512, 1)); 
		this.liste.add(new Tunnel(128, 512, 2)); 
		this.liste.add(new Tunnel(128, 512, 3)); 
		
		this.liste.add(new Tunnel(128, 1024, 0)); 
		this.liste.add(new Tunnel(128, 1024, 1)); 
		this.liste.add(new Tunnel(128, 1024, 2)); 
		this.liste.add(new Tunnel(128, 1024, 3)); 
		
	}
	
	/**
	 * Ordnet die Tunnel in der Liste in zuf&auml;lliger Reihenfolge an
	 */
	private void randomize(){
		Random generator = new Random();
		
		ArrayList<Tunnel> randomList = new ArrayList<Tunnel>(64);
		
		for(int i = 63; i > 0; i--){
			int random = generator.nextInt(i);
			randomList.add(this.liste.get(random));
			this.liste.remove(random);
		}
		
		this.liste = randomList;
	}
	
	/**
	 * L&ouml; den ersten Eintrag aus der Liste und gibt ihn zur&uuml;ck
	 * 
	 * @return ersten Tunnel der Liste
	 */
	public Tunnel getNext(){
		Tunnel next = this.liste.get(0);
		this.liste.remove(0);
		return next;
	}
	
	/**
	 * Gibt die Liste zur&uuml;ck
	 * 
	 * @return Liste
	 */
	public ArrayList<Tunnel> getList(){
		return this.liste;
	}
    	
}
