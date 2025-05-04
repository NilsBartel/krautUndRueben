# Auswahl aller Zutaten eines Rezepts
SELECT * FROM REZEPT_ZUTAT rz
                  JOIN ZUTAT z
                       ON rz.ZutatNr = z.ZutatNr
                           # Hier beliebige RezeptNr einfügen
WHERE rz.RezeptNr IN (1);

# Auswahl aller Rezepte einer bestimmten Ernährungskategorie
SELECT * FROM REZEPT r
WHERE r.RezeptNr NOT IN (
    SELECT rz.RezeptNr FROM REZEPT_ZUTAT rz
    WHERE rz.ZutatNr IN (
        SELECT ze.ZUTATNR FROM ZUTAT_ERNAEHRUNGSKATEGORIE ze
                                   JOIN ZUTAT z
                                        ON ze.ZUTATNR = z.ZUTATNR
                                            # Hier gewählte Ernährungskategorie angeben
WHERE ze.ERNAEHRUNGSKATNR != 4 AND ze.ERNAEHRUNGSKATNR IN (
    SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
    # Hier Typ und Priorität der gewählten Ernährungskategorie angeben
    WHERE e.TYP = 'ernährungsart' AND e.PRIORITAET < 2
    )
    )
    );

# Rezepte nach verwendeten Zutaten filtern
SELECT * FROM REZEPT r
WHERE r.RezeptNr IN (
    SELECT rz.RezeptNr FROM REZEPT_ZUTAT rz
          # Hier beliebige ZutatenNr angeben
WHERE rz.ZutatNr IN (1013, 1015)
    );

# TODO - Berechnung der durschnittlichen Nährwerte einer Bestellung


# Rezepte nach Kalorien Filtern
SELECT * FROM REZEPT r
WHERE r.REZEPTNR IN (
    SELECT rz.REZEPTNR FROM REZEPT_ZUTAT rz
    WHERE (
              SELECT SUM(rz.MENGE * (z.KALORIEN / 100)) FROM ZUTAT z
              WHERE z.ZutatNr IN (
                  SELECT rz2.ZutatNr FROM REZEPT_ZUTAT rz2
                  WHERE rz2.RezeptNr = rz.REZEPTNR
              )
                        # Hier den gewünschten Kalorienfilter angeben
    ) >2000
    );







-- get recipe below certain KALORIEN amount
SELECT
    r.*
FROM REZEPT r
    JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
    JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
GROUP BY r.REZEPTNR, r.NAME, r.BESCHREIBUNG, r.VORGEHEN
HAVING SUM((z.KALORIEN / 100.0) * rz.MENGE) < 800; -- <- change amount

-- get KALORIEN amount of a recipe
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.KALORIEN / 100.0) * rz.MENGE) AS GESAMTKALORIEN
FROM REZEPT r
    JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
    JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- LACHSLASAGNE KALORIEN,     KOHLENHYDRATE
-- spinat:      46              4
-- lachs:       250             0
-- nudeln:      60              11.25
-- mehl:        4,5             1
-- sahne:       75              1.5
-- parmesan:    14              0
-- zitrone:     12              2
-- käse:        148             1
-- milch:       40
-- buttler:     89
-- brühe:       0,3
-- = ca.        738,5

-- get recipe below certain PROTEIN amount
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.PROTEIN / 100.0) * rz.MENGE) AS GESAMTKALORIEN
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- get PROTEIN amount of a recipe
SELECT
    r.*
FROM REZEPT r
WHERE REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.PROTEIN / 100.0) * rz.MENGE) < 50
);
-- subquery to get REZEPTNR for each recipe with protein sum below (amount)
-- then gets everything from that recipe

SELECT
    r.REZEPTNR
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
GROUP BY r.REZEPTNR
HAVING SUM((z.PROTEIN / 100.0) * rz.MENGE) < 50;



-- TODO: the one i want
SELECT
    r.*,
    z.BEZEICHNUNG
FROM REZEPT r
    JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
    JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
        JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
        JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.PROTEIN / 100.0) * rz.MENGE) < 46 AND SUM((z.KALORIEN / 100.0) * rz.MENGE) < 800 AND COUNT(DISTINCT rz.ZUTATNR) < 12
    LIMIT 1
);


-- TODO: trying co2 bilanz
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    -- HAVING SUM((z.CO2 / 1000.0) * rz.MENGE) < 1.7
);
-- co2 ausstoß per kg


-- TODO: get DATA for recipe
-- co2 ausstoß per kg
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.CO2 / 1000.0) * rz.MENGE) AS GESAMT_CO2
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- Kohlenhydrate of a recipe
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) AS GESAMT_KOHLENHYDRATE
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = 3  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- protein of a recipe
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.PROTEIN / 100.0) * rz.MENGE) AS GESAMT_KOHLENHYDRATE
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = 3  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- fett of a recipe
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.FETT / 100.0) * rz.MENGE) AS GESAMT_KOHLENHYDRATE
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = 3  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- kalorien of a recipe
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.KALORIEN / 100.0) * rz.MENGE) AS GESAMT_KOHLENHYDRATE
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = 3  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;




-- ---------------------------------------------------------------------------------------------------------------------
-- recipe based on limit

-- co2
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.CO2 / 1000.0) * rz.MENGE) < 1.7
);
-- subquery to get REZEPTNR for each recipe with protein sum below (amount)
-- then gets everything from that recipe

-- kohlenhydrate
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) < 60
);

-- protein
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.PROTEIN / 100.0) * rz.MENGE) < 50
);

-- fett
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.FETT / 100.0) * rz.MENGE) < 20
);

-- kalorien
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING SUM((z.KALORIEN / 100.0) * rz.MENGE) < 800
);


-- ZUTAT amount
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT
        r.REZEPTNR
    FROM REZEPT r
             JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
             JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
    GROUP BY r.REZEPTNR
    HAVING COUNT(DISTINCT rz.ZUTATNR) < 12
);

-- REZEPT amount
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR IN (
    SELECT REZEPTNR FROM ( -- workaround for: This version of MySQL doesn't yet support 'LIMIT & IN/ALL/ANY/SOME subquery'
        SELECT
         r.REZEPTNR
        FROM REZEPT r
              JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
              JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
        GROUP BY r.REZEPTNR
        HAVING COUNT(DISTINCT rz.ZUTATNR) < 12
        LIMIT 2
    ) AS LIMIT_REZEPTE
);


SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR IN (SELECT REZEPTNR FROM (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR HAVING COUNT(DISTINCT rz.ZUTATNR) < 12 LIMIT 2) AS LIMIT_REZEPTE);




-- ---------------------------------------------------------------------------------------------------------------------
-- get data for a recipe

-- co2 ausstoß per kg
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.CO2 / 1000.0) * rz.MENGE) AS GESAMT_CO2
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- kohlenhydrate
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) AS GESAMT_KOHLENHYDRATE
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- protein
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.PROTEIN / 100.0) * rz.MENGE) AS GESAMT_PROTEIN
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- fett
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.FETT / 100.0) * rz.MENGE) AS GESAMT_FETT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- kalorien
SELECT
    r.REZEPTNR,
    r.NAME,
    SUM((z.KALORIEN / 100.0) * rz.MENGE) AS GESAMT_KALORIEN
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
WHERE r.REZEPTNR = ?  -- <- change REZEPTNR
GROUP BY r.REZEPTNR, r.NAME;

-- ---------------------------------------------------------------------------------------------------------------------
-- LACHSLASAGNE KALORIEN,     KOHLENHYDRATE
-- spinat:      46              4
-- lachs:       250             0
-- nudeln:      60              11.25
-- mehl:        4,5             1
-- sahne:       75              1.5
-- parmesan:    14              0
-- zitrone:     12              2
-- käse:        148             1
-- milch:       40
-- buttler:     89
-- brühe:       0,3
-- = ca.        738,5

-- ---------------------------------------------------------------------------------------------------------------------
-- Testen der ernährungskategorie
-- TODO: this one works !!! gets all the recipes with a specific ernährungsart !!!
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
                WHERE e2.BEZEICHNUNG = 'frutarisch' -- the one to edit
            ) AND e.TYP = 'ernährungsart'
        )
    )
);




SELECT * FROM REZEPT r
WHERE r.RezeptNr NOT IN (
    SELECT rz.RezeptNr FROM REZEPT_ZUTAT rz
    WHERE rz.ZutatNr IN (
        SELECT ze.ZUTATNR FROM ZUTAT_ERNAEHRUNGSKATEGORIE ze
                                   JOIN ZUTAT z
                                        ON ze.ZUTATNR = z.ZUTATNR
        # Hier gewählte Ernährungskategorie angeben
        WHERE ze.ERNAEHRUNGSKATNR != 1 AND ze.ERNAEHRUNGSKATNR IN (
            SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
            # Hier Typ und Priorität der gewählten Ernährungskategorie angeben
            WHERE e.TYP = 'ernährungsart' AND e.PRIORITAET < 4
        )
    )
);




SELECT * FROM REZEPT r
WHERE r.RezeptNr IN (
    SELECT rz.RezeptNr FROM REZEPT_ZUTAT rz
    WHERE rz.ZutatNr IN (
        SELECT ze.ZUTATNR FROM ZUTAT_ERNAEHRUNGSKATEGORIE ze
               JOIN ZUTAT z
                ON ze.ZUTATNR = z.ZUTATNR
        WHERE ze.ERNAEHRUNGSKATNR IN (
            SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
            # Hier Typ und Priorität der gewählten Ernährungskategorie angeben
            WHERE e.TYP = 'ernährungsart' AND e.PRIORITAET >= 4
        )
    )
);







SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
# Hier Typ und Priorität der gewählten Ernährungskategorie angeben
WHERE e.TYP = 'ernährungsart' AND e.PRIORITAET >= 1;








-- TODO: this one works to get the ERNAEHRUNGSKATNR of all the right ones
SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
WHERE e.PRIORITAET >= (
    SELECT e2.PRIORITAET
    FROM ERNAEHRUNGSKATEGORIE e2
    WHERE e2.BEZEICHNUNG = 'vegan'
) AND e.TYP = 'ernährungsart';




-- TODO: Works to get every ZUTAT with the bezeichnung (vegan etc.) and higher
select z.ZUTATNR, z.BEZEICHNUNG from ZUTAT z
join ZUTAT_ERNAEHRUNGSKATEGORIE ze on z.ZUTATNR = ze.ZUTATNR
where ze.ERNAEHRUNGSKATNR in (
    SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e
    WHERE e.PRIORITAET >= (
        SELECT e2.PRIORITAET
        FROM ERNAEHRUNGSKATEGORIE e2
        WHERE e2.BEZEICHNUNG = 'fleisch essend' -- the one to edit
    ) AND e.TYP = 'ernährungsart'
)
;


-- TODO: this one works !!! gets all the recipes with a specific ernährungsart !!!
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
                    WHERE e2.BEZEICHNUNG = 'frutarisch' -- the one to edit
                ) AND e.TYP = 'ernährungsart'
            )
        )
);
-- together with all ingredients
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
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
                    WHERE e2.BEZEICHNUNG = 'fleisch essend' -- the one to edit
                ) AND e.TYP = 'ernährungsart'
            )
        )
);

-- ---------------------------------------------------------------------------------------------------------------------
-- trying to build the query together
-- TODO: !!!!!!!!! TEST THIS ONE WITH MORE RECIPES. !!!!!!!!!!!

-- TODO: the one im using
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
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
                WHERE e2.BEZEICHNUNG = 'frutarisch' -- the one to edit
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
    HAVING COUNT(DISTINCT rz.ZUTATNR) < 12

);





-- base part
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR

-- ernährungskategorie part
-- WHERE NOT EXISTS (SELECT 1 FROM REZEPT_ZUTAT rz WHERE rz.REZEPTNR = r.REZEPTNR AND rz.ZUTATNR NOT IN (select z.ZUTATNR from ZUTAT z join ZUTAT_ERNAEHRUNGSKATEGORIE ze on z.ZUTATNR = ze.ZUTATNR where ze.ERNAEHRUNGSKATNR in (SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e WHERE e.PRIORITAET >= (SELECT e2.PRIORITAET FROM ERNAEHRUNGSKATEGORIE e2 WHERE e2.BEZEICHNUNG = 'fleisch essend') AND e.TYP = 'ernährungsart')))



-- rest

  -- and needs to be added
  AND
                         -- WHERE

    r.REZEPTNR IN (
    SELECT REZEPTNR FROM (
                             SELECT
                                 r.REZEPTNR
                             FROM REZEPT r
                                      JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
                                      JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
                             GROUP BY r.REZEPTNR
                             HAVING COUNT(DISTINCT rz.ZUTATNR) < 12
                             LIMIT 2
                             -- need to add this dynamically
                         ) AS LIMIT_REZEPTE
);

-- ---------------------------------------------------------------------------------------------------------------------
-- BESTELLUNGEN:

-- Bestellung ansehen nach bestimmter Bestellnr
SELECT
    b.*,
    br.PORTIONEN,
    r.NAME,
    z.BEZEICHNUNG,
    CEIL((rz.MENGE * br.PORTIONEN) / z.GEWICHT) AS BENÖTIGTE_MENGE,
    z.NETTOPREIS * CEIL((rz.MENGE * br.PORTIONEN) / z.GEWICHT) AS GESAMTPREIS
FROM BESTELLUNG b
    JOIN BESTELLUNG_REZEPT br ON b.BESTELLNR = br.BESTELLNR
    JOIN REZEPT r on r.REZEPTNR = br.REZEPTNR
    JOIN REZEPT_ZUTAT rz on br.REZEPTNR = rz.REZEPTNR
    Join ZUTAT z on z.ZUTATNR = rz.ZUTATNR
WHERE b.BESTELLNR IN (1007); -- edit: bestellnr


-- bestellung einfügen
START TRANSACTION;
SELECT @next_bestellnr := MAX(BESTELLNR) + 1 FROM BESTELLUNG;

INSERT INTO BESTELLUNG (BESTELLNR, KUNDENNR, BESTELLDATUM)
SELECT
    @next_bestellnr,
    k.KUNDENNR,
    CURDATE()
FROM KUNDE k
JOIN LOGIN l on l.USERNAME = k.USERNAME
WHERE l.USERNAME = 'foede' -- edit: username
LIMIT 1;

-- do this part for every recipe
INSERT INTO BESTELLUNG_REZEPT (BESTELLNR, REZEPTNR, PORTIONEN)
SELECT
    @next_bestellnr,
    (SELECT r.REZEPTNR),
    4 -- edit: portionen
    FROM REZEPT r
        WHERE NAME = 'lachslasagne'; -- edit: rezept name

INSERT INTO BESTELLUNG_REZEPT (BESTELLNR, REZEPTNR, PORTIONEN)
SELECT
    @next_bestellnr,
    (SELECT r.REZEPTNR),
    3
    FROM REZEPT r
        WHERE NAME = 'Kartoffel-Gemüse-Pfanne';

COMMIT;





select
    B.*,
    BR.*,
    R.NAME
from BESTELLUNG B
join krautundrueben.BESTELLUNG_REZEPT BR on B.BESTELLNR = BR.BESTELLNR
join krautundrueben.REZEPT R on R.REZEPTNR = BR.REZEPTNR
order by B.BESTELLNR;

select *
from BESTELLUNG;


-- ---------------------------------------------------------------------------------------------------------------------
-- Account Auskunft nach DSGVO:











-- ---------------------------------------------------------------------------------------------------------------------
-- Account löschen:

-- alles in LOGIN, ABO, Username(in KUNDE)

-- gucken ob aufbewahrungsplicht (bestellung inerhalb der letzten 10 jahre
    -- wenn NEIN: alles in KUNDE und ADRESSE(wenn niemand anderes die addresse benutz)
        -- daten archivieren???

    -- wenn JA:
        -- anonymisieren: KUNDE: EMAIL, TELEFON, GEBURTSDATUM




START TRANSACTION;

DELETE FROM LOGIN WHERE USERNAME = 'wellensteyn';

-- Setze Kundenbezug in Bestellungen auf NULL oder entferne den Datensatz
UPDATE BESTELLUNG SET KUNDENNR = NULL WHERE KUNDENNR = 123;

-- Lösche Kundendaten
DELETE FROM KUNDE WHERE KUNDENNR = 123;

-- Optional: Lösche Adresse, wenn sie nur von diesem Kunden verwendet wurde
DELETE FROM ADRESSE WHERE ADRESSENR = ... AND NOT EXISTS (
    SELECT 1 FROM KUNDE WHERE ADRESSNR = ADRESSE.ADRESSENR
    );

COMMIT;







-- ---------------------------------------------------------------------------------------------------------------------
-- Custom box:

-- find ingredients:

SELECT
    z.ZUTATNR,
    z.BEZEICHNUNG
FROM ZUTAT z
WHERE BEZEICHNUNG IN ('ei', '%milch%');

SELECT
    z.ZUTATNR,
    z.BEZEICHNUNG
FROM ZUTAT z
WHERE LOWER(BEZEICHNUNG) LIKE '%milch%'
OR LOWER(BEZEICHNUNG) LIKE '%ei%'





-- ---------------------------------------------------------------------------------------------------------------------
SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
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
                WHERE e2.BEZEICHNUNG = 'frutarisch' -- the one to edit
            ) AND e.TYP = 'ernährungsart'
        )
    )
) AND r.REZEPTNR IN (
    SELECT REZEPTNR FROM ( -- workaround for: This version of MySQL doesn't yet support 'LIMIT & IN/ALL/ANY/SOME subquery'
                             SELECT
                                 r.REZEPTNR
                             FROM REZEPT r
                                      JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
                                      JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
                             GROUP BY r.REZEPTNR
                             HAVING COUNT(DISTINCT rz.ZUTATNR) < 12
                             LIMIT 4
                         ) AS LIMIT_REZEPTE
)



SELECT
    r.*,
    z.BEZEICHNUNG,
    rz.MENGE,
    rz.EINHEIT
FROM REZEPT r
         JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
         JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
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
                WHERE e2.BEZEICHNUNG = 'frutarisch' -- the one to edit
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
    HAVING COUNT(DISTINCT rz.ZUTATNR) < 12
) LIMIT 10;



SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR  AND r.REZEPTNR IN (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR  HAVING COUNT(DISTINCT rz.ZUTATNR) < :ingredientLimit);



