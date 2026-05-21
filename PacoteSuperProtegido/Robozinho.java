package PacoteSuperProtegido;

import robocode.*;
import robocode.AdvancedRobot;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Robozinho extends AdvancedRobot {

	// Lógica baseada na quantidade de robôs inimigos presente na Arena.
	Set<String> inimigosVistos = new HashSet<String>();

	public void run() {
		coresDoRobo();

		// O Radar fica independente do Canhão, metodo especifico do AdvancedRobot;
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);

		while (true) {

			setTurnRadarRight(360);

			switch (inimigosVistos.size()) {
				case 1:
					// Lógica para o 1v1
					// estrategiaDuelo();
					break;
				case 2:
					// estrategiaTwoEnemy();
					break;
				case 3:
					// estrategiaThreeEnemy();
					break;
				default:
					// estrategiaSobrevivencia();
					// Estratégia para enfrentar mais de 3 inimigos.
					break;

			}
			// Execute é para executar o metodo set em todos os rounds.
			execute();
		}
	}

	// Evento para quando o robô for o campeão da rodada.
	public void onWin() {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
			setAllColors(Color.getHSBColor((i+45), (i*2), (i*5)));
		}
	}

	// Evento para quando o nosso robô escanear outro robô;
	public void onScannedRobot(ScannedRobotEvent inimigo) {
		inimigosVistos.add(inimigo.getName());
		System.out.print("Avistei esse robô: " + inimigo.getName());
		System.out.println("Inimigos avistados: " + inimigosVistos.size());
	}

	// Evento para quando o nosso robô for atingido por uma bala.
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(40);

	}

	// Evento para quando o nosso robô colidir com outro robô.
	public void onHitRobot(HitRobotEvent e) {
		// Se batermos de frente, atira com força MÁXIMA porque a chance de acertar é 100%
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			fire(3);
		}
		back(50);
	}

	// Evento para quando o nosso robô atingir uma parade.
	public void onHitWall(HitWallEvent e) {
		back(20);
	}

	// Evento para quando o nosso robô acertar um tiro em outro robô.
	public void onBulletHit(BulletHitEvent e) {
		System.out.println("Acertei um tiro no " + e.getName() + "!");
		System.out.println("Minha energia subiu para: " + getEnergy());
	}

	// Evento para quando o nosso robô errar um tiro.
	public void onBulletMissed(BulletMissedEvent e) {
		System.out.println("Putz, errei o tiro. Melhor me aproximar antes de atirar de novo.");
	}

	// Evento de quando o nosso robô identifica pela arena um robo morto.
	public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
		inimigosVistos.remove(robotDeathEvent.getName());
		// Imprime no console quem morreu e quantos inimigos conhecidos restam
		System.out.println("O robô " + robotDeathEvent.getName() + " foi destruído!");
		System.out.println("Agora restam " + inimigosVistos.size() + " inimigos na minha lista.");
	}

	public void coresDoRobo(){
		setColors(Color.red,Color.black,Color.white);
	}

	public void estrategiaDuelo(){}

	public void estrategiaTwoEnemy(){}

	public void estrategiaThreeEnemy(){}

	public void estrategiaSobrevivencia(){}
}
