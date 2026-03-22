package com.fr.pokemon.view.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import com.fr.pokemon.view.GamePanel;

/**
 * Gestore del sistema di tassellatura (Tile System) e della mappa di gioco.
 * <p>
 * Questa classe si occupa di caricare le immagini dei tile, leggere i dati della mappa 
 * da file esterni e renderizzare il terreno in modo ottimizzato, calcolando la 
 * posizione relativa rispetto al giocatore per creare l'effetto telecamera.
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class TileManager 
{
    private GamePanel gp;          // Riferimento al pannello principale
    private Tile[] tile;           // Array contenente i diversi tipi di tile disponibili
    private int mapTileNum[][];    // Matrice che memorizza la struttura della mappa (indici dei tile)

    /**
     * Inizializza il gestore dei tile, prepara l'array dei tipi e carica la mappa.
     * @param gp Il {@link GamePanel} di riferimento.
     */
    public TileManager(GamePanel gp) 
    {
        this.gp = gp;
        this.tile = new Tile[10]; // Inizializza un set di 10 tipologie di tile
        
        // Inizializzazione della matrice con le dimensioni massime del mondo (es. 50x50)
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
        
        getTileImage(); // Carica le immagini nelle risorse
        loadMap("/maps/world01.txt"); // Legge il file di testo della mappa
    }

    /**
     * Carica le immagini dei tile dai file sorgente e definisce le proprietà fisiche (es. collisioni).
     */
    public void getTileImage() 
    {
        try 
        {
            // Tile 0: Erba corta (calpestabile)
            tile[0] = new Tile(); 
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/shortgrass.png"));
            
            // Tile 1: Ghiaia (calpestabile)
            tile[1] = new Tile(); 
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gravel.png"));
            
            // Tile 2: Acqua (solida - attiva la collisione)
            tile[2] = new Tile(); 
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;
        } 
        catch (IOException e) 
        {
            e.printStackTrace(); // Log dell'errore in caso di file non trovato o corrotto
        }
    }

    /**
     * Legge un file di testo contenente i numeri della mappa e li carica nella matrice mapTileNum.
     * @param filePath Il percorso relativo del file della mappa.
     */
    public void loadMap(String filePath) 
    {
        try 
        {
            // Apertura dello stream per leggere il file della mappa
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0; 
            int row = 0;
            
            // Ciclo attraverso le righe e le colonne definite per il mondo di gioco
            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) 
            {
                String line = br.readLine(); // Legge una riga intera dal file
                String numbers[] = line.split(" "); // Separa i numeri usando lo spazio come divisore
                
                while (col < gp.getMaxWorldCol()) 
                {
                    // Converte la stringa in numero e la salva nella matrice
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                
                // Una volta finita una colonna, resetta e passa alla riga successiva
                if (col == gp.getMaxWorldCol()) 
                { 
                    col = 0; 
                    row++; 
                }
            }
            br.close(); // Chiusura del lettore
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Disegna la mappa a schermo. Implementa una logica di "Frustum Culling" per disegnare 
     * solo i tile che entrano nel campo visivo della telecamera (attorno al giocatore).
     * @param g2 Il contesto grafico {@link Graphics2D}.
     */
    public void draw(Graphics2D g2) 
    {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) 
        {
            int tileNum = mapTileNum[worldCol][worldRow];

            // Calcolo della posizione assoluta del tile all'interno del mondo di gioco
            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();

            // Calcolo della posizione relativa del tile sullo schermo rispetto al giocatore
            // La formula sottrae la posizione del giocatore e aggiunge l'offset del centro schermo
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            // RENDERING OTTIMIZZATO: Controlla se il tile è visibile nello schermo prima di disegnarlo
            // Se il tile è fuori dai margini visibili (più un margine di sicurezza), non viene processato
            if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) 
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            }
            
            worldCol++;
            
            // Gestione del passaggio alla riga successiva nella matrice del mondo
            if (worldCol == gp.getMaxWorldCol()) 
            {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    // --- METODI ACCESSORI (GETTERS) ---

    /** @return L'array contenente le tipologie di tile caricate. */
    public Tile[] getTile() { return tile; }
    
    /** @return La matrice numerica della mappa corrente. */
    public int[][] getMapTileNum() { return mapTileNum; }
}