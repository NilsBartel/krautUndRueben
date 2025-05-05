# Auswahl aller Zutaten eines Rezepts
SELECT * FROM rezept_zutat rz
JOIN zutat z
ON rz.ZutatNr = z.ZutatNr
# Hier beliebige RezeptNr einfügen
WHERE rz.RezeptNr IN (1);

# Auswahl aller Rezepte einer bestimmten Ernährungskategorie
SELECT * FROM rezept r
WHERE r.RezeptNr NOT IN (
	SELECT rz.RezeptNr FROM rezept_zutat rz
	WHERE rz.ZutatNr IN (
		SELECT ze.ZUTATNR FROM zutat_ernaehrungskategorie ze
		JOIN zutat z
		ON ze.ZUTATNR = z.ZUTATNR
		# Hier gewählte Ernährungskategorie angeben
		WHERE ze.ERNAEHRUNGSKATNR != 4 AND ze.ERNAEHRUNGSKATNR IN (
			SELECT e.ERNAEHRUNGSKATNR FROM ernaehrungskategorie e
			# Hier Typ und Priorität der gewählten Ernährungskategorie angeben
			WHERE e.TYP = 'ernährungsart' AND e.PRIORITAET < 1
		)
	)
);

# Rezepte nach verwendeten Zutaten filtern
SELECT * FROM rezept r
WHERE r.RezeptNr IN (
	SELECT rz.RezeptNr FROM rezept_zutat rz
	# Hier beliebige ZutatenNr angeben
	WHERE rz.ZutatNr IN (1013, 1015)
);

# TODO - Berechnung der durschnittlichen Nährwerte einer Bestellung


# Rezepte nach Kalorien Filtern
SELECT * FROM rezept r
WHERE r.REZEPTNR IN (
	SELECT rz.REZEPTNR FROM rezept_zutat rz
	WHERE (
		SELECT SUM(rz.MENGE * z.KALORIEN / 100) FROM zutat z
		WHERE z.ZutatNr IN (
			SELECT rz2.ZutatNr FROM rezept_zutat rz2
			WHERE rz2.RezeptNr = rz.REZEPTNR
		)
	# Hier den gewünschten Kalorienfilter angeben
	) > 100000
);

# TODO - Durchschnittliche Nährwerte einer Box
SELECT * FROM 










