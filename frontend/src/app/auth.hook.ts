import {User} from "./interfaces";
import {useCallback, useContext, useState} from "react";
import {EnvironmentContext} from "../environment/environment.context";

export function useAuthHook() {
    const apiUrl = useContext(EnvironmentContext).apiUrl;

    const getUser = useCallback(async () => {
        const request = await fetch(`${apiUrl}/authentication/session`, {
            credentials: 'include',
            mode: 'cors',
        })
        return await request.json() as User;

    }, []);

    const login = useCallback(async ({username, password}: { username: string, password: string }) => {
        await fetch(`${apiUrl}/authentication/session`, {
            method: 'POST',
            body: JSON.stringify({username, password}),
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'Content-Length': JSON.stringify({username, password}).length.toString(),
                'Accept': 'application/json'
            }
        })
    }, []);

    const logout = useCallback(async () => {
        await fetch(`${apiUrl}/authentication/session`, {
            method: 'DELETE',
            credentials: 'include',
            mode: 'cors',
        })
    }, []);

    return {getUser, login, logout};
}