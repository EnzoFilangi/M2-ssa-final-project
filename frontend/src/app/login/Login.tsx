import {ChangeEvent, FormEvent, useCallback, useEffect, useState} from "react";
import {useAuthHook} from "../auth.hook";
import {useNavigate} from 'react-router-dom';

export function Login() {
    const {login} = useAuthHook();
    const [username, setUsername] = useState('');
    const [error, setError] = useState(false);
    const [password, setPassword] = useState('');
    const navigate = useNavigate();


    const changeUsername = (event: ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value)
    }
    const changePassword = (event: ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value)
    }
    const onSubmit = useCallback(async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const response = await login({username, password});
        if (response.status === 401) {
            setError(true)
        } else {
            navigate('/');
        }
    }, [username, password]);

    return (
        <div>
            <section className="bg-gray-50 dark:bg-gray-900">
                <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                    <a href="#" className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white">
                        MyEfrei
                    </a>
                    <div
                        className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                                Se connecter
                            </h1>
                            <form className="space-y-4 md:space-y-6" onSubmit={onSubmit}
                                  onChange={() => setError(false)}>
                                <div>
                                    <label htmlFor="username"
                                           className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                                        Nom d'utilisateur
                                    </label>
                                    {error ? (
                                        <input type="text" name="username" id="username"
                                               className="bg-red-100 border border-red-300 text-gray-900 sm:text-sm rounded-lg focus:ring-red-600 focus:border-red-600 block w-full p-2.5 dark:bg-gray-700 dark:border-red-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-red-500 dark:focus:border-red-500"
                                               placeholder="" onChange={changeUsername} required/>) : (
                                        <input type="text" name="username" id="username"
                                               className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                               placeholder="" onChange={changeUsername} required/>)}
                                </div>
                                <div>
                                    <label htmlFor="password"
                                           className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Mot
                                        de passe</label>
                                    {error ? (
                                        <input type="password" name="password" id="password" placeholder="••••••••"
                                               className="bg-red-100 border border-red-500 text-gray-900 sm:text-sm rounded-lg focus:ring-red-600 focus:border-red-600 block w-full p-2.5 dark:bg-gray-700 dark:border-red-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-red-500"
                                               required onChange={changePassword}/>) : (
                                        <input type="password" name="password" id="password" placeholder="••••••••"
                                               className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                               required onChange={changePassword}/>)}

                                </div>
                                <button type="submit"
                                        className="w-full text-white bg-blue-500 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">
                                    Se connecter
                                </button>
                                {error && (
                                    <div className="text-red-500 text-sm">Le nom d'utilisateur ou le mot de passe sont
                                        incorrects</div>
                                )}
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}