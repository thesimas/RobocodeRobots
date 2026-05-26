package PacoteSuperProtegido;

import robocode.*;
import robocode.AdvancedRobot;
import java.awt.*;
import robocode.util.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Robozinho extends AdvancedRobot {

	// Lógica baseada na quantidade de robôs inimigos presente na Arena.
	Set<String> inimigosVistos = new HashSet<String>();
	// a função nativa do Robocode getOrders(), escaneia de forma mais precisa.

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

//			setTurnRight(35 * direcao);
//			setAhead(140 * direcao);

			switch (getOthers()) {
				case 1:
					estrategiaDuelo();
					break;
				case 2:
					estrategiaTwoEnemy();
					break;
				case 3:
					estrategiaThreeEnemy();
					break;
				default:
					estrategiaSobrevivencia();
					// Estratégia para enfrentar mais de 3 inimigos.
					break;
			}
			// Execute é para executar o metodo set em todos os rounds.
			execute();
		}
	}

	// Evento para quando o robô for o campeão da rodada.
	public void onWin(WinEvent event) {
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

//		double deltaEnergia = energiaAnterior - inimigo.getEnergy();

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
		back(20);
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
		// Muda a direção a cada 20 rodadas (ticks) de forma imprevisível
		if (getTime() % 20 == 0) {
			direcao = -direcao; // Inverte entre andar para frente (1) e para trás (-1)
		}

		// Move-se em arcos fechados e rápidos (zigue-zague)
		setAhead(100 * direcao);
		setTurnRight(45);
	}

	public void estrategiaTwoEnemy() {
		// Demora um pouco mais para mudar de direção (35 ticks)
		if (getTime() % 35 == 0) {
			direcao = -direcao;
		}

		// Passos maiores e arco de curva um pouco mais aberto
		setAhead(150 * direcao);
		setTurnRight(60);
	}

	public void estrategiaThreeEnemy() {
		// Curvas mais suaves para manter uma velocidade alta contínua
		if (getTime() % 40 == 0) {
			direcao = -direcao;
		}

		// Corre grandes distâncias (200) com curvas leves (30 graus)
		setAhead(200 * direcao);
		setTurnRight(30);
	}

	public void estrategiaSobrevivencia() {
		// Descobre as coordenadas exatas do centro da arena
		double meioDoMapaX = getBattleFieldWidth() / 2;
		double meioDoMapaY = getBattleFieldHeight() / 2;

		// Calcula a distância atual do nosso robô até o centro da arena
		double distanciaProCentro = Math.hypot(getX() - meioDoMapaX, getY() - meioDoMapaY);

		if (distanciaProCentro < 200) {
			// PERIGO: Estamos perto demais do centro!
			// Corre em linha reta para fugir da confusão
			setAhead(200 * direcao);
		} else {
			// SEGURO: Estamos nas bordas do mapa.
			// Fica patrulhando ao redor da arena de forma mais silenciosa
			if (getTime() % 50 == 0) {
				direcao = -direcao;
			}
			setAhead(150 * direcao);
			setTurnRight(20);
		}
	}
}
