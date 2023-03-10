import {useAuthHook} from "../auth.hook";
import React, {useCallback, useEffect, useState} from "react";
import {BrowserRouter as Router, Route, Routes, useNavigate} from "react-router-dom";
import {User} from "../interfaces";
import {Login} from "../login/Login";
import {Dashboard} from "./dashboard/Dashboard";
import {Navbar} from "./navbar/Navbar";

export function Portal() {
    const [user, setUser] = useState<User>()
    const {getUser, logout} = useAuthHook()

    const handleLogout = useCallback(async () => {
        await logout()
        setUser(undefined)
        navigate('/login')
    }, [logout])

    const navigate = useNavigate()
    useEffect(() => {
        (async () => {
            try {
                const user = await getUser()
                setUser(user)
            } catch (e) {
                navigate('/login')
            }
        })()
    }, [getUser])

    return (
        <div>
            {user && (
                <div>
                    <Navbar user={user} logout={handleLogout}/>
                    <Dashboard/>
                </div>
            )}
        </div>)
}