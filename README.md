# Fog System
Layer intermedio tra l'**edge** e il **cloud** **centralizzato**.

Non è un livello obbligato, ma la sua presenza abilita funzionalità molto importanti,
come lo shadowing dell'edge, mascherando la complessità della casina e abilitando una comunicazione più robusta e stabile con i client.
Questo livello richiede maggiori risorse rispetto all'edge; ad uno stesso Fog possono essere collegati più edge.

#### Software Info

![GitHub](https://img.shields.io/github/license/PC-ProgettoMIA/fog)
![GitHub language count](https://img.shields.io/github/languages/count/PC-ProgettoMIA/fog)
![GitHub top language](https://img.shields.io/github/languages/top/PC-ProgettoMIA/fog)
![GitHub pull requests](https://img.shields.io/github/issues-pr/PC-ProgettoMIA/fog)
![GitHub issues](https://img.shields.io/github/issues/PC-ProgettoMIA/fog)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/PC-ProgettoMIA/fog)
![GitHub repo size](https://img.shields.io/github/repo-size/PC-ProgettoMIA/fog)
![GitHub contributors](https://img.shields.io/github/contributors/PC-ProgettoMIA/fog)

#### Software Progress
![GitHub issues](https://img.shields.io/github/issues/PC-ProgettoMIA/fog)
![GitHub closed issues](https://img.shields.io/github/issues-closed/PC-ProgettoMIA/fog)
![GitHub pull requests](https://img.shields.io/github/issues-pr/PC-ProgettoMIA/fog)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/PC-ProgettoMIA/fog)
![GitHub commits since latest release (by date)](https://img.shields.io/github/commits-since/PC-ProgettoMIA/fog/latest/develop)
![GitHub last commit](https://img.shields.io/github/last-commit/PC-ProgettoMIA/fog/develop)


## Requirements

Il **_fog_** richiede un host risorse hardware di un normale host casalingo:
- RAM: 8 GB
- HD: 128 GB, in modo da avere abbastanza memoria per poter memorizzare i dati di più casine.

L'host richiede un sistema operativo **Unix** per il deployment del sistema tramite gli script  predefiniti
<!--, altrimenti su un device Windows richiede di lanciare i comandi compatibili tramite GitBash-->

## Deployment
Il deployment può essere effettuato in due modalità in base alle condizioni di utilizzo del sistema.

### Caso edge e fog 
In assenza del cloud e della messa in funzione di una o più casette e un singolo fog, seguire i seguenti passaggi:
```bash
#Abilitare i permessi per l'esecuzione dello script.
chmod 755 fog.sh
#Esecuzione per l'avvio del servizio
./fog.sh
```

In questo modo viene effettuato il deployment solo di:
- subscriber MQTT 
- server per esporre la Rest API sulla rete locale, in modo da essere raggiunta facilmente dagli applicativi Snap!


### Caso edge, fog e cloud
In presenza del cloud, seguire i seguenti passaggi:
```bash
#Abilitare i permessi per l'esecuzione dello script.
chmod 755 fog-cloud.sh
#Esecuzione per l'avvio del servizio
./fog-cloud.sh
```

In questo modo viene effettuato il deployment:
- del subscriber MQTT 
- del server per esporre la Rest API sulla rete locale 
- l'applicativo per inviare i dati al cloud del Progetto-MIA.



# License
See the [License File](./LICENSE).

## Author and Copyright
Author:
- [Battistini Ylenia](https://github.com/yleniaBattistini)
- [Gnagnarella Enrico](https://github.com/enrignagna)
- [Scucchia Matteo](https://github.com/scumatteo)

Copyright (c) 2021.
