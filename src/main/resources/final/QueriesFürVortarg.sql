select *
from BESTELLUNG
order by BESTELLDATUM;

select
    BR.*,
    R.NAME
from BESTELLUNG_REZEPT BR
join REZEPT R on R.REZEPTNR = BR.REZEPTNR
where BESTELLNR = 1014;

select *
from LOGIN;

select *
from KUNDE;