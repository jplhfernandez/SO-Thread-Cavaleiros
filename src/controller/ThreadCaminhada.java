package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadCaminhada extends Thread {
	private Random aleatorio = new Random();
	private static int[] portas = new int[4];
	private static int ordemChegada = 0;
	private int idCavaleiro;
	private static boolean tocha = false;
	private static boolean pedra = false;
	private int saida;
	private Semaphore semaforo;

	public ThreadCaminhada(Semaphore semaforo, int saida) {
		// TODO Auto-generated constructor stub
		super();
		this.idCavaleiro = (int)threadId();
		this.semaforo = semaforo;
		this.saida = saida;
	}

	@Override
	public void run() {
		cavaleiroCaminhando();
		try {
			semaforo.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			semaforo.release();
			portaCaminho();
		}
	}

	private void cavaleiroCaminhando() {
		// TODO Auto-generated method stub
		int distanciaTotal = 2000;
		int distanciaPercorrida = 0;
		int distanciaPerAnt = 0;
		int deslocamento = 0;
		int idCavaleiroTocha = 0;
		while (distanciaPercorrida < distanciaTotal) {
			distanciaPerAnt = deslocamento;
			deslocamento = aleatorio.nextInt(2,5);
			distanciaPercorrida += deslocamento;
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //System.out.println("#" + idCavaleiro + " andou " + deslocamento + "m; Total: " + distanciaPercorrida + "m/2000m. Último Salto: " + distanciaPerAnt + "m;");
	        if(tocha == false & distanciaPercorrida >= 500) {
	        	tocha = true;
	        	deslocamento = aleatorio.nextInt(4,5);
	        	System.out.println("O Cavaleiro " + idCavaleiro + " pegou a tocha "
	        			+ "e recebeu +2 de velocidade");
	        	idCavaleiroTocha = idCavaleiro;
	        }
	        
	        if(idCavaleiroTocha != idCavaleiro & pedra == false & distanciaPercorrida >= 1500) {
	        	pedra = true;
	        	deslocamento = aleatorio.nextInt(4,5);
	        	System.out.println("O Cavaleiro " + idCavaleiro + " pegou a pedra "
	        			+ "e recebeu +2 de velocidade");
	        }
		}
		System.out.println("#"+idCavaleiro+" chegou na porta, sendo a posição:"+ordemChegada++);
	}


	private void portaCaminho() {
		System.out.println("O cavaleiro "+idCavaleiro+" vai cruzar pela porta!");
		
		int portaEscolhida;
		
		boolean portaDiferente;
		
		do {
			portaDiferente = true;
			portaEscolhida = (int)(Math.random()*4)+1;
			
			for(int i = 0; i < ordemChegada;i++) {
				if(portas[i] == portaEscolhida) {
					portaDiferente = false;
				}
			}
		}while(!portaDiferente);
		portas[ordemChegada - 1] = portaEscolhida;
		System.out.println("O cavaleiro "+idCavaleiro+" escolheu a porta: "+portaEscolhida+"!");
		
		if(portaEscolhida != saida) {
			System.out.println("DESCANSE EM PAZ: Oh não! O cavaleiro "+idCavaleiro+" escolheu a porta errada foi devorado por monstros!");
		}else {
			System.out.println("Deu tudo certo! O cavaleiro "+idCavaleiro+" escolheu a saída verdadeira, e está são e salvo!");
		}
	}
}