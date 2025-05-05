# Auswahl aller Zutaten eines Rezepts nach RezeptNr
SET @RezeptNr = 1;

SELECT * FROM ZUTAT z
JOIN REZEPT_ZUTAT rz
ON z.ZUTATNR = rz.ZUTATNR
WHERE rz.REZEPTNR = @RezeptNr;



# Auswahl aller Zutaten eines Rezepts nach Name (nicht zwingend Eindeutig)
SET @RezeptName = 'Lachslasagne';

SELECT * FROM ZUTAT z
JOIN REZEPT_ZUTAT rz
ON z.ZUTATNR = rz.ZUTATNR
WHERE rz.REZEPTNR IN (
	SELECT r.REZEPTNR FROM REZEPT r
	WHERE r.`NAME` = @RezeptName
);



# Auswahl aller Rezepte einer bestimmten Ernährungskategorie
SET @KategorieBezeichnung = 'frutarisch';  -- 'frutarisch', 'vegan', 'vegetarisch', 'fleisch essend'
SET @KategorieTyp = 'ernährungsart';

SELECT r.*
FROM REZEPT r
WHERE NOT EXISTS (
    SELECT 1
    FROM REZEPT_ZUTAT rz
    WHERE rz.REZEPTNR = r.REZEPTNR
      AND rz.ZUTATNR NOT IN (
        select z.ZUTATNR from ZUTAT z
            join ZUTAT_ERNAEHRUNGSKATEGORIE ze on z.ZUTATNR = ze.ZUTATNR
        where ze.ERNAEHRUNGSKATNR in (
            SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
            WHERE e.PRIORITAET >= (
                SELECT e2.PRIORITAET
                FROM ERNAEHRUNGSKATEGORIE e2
                WHERE e2.BEZEICHNUNG = @KategorieBezeichnung
            ) AND e.TYP = @KategorieTyp
        )
    )
);



# Rezepte nach verwendeten Zutaten filtern
SELECT * FROM REZEPT r
WHERE r.RezeptNr IN (
   SELECT rz.RezeptNr FROM REZEPT_ZUTAT rz
	WHERE rz.ZutatNr IN (1001) -- Hier beliebige ZutatenNr angeben
);



# Berechnung der durschnittlichen Kalorien einer Bestellung
DROP VIEW IF EXISTS Totals;

CREATE VIEW Totals AS
SELECT SUM((z.Kalorien / 100) * rz.MENGE) AS Total FROM ZUTAT z
JOIN REZEPT_ZUTAT rz
ON z.ZUTATNR = rz.ZUTATNR
WHERE rz.REZEPTNR IN (
	SELECT br.REZEPTNR FROM BESTELLUNG_REZEPT br
	WHERE br.BESTELLNR IN (1001) -- Hier beliebige BestellNr angeben
)
GROUP BY rz.REZEPTNR;

SELECT AVG(t.Total) FROM Totals AS t;



# Die meistbestellten Rezepte
SELECT br.REZEPTNR, COUNT(br.REZEPTNR) AS Haeufigkeit FROM BESTELLUNG_REZEPT br
JOIN REZEPT r
ON br.REZEPTNR = r.REZEPTNR
GROUP BY br.REZEPTNR
ORDER BY Haeufigkeit DESC;



# Auswahl aller Rezepte, die eine bestimmte Kalorienmenge nicht überschreiten 
SET @MaxKalorien = 700;

SELECT
    r.*
FROM REZEPT r
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.KALORIEN / 100.0) * rz.MENGE) <= @MaxKalorien
);



# Auswahl aller Rezepte, die weniger als fünf Zutaten enthalten 
SET @MaxAnzahlZutaten = 5;

SELECT
    r.*
FROM REZEPT r
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING COUNT(DISTINCT rz.ZUTATNR) <= @MaxAnzahlZutaten
);



# Auswahl aller Rezepte, die weniger als fünf Zutaten enthalten und eine bestimmte Ernährungskategorie erfüllen
SET @KategorieBezeichnung = 'frutarisch'; -- 'frutarisch', 'vegan', 'vegetarisch', 'fleisch essend'
SET @KategorieTyp = 'ernährungsart';
SET @MaxAnzahlZutaten = 4;

SELECT r.*
FROM REZEPT r
WHERE NOT EXISTS (
    SELECT 1
    FROM REZEPT_ZUTAT rz
    WHERE rz.REZEPTNR = r.REZEPTNR
      AND rz.ZUTATNR NOT IN (
        SELECT z.ZUTATNR FROM ZUTAT z
              JOIN ZUTAT_ERNAEHRUNGSKATEGORIE ze ON z.ZUTATNR = ze.ZUTATNR
        WHERE ze.ERNAEHRUNGSKATNR IN (
            SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
            WHERE e.PRIORITAET >= (
                SELECT e2.PRIORITAET
                FROM ERNAEHRUNGSKATEGORIE e2
                WHERE e2.BEZEICHNUNG = @KategorieBezeichnung
            ) AND e.TYP = @KategorieTyp
        )
    )
) AND r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING COUNT(DISTINCT rz.ZUTATNR) <= @MaxAnzahlZutaten -- belibige zutaten anzahl
);



# Weitere Statements:

-- zeige alle kunden mit ihren bestellungen an
SELECT
    k.KUNDENNR,
    k.VORNAME,
    k.NACHNAME,
    b.BESTELLNR,
    b.BESTELLDATUM
FROM
    KUNDE k
        LEFT JOIN
    BESTELLUNG b ON k.KUNDENNR = b.KUNDENNR;



-- user löschen nach DSGVO

-- hat bestellung:      wellensteyn
-- keine bestellung:    urocki
SET @target_username = 'urocki';
SET @cutoff_date = DATE_SUB(CURDATE(), INTERVAL 10 YEAR);
#SET @cutoff_date = DATE_SUB(CURDATE(), INTERVAL 8 DAY);

# Hilfe zum Daten auslesen
SELECT k.KUNDENNR
FROM KUNDE k
WHERE USERNAME = @target_username
  AND KUNDENNR NOT IN (
    SELECT b.KUNDENNR
    FROM BESTELLUNG b
    WHERE b.BESTELLDATUM >= @cutoff_date
);

SELECT @cutoff_date;

SELECT * FROM KUNDE k
WHERE k.USERNAME = @target_username;

SELECT * FROM BESTELLUNG b;

SELECT * FROM BESTELLUNG b
JOIN KUNDE k
ON b.KUNDENNR = k.KUNDENNR
WHERE k.KUNDENNR = 2001;

SELECT * FROM LOGIN l
WHERE l.Username = @target_username;

SELECT * FROM BESTELLUNG b
JOIN KUNDE k
ON k.KUNDENNR = b.KUNDENNR
WHERE b.BESTELLDATUM < @cutoff_date AND k.USERNAME = @target_username;
# Hilfe Ende


# Löscht alle Userdaten außer Namens- und Adressinformationen
# Löscht die Kundennummer in Bestellungen vor dem Cutoff-date (wegen 10 Jahre Aufbewahrungsfrist)
-- hat bestellung:      wellensteyn
-- keine bestellung:    urocki
SET @target_username = 'urocki';
SET @cutoff_date = DATE_SUB(CURDATE(), INTERVAL 10 YEAR);

UPDATE BESTELLUNG b
JOIN KUNDE k
ON k.KUNDENNR = b.KUNDENNR
SET b.KUNDENNR = NULL
WHERE b.BESTELLDATUM < @cutoff_date AND k.USERNAME = @target_username;
UPDATE KUNDE k2
SET k2.Geburtsdatum = NULL, k2.email = NULL, k2.telefon = NULL, k2.abo = FALSE, k2.Username = NULL
WHERE k2.Username = @target_username;
DELETE FROM LOGIN
WHERE USERNAME = @target_username;


# Löscht alle Kundendaten
# Nur zu verwenden, wenn keine Bestellungen noch Aufbewahrungspflicht haben
SET @target_username = 'wellensteyn';

UPDATE BESTELLUNG b
JOIN KUNDE k
ON b.KUNDENNR = k.KUNDENNR
SET b.KUNDENNR = NULL
WHERE k.USERNAME = @target_username;
DELETE FROM KUNDE
WHERE USERNAME = @target_username;
DELETE FROM LOGIN
WHERE USERNAME = @target_username;








