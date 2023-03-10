import Modal from "react-bootstrap/Modal";
import {useMemo, useRef, useState} from "react";
import {Internship, NewInternship} from "../../interfaces";


interface AddUserModalProps {
    hideModal: () => void
    addInternship: (internship: NewInternship) => Promise<void>
    studentId: string
}

export function AddInternshipModal({hideModal, addInternship, studentId}: AddUserModalProps) {
    const [name, setName] = useState('')
    const [address, setAddress] = useState('')
    const [startDate, setStartDate] = useState('')
    const [endDate, setEndDate] = useState('')

    const companyNameInput = useRef(null)
    const addressInput = useRef(null)
    const startDateInput = useRef(null)
    const endDateInput = useRef(null)

    const handleAddInternship = async () => {
        await addInternship({name, address, startDate, endDate, studentId})
        hideModal();
    }

    const disabled = useMemo(() => !name.length && !address.length && !startDate.length && !endDate.length, [name, address, startDate, endDate])

    return (<>
            <Modal.Header>
                <Modal.Title>Ajouter un utilisateur</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="flex flex-col">
                    <label htmlFor="firstName">Nom de l'entreprise</label>
                    <input ref={companyNameInput} id="companyName" type="text" value={name}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                           onChange={e => setName(e.target.value)}/>
                </div>
                <div className="flex flex-col">
                    <label htmlFor="lastName">Adresse de l'entreprise</label>
                    <input ref={addressInput} id="companyAdress" type="text" value={address}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                           onChange={e => setAddress(e.target.value)}/>
                </div>
                <div className="flex flex-col">
                    <label htmlFor="group">Date de début</label>
                    <input ref={startDateInput} id="startDate" type="date" value={startDate}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                           onChange={e => setStartDate(e.target.value)}/>
                </div>
                <div className="flex flex-col">
                    <label htmlFor="group">Date de fin</label>
                    <input ref={endDateInput} id="endDate" type="date" value={endDate}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                           onChange={e => setEndDate(e.target.value)}/>
                </div>

            </Modal.Body>
            <Modal.Footer>
                <button className="text-red-500 mr-5" onClick={hideModal}>Annuler</button>
                <button disabled={disabled} onClick={handleAddInternship}>Créer</button>
            </Modal.Footer>
        </>
    )
}