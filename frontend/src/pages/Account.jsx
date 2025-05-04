import { useState, useEffect } from 'react';
import { resolvePath, useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import { deleteToken, getToken, hasToken } from '../lib/token';

function Account() {
    const navigate = useNavigate();

    useEffect(() => {
        if (!hasToken()) {
            navigate("/login");
        }
    }, []);

    const apiPath = "http://localhost:8080"

    function deleteAccount() {
        fetch(apiPath + "/account/delete", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                token: getToken()
            })
        })
            .then((response) => {
                return response.json().then(json => {
                    return response.ok ? json : Promise.reject(json);
                });
            })
            .then((response) => {
                console.log(response);

                deleteToken();
                navigate("/");
            })
            .catch((response) => {
                console.log(response);
                alert(response.error)
            });
    }



    return (
        <>
            <Header />
            <button onClick={() => {
                deleteToken();
                navigate("/");
            }}>Abmelden</button>
            <button onClick={deleteAccount}>Account löschen</button>
        </>
    );
}

export default Account;
