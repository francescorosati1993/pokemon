package com.fr.pokemon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import com.fr.pokemon.controller.CollisionChecker;
import com.fr.pokemon.controller.KeyHandler;
import com.fr.pokemon.model.entity.Player;
import com.fr.pokemon.view.tile.TileManager;

/**
 * Gestore principale dell'interfaccia di gioco e del ciclo di vita dell'applicazione.
 * <p>
 * Questa classe estende {@link JPanel} per fornire le capacità di rendering e implementa 
 * {@link Runnable} per l'esecuzione del Game Loop in un thread dedicato. 
 * Funge da orchestratore tra il modello (entità), la vista (tiles) e i controller (input/collisioni).
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class GamePanel extends JPanel implements Runnable 
{
    private static final long serialVersionUID = 1L;
    
    // --- IMPOSTAZIONI DELLO SCHERMO ---
    private final int originalTileSize = 16; // Dimensione base di ogni tile (16x16 pixel)
    private final int scale = 4;             // Fattore di scala per monitor moderni
    private final int tileSize = originalTileSize * scale; // Dimensione effettiva a schermo (64x64)
    private final int maxScreenCol = 16;     // Numero di colonne visibili nel pannello
    private final int maxScreenRow = 12;     // Numero di righe visibili nel pannello
    private final int screenWidth = tileSize * maxScreenCol;  // Larghezza totale calcolata: 1024px
    private final int screenHeight = tileSize * maxScreenRow; // Altezza totale calcolata: 768px

    // --- IMPOSTAZIONI DEL MONDO (World Settings) ---
    private final int maxWorldCol = 50;      // Numero massimo di colonne della mappa totale
    private final int maxWorldRow = 50;      // Numero massimo di righe della mappa totale

    // --- FPS ---
    private int FPS = 60;                    // Target di fotogrammi al secondo

    // --- SISTEMA E ENTITÀ ---
    private TileManager tileM;               // Gestore dei tasselli della mappa e del disegno del terreno
    private KeyHandler keyH = new KeyHandler(); // Gestore degli input da tastiera
    private CollisionChecker cChecker;       // Gestore del controllo collisioni tra entità e mondo
    private Thread gameThread;               // Thread principale per far girare il loop di gioco
    private Player player;                   // L'istanza dell'entità controllata dall'utente

    /**
     * Inizializza un nuovo pannello di gioco configurando le impostazioni grafiche,
     * il double buffering e le dipendenze software primarie.
     */
    public GamePanel() 
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Imposta la risoluzione della finestra
        this.setBackground(Color.black); // Imposta il colore di sfondo (nero per evitare artefatti)
        this.setDoubleBuffered(true);    // Abilita il double buffering per un rendering fluido senza sfarfallio
        this.addKeyListener(keyH);       // Associa il gestore della tastiera al pannello
        this.setFocusable(true);         // Rende il pannello capace di ricevere il focus per gli input

        // INIZIALIZZAZIONE (L'ordine è fondamentale per la gerarchia delle dipendenze!)
        this.tileM = new TileManager(this);         // Carica e prepara i tile della mappa
        this.cChecker = new CollisionChecker(this); // Inizializza il motore di calcolo collisioni
        this.player = new Player(this, keyH);      // Crea il giocatore passandogli il pannello e l'input
    }

    /**
     * Instanzia e avvia il thread principale dedicato all'esecuzione della logica di gioco.
     */
    public void startGameThread() 
    {
        gameThread = new Thread(this); // Crea un nuovo thread passando questo oggetto Runnable
        gameThread.start();            // Avvia l'esecuzione del metodo run()
    }

    /**
     * Punto di ingresso del thread di gioco. Implementa un Game Loop basato su 
     * tecnica ad accumulo di tempo (Delta Timing) per garantire una velocità di 
     * aggiornamento costante indipendentemente dalle performance hardware.
     */
    @Override
    public void run() 
    {
        double drawInterval = 1000000000 / FPS; // Calcola l'intervallo di tempo per frame in nanosecondi
        double delta = 0;                       // Accumulatore del tempo trascorso
        long lastTime = System.nanoTime();      // Timestamp dell'ultimo ciclo
        long currentTime;

        while (gameThread != null) 
        {
            currentTime = System.nanoTime();
            // Aggiunge al delta la frazione di tempo trascorsa dall'ultimo frame
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Se il delta raggiunge 1, è trascorso il tempo necessario per un frame
            if (delta >= 1) 
            {
                update();   // Aggiorna la logica (posizioni, collisioni, IA)
                repaint();  // Richiama il metodo paintComponent per il rendering
                delta--;    // Sottrae il frame elaborato dal delta
            }
        }
    }

    /**
     * Esegue l'aggiornamento logico di tutti i componenti attivi nel sistema,
     * inclusi lo stato del giocatore e l'elaborazione degli input.
     */
    public void update() 
    {
        player.update(); // Aggiorna lo stato e la posizione del giocatore
    }

    /**
     * Gestisce il rendering grafico dei componenti del gioco.
     * <p>
     * Sovrascrive il metodo standard Swing per disegnare prima la mappa (livello inferiore)
     * e successivamente le entità (livello superiore).
     * </p>
     * * @param g Il contesto grafico {@link Graphics} fornito dal sistema Swing.
     */
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g); // Esegue la pulizia del pannello prima del nuovo disegno
        
        // Conversione in Graphics2D per accedere a funzionalità grafiche avanzate
        Graphics2D g2 = (Graphics2D) g;

        // DISEGNO (L'ordine determina la profondità dei layer)
        if (tileM != null) {
            tileM.draw(g2);   // Disegna prima lo sfondo e le tile del terreno
        }
        if (player != null) {
            player.draw(g2);  // Disegna il giocatore sopra i tile
        }

        g2.dispose(); // Rilascia le risorse di sistema occupate dal contesto grafico
    }

    // --- METODI ACCESSORI (GETTERS) ---

    /** @return La dimensione effettiva in pixel di un singolo tile. */
    public int getTileSize() { return tileSize; }
    
    /** @return La larghezza totale dell'area di gioco in pixel. */
    public int getScreenWidth() { return screenWidth; }
    
    /** @return L'altezza totale dell'area di gioco in pixel. */
    public int getScreenHeight() { return screenHeight; }
    
    /** @return Il numero massimo di colonne che compongono la mappa del mondo. */
    public int getMaxWorldCol() { return maxWorldCol; }
    
    /** @return Il numero massimo di righe che compongono la mappa del mondo. */
    public int getMaxWorldRow() { return maxWorldRow; }
    
    /** @return Il gestore dei tile attualmente in uso. */
    public TileManager getTileM() { return tileM; }
    
    /** @return L'istanza del sistema di controllo collisioni. */
    public CollisionChecker getcChecker() { return cChecker; }
    
    /** @return L'istanza del giocatore principale. */
    public Player getPlayer() { return player; }
}