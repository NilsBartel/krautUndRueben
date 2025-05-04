import { useState } from 'react';
import Header from '../components/Header';

const ingredientsList = [
    'Banane', 'Zucchini', 'Zwiebel', 'Tomate', 'Schalotte', 'Karotte', 'Kartoffel', 'Rucola', 'Lauch',
    'Knoblauch', 'Basilikum', 'Süßkartoffel', 'Schnittlauch', 'Apfel', 'Vollmilch. 3.5%',
    'Mozzarella', 'Butter', 'Ei', 'Wiener Würstchen', 'Tofu-Würstchen', 'Couscous', 'Gemüsebrühe',
    'Kichererbsen', 'Spinat', 'Lachs', 'Lasagne Nudeln', 'Mehl', 'Sahne', 'Parmesan', 'Zitrone'
];

function Customized() {
    const [selectedIngredients, setSelectedIngredients] = useState({});
    const apiPath = "http://localhost:8080"
    const handleCheckboxChange = (e) => {
        const { name, checked } = e.target;
        setSelectedIngredients((prev) => ({
            ...prev,
            [name]: checked ? 1 : undefined
        }));
    };

    const handleQuantityChange = (e, ingredient) => {
        const value = parseInt(e.target.value, 10);
        setSelectedIngredients((prev) => ({
            ...prev,
            [ingredient]: isNaN(value) || value < 1 ? undefined : value
        }));
    };

    const handleSubmit = () => {
        const selected = Object.entries(selectedIngredients)
            .filter(([_, qty]) => qty)
            .map(([name, qty]) => {
                return {
                    name: name,
                    anzahl: qty
                }
            });

        if (selected.length === 0) {
            alert('Bitte wähle mindestens eine Zutat aus.');
            return;
        }

        console.log("Bestellung:", selected);
        addToCart();
        fetch(apiPath + "/recipe", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                zutaten: selected
            })
        })
            .then((response) => {
                return response.json().then(json => {
                    return response.ok ? json : Promise.reject(json);
                });
            })
            .then((response) => {
                console.log(response);
                addToCart()
                //setRecipies(response.recipes);

                // saveToken(response.token);
                // navigate("/");
            })
            .catch((response) => {
                console.log(response);
                alert(response.error)
            });

        function addToCart() {

            let cart = JSON.parse(localStorage.getItem("cart"));
            if (cart == null) {
                cart = { items: [] };
            }

            cart.items.push({
                name: "Customized Box",
                id: "custom",
                key: Date.now()
            });

            localStorage.removeItem("cart");
            localStorage.setItem("cart", JSON.stringify(cart));
            window.dispatchEvent(new Event('storage'))
        }
    };

    return (
        <>
            <Header />
            <div style={{ display: 'flex', padding: '20px' }}>
                {/* Linke Seite: Bild */}
                <div style={{ flex: 1 }}>
                    <img src="boxui.png" alt="Box UI" style={{ width: '60%' }} />
                </div>

                {/* Rechte Seite: Zutaten-Auswahl */}
                <div style={{ flex: 1, padding: '20px' }}>
                    <h2>Zutaten auswählen</h2>
                    <div
                        style={{
                            display: 'grid',
                            gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
                            gap: '10px'
                        }}
                    >
                        {ingredientsList.map((ingredient) => (
                            <div key={ingredient}>
                                <label>
                                    <input
                                        type="checkbox"
                                        name={ingredient}
                                        checked={!!selectedIngredients[ingredient]}
                                        onChange={handleCheckboxChange}
                                    />
                                    {` ${ingredient}`}
                                </label>
                                {selectedIngredients[ingredient] !== undefined && (
                                    <input
                                        type="number"
                                        min="1"
                                        value={selectedIngredients[ingredient]}
                                        onChange={(e) => handleQuantityChange(e, ingredient)}
                                        style={{ marginLeft: '10px', width: '60px' }}
                                    />
                                )}
                            </div>
                        ))}
                    </div>
                    <br />
                    <button onClick={handleSubmit} style={{ marginTop: '20px', padding: '10px 20px' }}>
                        Bestellung abschicken
                    </button>
                </div>
            </div>
        </>
    );
}

export default Customized;
