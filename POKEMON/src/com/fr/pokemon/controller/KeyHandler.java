package com.fr.pokemon.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestore degli eventi della tastiera per il controllo del gioco.
 * <p>
 * Implementa l'interfaccia {@link KeyListener} per intercettare la pressione e il 
 * rilascio dei tasti. Supporta sia i tasti direzionali (frecce) che il sistema WASD.
 * </p>
 * * @author Francesco Rosati
 * @version 1.0
 */
public class KeyHandler implements KeyListener 
{
    // --- STATI DEI TASTI (Flag Booleani) ---
    private boolean upPressed;    // Indica se il tasto per andare su è premuto
    private boolean downPressed;  // Indica se il tasto per andare giù è premuto
    private boolean leftPressed;  // Indica se il tasto per andare a sinistra è premuto
    private boolean rightPressed; // Indica se il tasto per andare a destra è premuto

    /**
     * Metodo richiesto dall'interfaccia KeyListener. 
     * Non utilizzato in questa implementazione specifica.
     */
    @Override
    public void keyTyped(KeyEvent e) 
    {
        // Non utilizzato
    }

    /**
     * Rileva quando un tasto viene premuto sulla tastiera.
     * Imposta a true il flag corrispondente alla direzione desiderata.
     * * @param e L'evento della tastiera generato dal sistema.
     */
    @Override
    public void keyPressed(KeyEvent e) 
    {
        // Otteniamo il codice numerico associato al tasto premuto
        int code = e.getKeyCode();

        // Controllo direzione SU (W o Freccia Su)
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) 
        {
            upPressed = true;
        }
        
        // Controllo direzione GIÙ (S o Freccia Giù)
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) 
        {
            downPressed = true;
        }
        
        // Controllo direzione SINISTRA (A o Freccia Sinistra)
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) 
        {
            leftPressed = true;
        }
        
        // Controllo direzione DESTRA (D o Freccia Destra)
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) 
        {
            rightPressed = true;
        }
    }

    /**
     * Rileva quando un tasto viene rilasciato sulla tastiera.
     * Imposta a false il flag corrispondente per interrompere il movimento.
     * * @param e L'evento della tastiera generato dal sistema.
     */
    @Override
    public void keyReleased(KeyEvent e) 
    {
        // Otteniamo il codice numerico associato al tasto rilasciato
        int code = e.getKeyCode();

        // Rilascio direzione SU
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) 
        {
            upPressed = false;
        }
        
        // Rilascio direzione GIÙ
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) 
        {
            downPressed = false;
        }
        
        // Rilascio direzione SINISTRA
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) 
        {
            leftPressed = false;
        }
        
        // Rilascio direzione DESTRA
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) 
        {
            rightPressed = false;
        }
    }

    // --- GETTERS PER L'INCAPSULAMENTO ---

    /** @return true se il giocatore sta premendo verso l'alto. */
    public boolean isUpPressed() { return upPressed; }
    
    /** @return true se il giocatore sta premendo verso il basso. */
    public boolean isDownPressed() { return downPressed; }
    
    /** @return true se il giocatore sta premendo verso sinistra. */
    public boolean isLeftPressed() { return leftPressed; }
    
    /** @return true se il giocatore sta premendo verso destra. */
    public boolean isRightPressed() { return rightPressed; }
}