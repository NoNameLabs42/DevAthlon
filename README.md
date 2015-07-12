# DevAthlon
Unsere Teilnahme am 2. DevAthlon!

Projekt Runde 1 - PacCraft
Minecraft Version: 1.8.7

Beschreibung:
PacCraft ist ein Minispiel, was an Pacman anglehnt ist. In PacCraft gibt es zwei Arten von Spielern: die PacMans und die Geister.
Die PacMans haben die Aufgabe Coins zu sammeln und sich nicht von den Geistern "fressen" zu lassen, denn wenn das passiert, verlieren
sie 25% ihrer Punkte(Coins) und die verlorenen Punkte werden dem Geist zugeschrieben.
Die Geister müssen die PacMans töten um so an ihre Punkte zu kommen. 
Auf der ganzen Map verteilt spawnen Coins und PowerUps, welche je nach Spielertyp eine andere Wirkung haben:
So können PacMans, die ein PowerUp eingesammelt haben, Geister töten und Geister können dann Coins einsammeln.
Mit den gesammelten Punkten kann man sich im Shop Items und Buffs kaufen.

Nach einer Spielzeit von 5 Minuten wird das Spiel beendet und der Spieler mit den meisten Punkten gewinnt.

Konfiguration:
InGame:
Mit /addplayerspawn wird ein Spawnpunkt für die Spielerfraktion hinzugefügt.
Es können mehr Spawn für Spieler vorhanden sein als Spieler, aber nicht mehr Spieler als Spielerspawns.
Mit /resetplayerspawn werden ALLE Spawnpunkte der Spieler entfernt.
Mit /setghostspawn wird der Spawnpunkt für die Geister gesetzt.
Es gibt einen Spawn für alle Geister..
Mit /startgame <Anzahl Spieler> <Anzahl Geister> wird das Spiel mit den angegebenen Anzahlen an Spielern und Geistern gestartet, 
es müssen aber mindestens so viele Spieler wie angegeben online sein. Sollte die Anzahl an Spielern die online sind größer sein als die Anzahlen an
Spielern und Geistern zusammen, werden Spieler und Geister zufällig ausgewählt und die Restlichen werden zu Zuschauern.
Es müssen mindesten genauso viele Spawns wie angegebene Spieler vorhanden sein, sonst startet das Spiel nicht.
Mit /stopgame kann man das Spiel eindach wieder beenden.
Mit /mapcorner1 und /mapcorner2 werden die Ecken des Spielfeldes gesetzt, das muss gemacht werden damit die einsammelbaren Coins und PowerUps auf Barrieren- und Holzblöcken
zu 25% spawnen und PowerUps zu 0.5% spawnen können.
Mit dem Befehl /togglePvP kann man einstellen ob normale Spieler sich schlagen dürfen oder nicht.


Um nicht extra eine Map zu bauen, wird die von uns selbstgebaute Map emfhohlen. Download Link:
http://workupload.com/file/6X7FyfGp

Wenn unsere Map zum testen benutzt wird muss folgendes Archiv als entpackte Datei in den PlugIns-Ordner des Servers
eingefügt werden, sodass im PlugIns-Ordner der DevAthlon-Ordner vorhanden ist. Wenn sich in diesem DevAthlon Ordner die Datei "config.yml" befindet, dann hast du keinen Felher gemacht. Ist jedoch im DevAthlon-Ordner ein zweiter DevAthlon-Ordner enthalten, musst du die "config.yml" aus dem zweiten DevAthlon-Ordner rauskopieren und in den ersten DevAthlon-Ordner einfügen, anschließend kann der zweite DevAthlon-Ordner gelöscht werden. Das muss gmeacht werden, damit die Spawnpunkte und Mapecken nicht noch mal neu gesetzt werden müssen, sondern aus der Datei entnommen werden. Download Link:
http://workupload.com/file/cLHRrXRT

Ebenso ist ein ausführliches Handbuch, wo alles noch viel genauer erklärt wird, vorhanden. Download Link:
http://workupload.com/file/NKk36GnJ

Empholene Spieleinstellungen:
-PvP aus
- 1/5 der Spieler sind Geister

Alternativer Donwload Link für das Plugin:
http://workupload.com/file/89CnZ8ML
