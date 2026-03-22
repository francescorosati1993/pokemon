package com.fr.pokemon.controller;

import com.fr.pokemon.model.entity.Entity;
import com.fr.pokemon.view.GamePanel;
import com.fr.pokemon.view.tile.Tile;

/**
 * Gestore del sistema di rilevamento delle collisioni del gioco.
 * <p>
 * Questa classe analizza la posizione delle entità rispetto alla griglia dei tile 
 * del mondo di gioco. Calcola preventivamente se il movimento successivo di un'entità 
 * causerà una sovrapposizione con un tile marcato come solido.
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class CollisionChecker 
{
    private GamePanel gp; // Riferimento al pannello principale per accedere ai dati della mappa

    /**
     * Costruisce un CollisionChecker collegato all'istanza corrente del gioco.
     * @param gp Il {@link GamePanel} da cui recuperare le informazioni sui tile.
     */
    public CollisionChecker(GamePanel gp) 
    {
        this.gp = gp;
    }

    /**
     * Verifica se l'entità specificata sta entrando in collisione con un tile solido.
     * <p>
     * Il metodo calcola le coordinate della "Solid Area" dell'entità nel mondo, 
     * prevede la sua posizione futura in base alla velocità e direzione, 
     * e controlla i due tile che l'entità andrebbe a toccare.
     * </p>
     * * @param entity L'{@link Entity} di cui controllare la collisione.
     */
    public void checkTile(Entity entity) 
    {
        // Recuperiamo la matrice numerica della mappa (indici dei tile)
        int[][] map = gp.getTileM().getMapTileNum();
        // Recuperiamo l'array dei tipi di tile disponibili (erba, acqua, muro, ecc.)
        Tile[] tileSet = gp.getTileM().getTile();

        // --- CALCOLO DEI BORDI DELLA SOLID AREA (HITBOX) NEL MONDO ---
        
        // Coordinata X del bordo sinistro della hitbox nel mondo
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        // Coordinata X del bordo destro della hitbox nel mondo
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        // Coordinata Y del bordo superiore della hitbox nel mondo
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        // Coordinata Y del bordo inferiore della hitbox nel mondo
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        // --- CONVERSIONE COORDINATE PIXEL -> INDICI GRIGLIA (COLONNE/RIGHE) ---
        
        // Indice della colonna sinistra occupata dall'entità
        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        // Indice della colonna destra occupata dall'entità
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        // Indice della riga superiore occupata dall'entità
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        // Indice della riga inferiore occupata dall'entità
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        // Variabili per identificare i numeri dei tile nelle posizioni di test
        int tileNum1, tileNum2; 

        // Analizziamo la direzione per prevedere la collisione nel prossimo spostamento
        switch (entity.getDirection()) 
        {
            case "up":
                // Calcoliamo la riga superiore dove l'entità si troverebbe dopo il movimento
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
                
                // Se la riga calcolata è fuori dai limiti superiori della mappa
                if (entityTopRow < 0) 
                {
                    entity.setCollisionOn(true); // Blocca il movimento
                } 
                else 
                {
                    // Identifichiamo i due tile che i bordi superiori della hitbox toccherebbero
                    tileNum1 = map[entityLeftCol][entityTopRow];
                    tileNum2 = map[entityRightCol][entityTopRow];
                    
                    // Se uno dei due tile prevede collisione (es. è un muro o acqua)
                    if (tileSet[tileNum1].collision || tileSet[tileNum2].collision) 
                    {
                        entity.setCollisionOn(true); // Attiva il flag di collisione dell'entità
                    }
                }
                break;

            case "down":
                // Calcoliamo la riga inferiore dove l'entità si troverebbe dopo il movimento
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
                
                // Se la riga calcolata supera il limite inferiore della mappa
                if (entityBottomRow >= gp.getMaxWorldRow()) 
                {
                    entity.setCollisionOn(true);
                } 
                else 
                {
                    // Controlliamo i tile che i bordi inferiori della hitbox andrebbero a toccare
                    tileNum1 = map[entityLeftCol][entityBottomRow];
                    tileNum2 = map[entityRightCol][entityBottomRow];
                    
                    if (tileSet[tileNum1].collision || tileSet[tileNum2].collision) 
                    {
                        entity.setCollisionOn(true);
                    }
                }
                break;

            case "left":
                // Calcoliamo la colonna sinistra dove l'entità si troverebbe dopo il movimento
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
                
                // Se la colonna calcolata è fuori dal limite sinistro della mappa
                if (entityLeftCol < 0) 
                {
                    entity.setCollisionOn(true);
                } 
                else 
                {
                    // Controlliamo i tile che i bordi sinistri della hitbox andrebbero a toccare
                    tileNum1 = map[entityLeftCol][entityTopRow];
                    tileNum2 = map[entityLeftCol][entityBottomRow];
                    
                    if (tileSet[tileNum1].collision || tileSet[tileNum2].collision) 
                    {
                        entity.setCollisionOn(true);
                    }
                }
                break;

            case "right":
                // Calcoliamo la colonna destra dove l'entità si troverebbe dopo il movimento
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
                
                // Se la colonna calcolata supera il limite destro della mappa
                if (entityRightCol >= gp.getMaxWorldCol()) 
                {
                    entity.setCollisionOn(true);
                } 
                else 
                {
                    // Controlliamo i tile che i bordi destri della hitbox andrebbero a toccare
                    tileNum1 = map[entityRightCol][entityTopRow];
                    tileNum2 = map[entityRightCol][entityBottomRow];
                    
                    if (tileSet[tileNum1].collision || tileSet[tileNum2].collision) 
                    {
                        entity.setCollisionOn(true);
                    }
                }
                break;
        }
    }
}