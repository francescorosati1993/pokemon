🎮 Pokémon Adventure (Java Edition)
Pokémon Adventure è un videogioco di ruolo (RPG) con visuale dall'alto, sviluppato interamente in Java utilizzando la libreria Swing. Il progetto si pone l'obiettivo di ricreare l'estetica e il feeling dei classici titoli Pokémon per console portatili, implementando da zero i motori core di un engine di gioco 2D.

🛠️ Caratteristiche Tecniche
Il gioco si basa su un'architettura robusta e modulare:

Custom Game Engine: Un loop di gioco sincronizzato a 60 FPS costanti tramite gestione del Delta Timing, che garantisce fluidità di movimento indipendentemente dalla potenza del processore.

Sistema di Rendering a Livelli: Gestione avanzata dei tile con algoritmo di Frustum Culling, che renderizza solo gli elementi visibili a schermo per ottimizzare l'uso della memoria e della CPU.

Gestione Telecamera Dinamica: Il mondo di gioco (World Map) è vasto, ma la visuale rimane centrata sul giocatore, creando un effetto di scorrimento fluido del terreno.

Collision Engine: Un sistema di rilevamento delle collisioni basato su matrici, che impedisce al giocatore di attraversare elementi solidi come acqua, edifici o ostacoli naturali.

Animazioni Sprite: Gestione degli stati dell'entità (Camminata, Idle) con cambio dinamico delle immagini basato sul tempo trascorso.

🗺️ Il Mondo di Gioco
L'avventura si svolge in una mappa di 50x50 tile, caricata dinamicamente da file esterni. Il giocatore può esplorare diverse tipologie di terreno, ognuna con le proprie caratteristiche fisiche e visive, dai sentieri in ghiaia ai prati fioriti, fino ai bordi dei laghi.

Prossimi Step dello Sviluppo
Il progetto è in continua evoluzione. Le prossime implementazioni previste includono:

NPC System: Introduzione di personaggi non giocanti con percorsi di movimento predefiniti.

Event Trigger: Interazione con oggetti (es. cartelli, porte, oggetti a terra).

UI & Dialoghi: Un sistema di messaggistica a schermo per la narrazione.
