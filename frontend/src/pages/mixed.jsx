import BoxCreator from '../components/BoxCreator';
import Header from '../components/Header';
import { useState } from 'react';

function Mixed() {
    const boxData = {
        name: "Mixed Box",
        id: "mixed"
    }

    return (
        <>
            <Header />
            <BoxCreator boxData={boxData} />
        </>
    );
}

export default Mixed;

