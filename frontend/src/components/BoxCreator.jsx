import { useState } from 'react';
import "./BoxCreator.css"
import RecipieDisplay from './RecipeDisplay';

function BoxCreator({ boxData }) {
    const [filters, setFilters] = useState({
        rezepte: 1,
        kohlenhydrate: 'low',
        kalorien: 'niedrig',
        proteine: 'niedrig',
        fett: 'niedrig',
        co2: 'niedrig',
        zutaten: 1,
    });
    const [recipies, setRecipies] = useState([]);
    const [showRecipes, setShowRecipes] = useState(false);

    const apiPath = "http://localhost:8080"


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilters((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = () => {
        fetch(apiPath + "/recipe", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                ernährungsart: boxData.id,
                kohlenhydrate: filters.kohlenhydrate,
                ingredientLimit: filters.zutaten,
                protein: filters.proteine,
                amount: filters.rezepte,
                fat: filters.fett,
                co2: filters.co2


            })
        })
            .then((response) => {
                return response.json().then(json => {
                    return response.ok ? json : Promise.reject(json);
                });
            })
            .then((response) => {
                console.log(response);
                setRecipies(response.recipes);
                setShowRecipes(true);
            })
            .catch((response) => {
                console.log(response);
                alert(response.error)
            });
        console.log('Bestellung:', filters);
    };

    return (
        <div style={{ display: 'flex', padding: '20px' }}>
            <div style={{ flex: 1 }}>
                <img src=" boxui.png" alt="Box UI" style={{ width: '60%' }} />
            </div>

            <div style={{ flex: 1, padding: '20px' }}>
                <h2>{boxData.name}</h2>

                <label>
                    Anzahl Rezepte:
                    <input
                        type="number"
                        name="rezepte"
                        value={filters.rezepte}
                        onChange={handleChange}
                        min="1"
                    />
                </label>
                <br />

                <label>
                    Kohlenhydrate:
                    <select name="kohlenhydrate" value={filters.kohlenhydrate} onChange={handleChange}>
                        <option value="low">niedrig</option>
                        <option value="high">Hoch</option>
                    </select>
                </label>
                <br />

                <label>
                    Kalorien:
                    <select name="kalorien" value={filters.kalorien} onChange={handleChange}>
                        <option value="low">niedrig</option>
                        <option value="high">Hoch</option>
                    </select>
                </label>
                <br />

                <label>
                    Proteine:
                    <select name="proteine" value={filters.proteine} onChange={handleChange}>
                        <option value="low">niedrig</option>
                        <option value="high">Hoch</option>
                    </select>
                </label>
                <br />

                <label>
                    Fett:
                    <select name="fett" value={filters.fett} onChange={handleChange}>
                        <option value="low">niedrig</option>
                        <option value="high">Hoch</option>
                    </select>
                </label>
                <br />

                <label>
                    CO₂-Bilanz:
                    <select name="co2" value={filters.co2} onChange={handleChange}>
                        <option value="low">niedrig</option>
                        <option value="high">Hoch</option>
                    </select>
                </label>
                <br />

                <label>
                    Anzahl Zutaten:
                    <input
                        type="number"
                        name="zutaten"
                        value={filters.zutaten}
                        onChange={handleChange}
                        min="1"
                    />
                </label>
                <br /><br />

                <button onClick={handleSubmit}>Bestellung abschicken</button>

            </div>
            <RecipieDisplay hidden={!showRecipes} boxData={boxData} recipes={recipies} />

            {/* <RecipieDisplay hidden={true} boxData={boxData} recipes={[{ name: "deine mom" }, { name: "meine mom" }, { name: "ihre mutter" }]} /> */}
        </div>

    );
}

export default BoxCreator;

