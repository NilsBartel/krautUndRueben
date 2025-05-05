# Auswahl aller Zutaten eines Rezepts nach RezeptNr
SELECT * FROM ZUTAT z
JOIN REZEPT_ZUTAT rz
ON z.ZUTATNR = rz.ZUTATNR
WHERE rz.REZEPTNR = 1; -- Hier beliebige RezeptNr einfügen

# Auswahl aller Zutaten eines Rezepts nach Name
SELECT * FROM ZUTAT z
JOIN REZEPT_ZUTAT rz
ON z.ZUTATNR = rz.ZUTATNR
WHERE rz.REZEPTNR IN (
	SELECT r.REZEPTNR FROM REZEPT r
	WHERE r.`NAME` = 'Lachslasagne' -- Hier beliebigen Namen einfügen
);

# Auswahl aller Rezepte einer bestimmten Ernährungskategorie
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
                WHERE e2.BEZEICHNUNG = 'frutarisch' -- 'frutarisch', 'vegan', 'vegetarisch', 'fleisch essend'
            ) AND e.TYP = 'ernährungsart'
        )
    )
);

# Rezepte nach verwendeten Zutaten filtern
SELECT * FROM REZEPT r
WHERE r.RezeptNr IN (
   SELECT rz.RezeptNr FROM REZEPT_ZUTAT rz
	WHERE rz.ZutatNr IN (1001) -- Hier beliebige ZutatenNr angeben
);

# Berechnung der durschnittlichen Nährwerte einer Bestellung
DROP VIEW Totals;
CREATE VIEW Totals AS
SELECT SUM((z.Kalorien / 100) * rz.MENGE) AS Total FROM ZUTAT z
JOIN REZEPT_ZUTAT rz
ON z.ZUTATNR = rz.ZUTATNR
WHERE rz.REZEPTNR IN (
	SELECT br.REZEPTNR FROM BESTELLUNG_REZEPT br
	WHERE br.BESTELLNR IN (1002) -- Hier beliebige BestellNr angeben
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
    HAVING SUM((z.KALORIEN / 100.0) * rz.MENGE) <= 700
);

# Auswahl aller Rezepte, die weniger als fünf Zutaten enthalten 
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
    HAVING COUNT(DISTINCT rz.ZUTATNR) < 6
);

# Auswahl aller Rezepte, die weniger als fünf Zutaten enthalten und eine bestimmte Ernährungskategorie erfüllen
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
                WHERE e2.BEZEICHNUNG = 'frutarisch' -- 'frutarisch', 'vegan', 'vegetarisch', 'fleisch essend'
            ) AND e.TYP = 'ernährungsart'
        )
    )
) AND r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING COUNT(DISTINCT rz.ZUTATNR) < 6 -- belibige zutaten anzahl
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
    

    



-- LACHSLASAGNE KALORIEN
-- spinat: 46
-- lachs: 250
-- nudeln: 60
-- mehl: 4,5
-- sahne: 75
-- parmesan: 14
-- zitrone: 12
-- käse: 148
-- milch: 40
-- buttler: 89
-- brühe: 0,3
-- = ca. 738,5






