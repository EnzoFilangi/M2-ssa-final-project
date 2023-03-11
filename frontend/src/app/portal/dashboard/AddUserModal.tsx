import Modal from "react-bootstrap/Modal";
import {useMemo, useRef, useState} from "react";

interface AddUserModalProps {
    hideModal: () => void
    addStudent: (user: { firstName: string, lastName: string, group: string }) => Promise<void>
}

export function AddUserModal({hideModal, addStudent}: AddUserModalProps) {
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [group, setGroup] = useState('')

    const firstNameInput = useRef(null)
    const lastNameInput = useRef(null)
    const groupInput = useRef(null)

    const handleAddStudent = async () => {
        await addStudent({firstName, lastName, group})
        hideModal();
    }

    const disabled = useMemo(() => !firstName.length && !lastName.length && !group.length, [firstName, lastName, group])

    return (<>
            <Modal.Header>
                <Modal.Title>Ajouter un étudiant</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="flex flex-col">
                    <label htmlFor="firstName">Prénom</label>
                    <input ref={firstNameInput} id="firstName" type="text" value={firstName}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                           onChange={e => setFirstName(e.target.value)}/>
                </div>
                <div className="flex flex-col">
                    <label htmlFor="lastName">Nom</label>
                    <input ref={lastNameInput} id="lastName" type="text" value={lastName}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                           onChange={e => setLastName(e.target.value)}/>
                </div>
                <div className="flex flex-col">
                    <label htmlFor="group">Groupe</label>
                    <input ref={groupInput} id="group" type="text" value={group}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                           onChange={e => setGroup(e.target.value)}/>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <button className="text-red-500 mr-5" onClick={hideModal}>Annuler</button>
                <button disabled={disabled} onClick={handleAddStudent} className="enabled:text-blue-500">Ajouter</button>
            </Modal.Footer>
        </>
    )
}