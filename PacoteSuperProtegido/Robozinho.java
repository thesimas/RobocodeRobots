package PacoteSuperProtegido;

import robocode.*;
import robocode.AdvancedRobot;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Robozinho extends AdvancedRobot
{
	Set<String> inimigosVistos = new HashSet<String>();

	HashMap<String, Double> saudeInimigos = new HashMap<>();

	public void run() {

		setColors(Color.red,Color.blue,Color.green);

		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100); // ir para frente
			turnGunRight(360); 
			back(100);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent inimigo) {
		int quantidade = contaInimigos(inimigo);
		if(quantidade >= 4){
			fire(1);
			System.out.println("Fire 1");
		}else if(quantidade >= 2){
			fire(1.5);
			System.out.println("Fire 1.5");
		}else if(quantidade >= 1){
			fire(2);
			System.out.println("Fire 2");
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);

	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}

	public int contaInimigos(ScannedRobotEvent inimigo){
		inimigosVistos.add(inimigo.getName());
		String UltimoNome = null;
		for(String nome : inimigosVistos ) {
			UltimoNome = nome;
		}
		saudeInimigos.put(UltimoNome, inimigo.getEnergy());

		if(saudeInimigos.get(UltimoNome) < 0){
			inimigosVistos.remove(UltimoNome);
		}

		return inimigosVistos.size();
	}
}
