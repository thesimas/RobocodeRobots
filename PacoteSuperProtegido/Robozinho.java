package PacoteSuperProtegido;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

import robocode.*;
import robocode.AdvancedRobot;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Robozinho extends AdvancedRobot
{
	Set<String> inimigosVistos = new HashSet<String>();

	public void run() {

		setColors(Color.red,Color.blue,Color.green); // body,gun,radar



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
	public void onScannedRobot(ScannedRobotEvent e) {
		inimigosVistos.add(e.getName());
		System.out.print("Eu vi esse robô: " + e.getName() + "\n\n");
		System.out.print("Inimigos vistos: " + inimigosVistos.size() + "\n\n");
		// Replace the next line with any behavior you would like
		fire(2);
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
}
