package PacoteSuperProtegido;

import robocode.*;
import robocode.AdvancedRobot;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import robocode.util.Utils;
import java.util.HashMap;
import java.util.Map;

public class Robozinho extends AdvancedRobot {

	// Lógica baseada na quantidade de robôs inimigos presente na Arena.
	Set<String> inimigosVistos = new HashSet<String>();

	// Memória dos inimigos
	Map<String, Double> ultimaEnergia = new HashMap<String, Double>();
	Map<String, Double> ultimoHeading = new HashMap<String, Double>();

	// Controle de movimentação
	int direcao = 1;

	public void run() {
		coresDoRobo();

		// O Radar fica independente do Canhão, metodo especifico do AdvancedRobot;
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);

		while (true) {

			setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

			setTurnRight(35 * direcao);
			setAhead(140 * direcao);

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
			setAllColors(Color.getHSBColor((i + 45), (i * 2), (i * 5)));
		}
	}

	// Evento para quando o nosso robô escanear outro robô;
	public void onScannedRobot(ScannedRobotEvent inimigo) {
		double radarTurn = getHeadingRadians()
				+ inimigo.getBearingRadians()
				- getRadarHeadingRadians();

		setTurnRadarRightRadians(
				Utils.normalRelativeAngle(radarTurn) * 2);

		double firePower = Math.min(3.0, Math.max(1.2, 400 / inimigo.getDistance())); // tiro com distancia

		double energiaAnterior = ultimaEnergia.getOrDefault(
				inimigo.getName(),
				inimigo.getEnergy()); // desvia quando o inimigo atira

		double deltaEnergia = energiaAnterior - inimigo.getEnergy();

		ultimaEnergia.put(
				inimigo.getName(),
				inimigo.getEnergy());

		double enemyHeading = inimigo.getHeadingRadians(); // mira conforme o histórico do inimigo (preditiva)

		double enemyVelocity = inimigo.getVelocity();

		double enemyX = getX()
				+ Math.sin(
						getHeadingRadians()
								+ inimigo.getBearingRadians())
						* inimigo.getDistance();

		double enemyY = getY()
				+ Math.cos(
						getHeadingRadians()
								+ inimigo.getBearingRadians())
						* inimigo.getDistance();

		double bulletSpeed = 20 - firePower * 3;

		double tempo = inimigo.getDistance() / bulletSpeed;

		double headingAnterior = ultimoHeading.getOrDefault(
				inimigo.getName(),
				enemyHeading);

		double mudancaHeading = Utils.normalRelativeAngle(
				enemyHeading - headingAnterior);

		ultimoHeading.put(
				inimigo.getName(),
				enemyHeading);

		double predictedX = enemyX;
		double predictedY = enemyY;

		double predictedHeading = enemyHeading;

		for (int i = 0; i < tempo; i++) {

			predictedX += Math.sin(predictedHeading)
					* enemyVelocity;

			predictedY += Math.cos(predictedHeading)
					* enemyVelocity;

			predictedHeading += mudancaHeading;

			predictedX = Math.max(
					18,
					Math.min(
							getBattleFieldWidth() - 18,
							predictedX));

			predictedY = Math.max(
					18,
					Math.min(
							getBattleFieldHeight() - 18,
							predictedY));
		}

		double anguloAbsoluto = Math.atan2(
				predictedX - getX(),
				predictedY - getY());

		double gunTurn = Utils.normalRelativeAngle(
				anguloAbsoluto
						- getGunHeadingRadians());

		setTurnGunRightRadians(gunTurn);

		if (Math.abs(getGunTurnRemaining()) < 10) {
			fire(firePower);
		} // fim da mira preditiva

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
		// Se batermos de frente, atira com força MÁXIMA porque a chance de acertar é
		// 100%
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			fire(3);
		}
		back(50);
	}

	// Evento para quando o nosso robô atingir uma parade.
	public void onHitWall(HitWallEvent e) {
		back(20)
		turnRight(90);
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

	public void coresDoRobo() {
		setColors(Color.red, Color.black, Color.white);
	}

	public void estrategiaDuelo() {
	}

	public void estrategiaTwoEnemy() {
	}

	public void estrategiaThreeEnemy() {
	}

	public void estrategiaSobrevivencia() {
	}
}
