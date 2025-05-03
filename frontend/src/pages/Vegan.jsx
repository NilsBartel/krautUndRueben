import BoxCreator from '../components/BoxCreator';
import Header from '../components/Header';
import { useState } from 'react';

function Vegan() {
    const boxData = {
        name: "Vegan Box",
        id: "vegan"
    }

    return (
        <>
            <Header />
            <BoxCreator boxData={boxData} />
        </>
    );
}

export default Vegan;

