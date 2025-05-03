import { useState } from 'react';
import "./BoxCreator.css"

function BoxCreator({ boxData }) {
    const [filters, setFilters] = useState({
        rezepte: 1,
        kohlenhydrate: 'low',
        kalorien: 'low',
        proteine: 'low',
        fett: 'low',
        co2: 'low',
        zutaten: 1,
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilters((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = () => {
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
                        <option value="low">Low</option>
                        <option value="high">High</option>
                    </select>
                </label>
                <br />

                <label>
                    Kalorien:
                    <select name="kalorien" value={filters.kalorien} onChange={handleChange}>
                        <option value="low">Low</option>
                        <option value="high">High</option>
                    </select>
                </label>
                <br />

                <label>
                    Proteine:
                    <select name="proteine" value={filters.proteine} onChange={handleChange}>
                        <option value="low">Low</option>
                        <option value="high">High</option>
                    </select>
                </label>
                <br />

                <label>
                    Fett:
                    <select name="fett" value={filters.fett} onChange={handleChange}>
                        <option value="low">Low</option>
                        <option value="high">High</option>
                    </select>
                </label>
                <br />

                <label>
                    CO₂-Bilanz:
                    <select name="co2" value={filters.co2} onChange={handleChange}>
                        <option value="low">Low</option>
                        <option value="high">High</option>
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
        </div>

    );
}

export default BoxCreator;

