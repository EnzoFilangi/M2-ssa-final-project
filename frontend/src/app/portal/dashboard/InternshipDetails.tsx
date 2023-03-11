import Modal from "react-bootstrap/Modal";
import {useMemo, useRef, useState} from "react";
import {Company} from "../../interfaces";


interface AddUserModalProps {
    hideModal: () => void
    editCompany: (company: Company) => Promise<void>
    deleteInternship: (id: string) => Promise<void>
    internshipId: string
    company: Company
}

export function InternshipDetail({hideModal, editCompany, company, deleteInternship, internshipId}: AddUserModalProps) {
    const [name, setName] = useState(company.name)
    const [address, setAddress] = useState(company.address)

    const companyNameInput = useRef(null)
    const addressInput = useRef(null)

    const handleEditCompany = async () => {
        await editCompany({name, address, id: company.id})
        hideModal();
    }

    const handleDeleteInternship = async () => {
        await deleteInternship(internshipId)
        hideModal();
    }

    const disabled = useMemo(() => !name.length && !address.length, [name, address])

    return (<>
            <Modal.Header>
                <Modal.Title>Détail du stage</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="flex flex-col">
                    <label htmlFor="firstName">Nom de l'entreprise</label>
                    <input ref={companyNameInput} id="companyName" type="text" value={name}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                           onChange={e => setName(e.target.value)}/>
                </div>
                <div className="flex flex-col">
                    <label htmlFor="lastName">Adresse de l'entreprise</label>
                    <input ref={addressInput} id="companyAdress" type="text" value={address}
                           className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                           onChange={e => setAddress(e.target.value)}/>
                </div>

            </Modal.Body>
            <Modal.Footer>
                <button className="text-red-500 mr-5" onClick={hideModal}>Annuler</button>
                <button className="text-red-500 mr-5" onClick={handleDeleteInternship}>Supprimer le stage</button>
                <button className="enabled:text-blue-500" disabled={disabled} onClick={handleEditCompany}>Sauvegarder</button>
            </Modal.Footer>
        </>
    )
}