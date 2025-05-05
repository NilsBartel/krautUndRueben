
-- Auswahl aller Zutaten eines Rezeptes nach Rezeptname
SELECT * FROM REZEPT_ZUTAT rz
    JOIN ZUTAT z
       ON rz.ZutatNr = z.ZutatNr
WHERE rz.RezeptNr IN (1); -- belibige RezeptNr


-- Auswahl aller Rezepte einer bestimmten Ernährungskategorie
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
);

-- Auswahl aller Rezepte, die eine gewisse Zutat enthalten
SELECT * FROM REZEPT r
WHERE r.RezeptNr IN (
    SELECT rz.RezeptNr FROM REZEPT_ZUTAT rz
    WHERE rz.ZutatNr = 1001 -- belibige zutatNr
);

-- Berechnung der durchschnittlichen Nährwerte aller Bestellungen eines Kunden

-- Meist gekaufte rezepte

-- Auswahl aller Rezepte, die eine bestimmte Kalorienmenge nicht überschreiten
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
    HAVING SUM((z.KALORIEN / 100.0) * rz.MENGE) < 700
);

-- Auswahl aller Rezepte, die weniger als fünf Zutaten enthalten
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

-- Auswahl aller Rezepte, die weniger als fünf Zutaten enthalten und eine bestimmte Ernährungskategorie erfüllen
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

-- user löschen nach DSGVO

-- auskunt über userdaten nach DSGVO

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