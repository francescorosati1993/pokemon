package com.fr.pokemon;

import javax.swing.JFrame;
import com.fr.pokemon.view.GamePanel;

/**
 * Classe principale di avvio dell'applicazione Pokemon Adventure.
 * <p>
 * Questa classe contiene il metodo main che funge da entry point del programma.
 * Si occupa di inizializzare la finestra principale (JFrame), configurare le 
 * impostazioni di sistema della GUI e avviare il thread del loop di gioco.
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class PokemonApplication 
{
    /**
     * Metodo di ingresso del programma. 
     * Configura la finestra di gioco e avvia l'esecuzione del pannello principale.
     * @param args Argomenti della riga di comando (non utilizzati).
     */
	public static void main(String[] args) 
	{
		// 1. Creiamo l'oggetto finestra (JFrame è il contenitore principale della GUI)
        JFrame window = new JFrame();
        
        // 2. Questa riga permette di chiudere il programma correttamente 
        // quando l'utente preme la "X" della finestra.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 3. Impediamo all'utente di ridimensionare la finestra trascinando i bordi.
        // In un gioco stile retro, cambiare dimensione a caso romperebbe la grafica.
        window.setResizable(false);
        
        // 4. Il titolo che apparirà sulla barra in alto della finestra.
        window.setTitle("Pokemon Adventure");
        
        // 5. Istanziamo il pannello di gioco che contiene tutta la logica e la grafica.
        GamePanel gamePanel = new GamePanel();
        
        // 6. Aggiungiamo il pannello alla finestra appena creata.
        window.add(gamePanel); 
        
        // 7. Adatta la dimensione della finestra JFrame in base alla dimensione preferita 
        // definita dentro GamePanel (setPreferredSize).
        window.pack();
        
        // 8. Centra la finestra sullo schermo dell'utente. 
        // Deve essere chiamato DOPO window.pack() per calcolare correttamente il centro.
        window.setLocationRelativeTo(null);
        
        // 9. Rende la finestra visibile (di default le finestre Swing sono invisibili).
        window.setVisible(true);

        // 10. Avvia il thread principale del gioco per iniziare il loop di update e draw.
        gamePanel.startGameThread();
	}
}
