import React, {useMemo} from 'react'
import {BuildInformation, EnvironmentContext} from './environment/environment.context';
import './App.css'
import {parseISO} from 'date-fns';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import {Login} from "./app/login/Login";
import {Portal} from "./app/portal/Portal";

const apiUrl = import.meta.env.VITE_API_URL as string;
const rawBuildInfo = {
    date: import.meta.env.VITE_BUILD_DATE as string,
    sha: import.meta.env.VITE_COMMIT_SHA as string,
};

function App() {
    const buildInfo = useMemo<BuildInformation>(() => {
        return {
            buildDate: rawBuildInfo.date ? parseISO(rawBuildInfo.date) : undefined,
            sha: rawBuildInfo.sha ?? undefined,
        };
    }, []);
    return (
        <EnvironmentContext.Provider value={{apiUrl, buildInfo}}>
            <Router>
                <Routes>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/" element={<Portal/>}/>
                </Routes>
            </Router>
        </EnvironmentContext.Provider>
    )
}

export default App
