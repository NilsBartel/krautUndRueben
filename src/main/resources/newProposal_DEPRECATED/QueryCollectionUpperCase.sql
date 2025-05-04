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
    WHERE e.TYP = 'ernährungsart' AND e.PRIORITAET < 1
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


# Rezepte nach Kalorien Filtern
SELECT *
FROM REZEPT r
WHERE r.REZEPTNR IN (
    SELECT rz.REZEPTNR FROM REZEPT_ZUTAT rz
        WHERE (
              SELECT SUM(rz.MENGE * (z.KALORIEN / 100)) FROM ZUTAT z
              WHERE z.ZutatNr IN (
                  SELECT rz2.ZutatNr FROM REZEPT_ZUTAT rz2
                  WHERE rz2.RezeptNr = rz.REZEPTNR
              )
                        # Hier den gewünschten Kalorienfilter angeben
    ) > 3550
);


SELECT
    r.*,
    z.BEZEICHNUNG
FROM REZEPT r
    JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR
    JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR
GROUP BY r.REZEPTNR, r.NAME, r.BESCHREIBUNG, r.VORGEHEN, z.BEZEICHNUNG
HAVING SUM((z.KALORIEN / 100.0) * rz.MENGE) < 800;

# TODO - Durchschnittliche Nährwerte einer Box
SELECT * FROM;




SELECT SUM(rz.MENGE * z.KALORIEN / 100) FROM ZUTAT z
join krautundrueben.REZEPT_ZUTAT rz on z.ZUTATNR = rz.ZUTATNR
WHERE z.ZutatNr IN (
    SELECT rz2.ZutatNr FROM REZEPT_ZUTAT rz2
    WHERE rz2.RezeptNr = rz.REZEPTNR
    )

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






