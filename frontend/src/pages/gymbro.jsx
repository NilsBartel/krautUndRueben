import BoxCreator from '../components/BoxCreator';
import Header from '../components/Header';
import { useState } from 'react';

function Gymbro() {
    const boxData = {
        name: "Gym Bro Box",
        id: "frutarisch"
    }

    return (
        <>
            <Header />
            <BoxCreator boxData={boxData} />
        </>
    );
}

export default Gymbro;

