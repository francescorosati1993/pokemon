package com.fr.pokemon.model.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.fr.pokemon.controller.KeyHandler;
import com.fr.pokemon.view.GamePanel;

/**
 * Rappresenta il personaggio controllato dall'utente all'interno del gioco.
 * <p>
 * Questa classe estende {@link Entity} e gestisce l'input della tastiera per il movimento,
 * il caricamento degli sprite specifici del giocatore, la logica di animazione 
 * e il posizionamento relativo alla telecamera di gioco.
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class Player extends Entity 
{
    private GamePanel gp;     // Riferimento al pannello di gioco principale
    private KeyHandler keyH;  // Riferimento al gestore degli input per il movimento

    // --- POSIZIONE FISSA SULLO SCHERMO (Telecamera) ---
    private final int screenX; // Posizione X costante del giocatore sullo schermo (centro)
    private final int screenY; // Posizione Y costante del giocatore sullo schermo (centro)

    /**
     * Costruisce un nuovo oggetto Player, inizializzando i riferimenti di sistema,
     * calcolando la posizione della telecamera e caricando le risorse grafiche.
     * * @param gp Il {@link GamePanel} di riferimento.
     * @param keyH Il {@link KeyHandler} per la gestione degli input.
     */
    public Player(GamePanel gp, KeyHandler keyH) 
    {
        this.gp = gp;
        this.keyH = keyH;

        // Calcolo del centro dello schermo: metà risoluzione meno metà dimensione del tile
        this.screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        this.screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);

        // Definizione dell'area solida per le collisioni (Hitbox personalizzata)
        // Parametri: x, y, larghezza, altezza (relativi al tile del giocatore)
        solidArea = new Rectangle(8, 16, 32, 32); 

        setDefaultValues(); // Imposta i valori di stato iniziali
        getPlayerImage();   // Carica le immagini degli sprite
    }

    /**
     * Configura i valori predefiniti del giocatore, come la posizione iniziale 
     * nel mondo di gioco, la velocità e la direzione di partenza.
     */
    public void setDefaultValues() 
    {
        // Posizionamento iniziale (es. centro di una mappa 50x50 tile)
        worldX = gp.getTileSize() * 25; 
        worldY = gp.getTileSize() * 25;
        speed = 4;           // Velocità di movimento in pixel
        direction = "down";  // Direzione iniziale verso il basso
    }

    /**
     * Carica le immagini degli sprite del giocatore dalle risorse del progetto.
     * Gestisce le eccezioni di input/output in caso di file mancanti.
     */
    public void getPlayerImage() 
    {
        try 
        {
            // Caricamento sprite di movimento e stati di riposo
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            
            stayUp = ImageIO.read(getClass().getResourceAsStream("/player/stayup.png"));
            stayDown = ImageIO.read(getClass().getResourceAsStream("/player/staydown.png"));
            stayLeft = ImageIO.read(getClass().getResourceAsStream("/player/stayleft.png"));
            stayRight = ImageIO.read(getClass().getResourceAsStream("/player/stayright.png"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace(); // Log dell'errore se il caricamento fallisce
        }
    }

    /**
     * Aggiorna lo stato del giocatore ad ogni frame.
     * Gestisce l'input, il controllo delle collisioni e il ciclo dell'animazione.
     */
    public void update() 
    {
        // Verifica se almeno un tasto di movimento è premuto
        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) 
        {
            // Aggiornamento della direzione in base al tasto premuto
            if (keyH.isUpPressed()) direction = "up";
            else if (keyH.isDownPressed()) direction = "down";
            else if (keyH.isLeftPressed()) direction = "left";
            else if (keyH.isRightPressed()) direction = "right";

            // CONTROLLO COLLISIONI: Reset del flag e verifica con il TileManager
            collisionOn = false;
            gp.getcChecker().checkTile(this);

            // Se non ci sono collisioni, il giocatore può muoversi nelle coordinate del mondo
            if (!collisionOn) 
            {
                switch (direction) 
                {
                    case "up":    worldY -= speed; break; // Sottrae pixel per salire
                    case "down":  worldY += speed; break; // Aggiunge pixel per scendere
                    case "left":  worldX -= speed; break; // Sottrae pixel per andare a sinistra
                    case "right": worldX += speed; break; // Aggiunge pixel per andare a destra
                }
            }

            // GESTIONE ANIMAZIONE: Incrementa il contatore per alternare gli sprite
            spriteCounter++;
            if (spriteCounter > 12) // Ogni 12 frame cambia l'immagine dello sprite
            {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    /**
     * Disegna il giocatore sul pannello di gioco.
     * L'immagine viene scelta in base alla direzione e alla fase dell'animazione.
     * * @param g2 Il contesto grafico {@link Graphics2D} utilizzato per il rendering.
     */
    public void draw(Graphics2D g2) 
    {
        BufferedImage image = null;

        // Selezione dello sprite corretto in base alla direzione e al numero di animazione
        switch (direction) 
        {
            case "up":    image = (spriteNum == 1) ? up1 : up2; break;
            case "down":  image = (spriteNum == 1) ? down1 : down2; break;
            case "left":  image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }
        
        // Disegno effettivo: il giocatore rimane fisso in screenX/screenY (centro dello schermo)
        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
    }

    // --- GETTERS PER LA POSIZIONE DELLO SCHERMO ---

    /** @return La posizione X del giocatore relativa allo schermo. */
    public int getScreenX() { return screenX; }
    
    /** @return La posizione Y del giocatore relativa allo schermo. */
    public int getScreenY() { return screenY; }
}