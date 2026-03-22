package com.fr.pokemon.model.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Classe astratta di base che rappresenta un'entità generica all'interno del mondo di gioco.
 * <p>
 * Definisce le proprietà comuni a tutti gli oggetti dinamici (giocatori, NPC, mostri),
 * includendo la gestione della posizione nel mondo, le animazioni basate sugli sprite 
 * e i dati relativi alle collisioni fisiche.
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class Entity 
{
    // --- COORDINATE NEL MONDO (World Coordinates) ---
    protected int worldX; // Coordinata X assoluta dell'entità all'interno della mappa globale
    protected int worldY; // Coordinata Y assoluta dell'entità all'interno della mappa globale
    protected int speed;  // Velocità di movimento dell'entità (pixel per frame)

    // --- IMMAGINI E ANIMAZIONE ---
    /** Buffer per le immagini dei vari stati di movimento. */
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    /** Buffer per le immagini dell'entità in stato di riposo (idle). */
    protected BufferedImage stayUp, stayDown, stayLeft, stayRight;
    /** Stringa che identifica la direzione corrente (es. "up", "down", "left", "right"). */
    protected String direction;

    protected int spriteCounter = 0; // Contatore di frame per gestire il timing dello switch delle immagini
    protected int spriteNum = 1;     // Identificativo dell'immagine corrente nel ciclo di animazione (1 o 2)

    // --- COLLISIONE ---
    /** Rettangolo che definisce l'area solida dell'entità per il calcolo delle collisioni. */
    protected Rectangle solidArea; 
    /** Flag che indica se l'entità sta attualmente collidendo con un oggetto solido. */
    protected boolean collisionOn = false;

    // --- METODI ACCESSORI (GETTERS E SETTERS) ---

    /** @return La posizione X dell'entità nella mappa globale. */
    public int getWorldX() { return worldX; }
    
    /** @return La posizione Y dell'entità nella mappa globale. */
    public int getWorldY() { return worldY; }
    
    /** @return La velocità di spostamento dell'entità. */
    public int getSpeed() { return speed; }
    
    /** @return La direzione di movimento o orientamento attuale. */
    public String getDirection() { return direction; }
    
    /** @return Il rettangolo di collisione dell'entità. */
    public Rectangle getSolidArea() { return solidArea; }
    
    /**
     * Imposta lo stato della collisione.
     * @param collisionOn true se è stata rilevata una collisione, altrimenti false.
     */
    public void setCollisionOn(boolean collisionOn) 
    {
        this.collisionOn = collisionOn;
    }
    
    /**
     * Verifica se l'entità è attualmente in collisione.
     * @return true se la collisione è attiva, false altrimenti.
     */
    public boolean isCollisionOn() 
    {
        return collisionOn;
    }
}