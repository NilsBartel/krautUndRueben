import BoxCreator from '../components/BoxCreator';
import Header from '../components/Header';
import { useState } from 'react';

function Veggi() {
    const boxData = {
        name: "Veggi Box",
        id: "vegetarisch"
    }

    return (
        <>
            <Header />
            <BoxCreator boxData={boxData} />
        </>
    );
}

export default Veggi;

