package com.fr.pokemon.view.tile;

import java.awt.image.BufferedImage;

/**
 * Rappresenta un singolo tassello (Tile) del mondo di gioco.
 * <p>
 * Questa classe è un contenitore di dati che definisce le proprietà visive 
 * e fisiche di una specifica tipologia di terreno (es. erba, acqua, muro).
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class Tile 
{
    /** L'immagine grafica del tassello caricata in memoria. */
    public BufferedImage image;

    /** * Flag che determina se il tassello è solido.
     * Se impostato a true, le entità non potranno attraversarlo.
     */
    public boolean collision = false;

    /**
     * Costruttore predefinito della classe Tile.
     * Inizializza un tassello base senza immagine e con collisione disattivata.
     */
    public Tile() 
    {
        // Costruttore vuoto per l'istanziazione dinamica
    }
}