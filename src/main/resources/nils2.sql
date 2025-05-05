
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

-- hat bestellung:      wellensteyn
-- keine bestellung:    urocki
SET @target_username = 'wellensteyn';
SET @cutoff_date = DATE_SUB(CURDATE(), INTERVAL 10 YEAR);

SELECT KUNDENNR
FROM KUNDE
WHERE USERNAME = @target_username
  AND KUNDENNR NOT IN (
    SELECT KUNDENNR
    FROM BESTELLUNG
    WHERE BESTELLDATUM >= @cutoff_date
);

-- wenn das null returned
-- delete

-- wenn bestellung inerhalb der letzten 10 jahren
--   wenn bestellung mehr als 10 jahre, dann kundenNr löschen
--   LOGIN LÖSCHEN
--   KUNDE (geburstdatum, email, telefon, abo=false) anonymisieren, username zu null



-- wenn KEINE bestellung inerhalb der letzten 10 jahren
--   zuerst kundennr löschen
--   LOGIN LÖSCHEN
--   KUNDE LÖSCHEN

-- auskunt über userdaten nach DSGVO
SELECT
    k.KUNDENNR,
    k.VORNAME,
    k.NACHNAME,
    k.GEBURTSDATUM,
    k.TELEFON,
    k.EMAIL,
    k.ABO,
    a.STRASSE,
    a.HAURSNR,
    a.POSTLEITZAHL,
    a.ORT,
    a.STADT,
    a.LAND,
    l.USERNAME
FROM KUNDE k
         LEFT JOIN ADRESSE a ON k.ADRESSNR = a.ADRESSENR
         LEFT JOIN LOGIN l ON k.USERNAME = l.USERNAME
WHERE k.KUNDENNR = 2001; -- KundenNr 2001-2009

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


