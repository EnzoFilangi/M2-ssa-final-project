import {Company, Internship, Student, User} from "../../interfaces";
import {useUser} from "../../user.hook";
import {useCallback, useEffect, useState} from "react";
import Modal from 'react-bootstrap/Modal';
import {AddUserModal} from "./AddUserModal";
import {AddInternshipModal} from "./CreateInternshipModal";
import {InternshipDetail} from "./InternshipDetails";

export function Dashboard() {
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [showCreateInternshipModal, setShowCreateInternshipModal] = useState(false);
    const [showShowInternshipDetail, setShowShowInternshipDetail] = useState(false);
    const [selectedStudentId, setSelectedStudentId] = useState<string>('');
    const [selectedInternshipCompany, setSelectedInternshipCompany] = useState<Company | undefined>(undefined);
    const [selectedInternshipId, setSelectedInternshipId] = useState<string>('');
    const {students, editInternship, editStudent, addStudent, addInternship, editCompany, deleteInternship, deleteStudent} = useUser();
    const handleEditInternship = useCallback(async (internship: Internship) => {
        await editInternship(internship)
    }, [])
    const handleEditStudent = useCallback(async (student: Student) => {
        await editStudent(student)
    }, []);

    const handleCloseCreateModal = () => setShowCreateModal(false);
    const handleShowCreateModal = () => setShowCreateModal(true);

    const handleCloseCreateInternshipModal = () => setShowCreateInternshipModal(false);
    const handleShowCreateInternshipModal = (id: string) => {
        setSelectedStudentId(id)
        setShowCreateInternshipModal(true)
    };

    const handleCloseShowInternshipDetail = () => setShowShowInternshipDetail(false);
    const handleShowShowInternshipDetail = (company: Company, internshipId: string) => {
        setSelectedInternshipCompany(company)
        setSelectedInternshipId(internshipId)
        setShowShowInternshipDetail(true)
    }

    return (
        <>
            <div className="flex justify-center mt-10 flex-col items-center">
                <div className="overflow-auto w-[80%]">
                    <table className="table-auto overflow-scroll w-[200%]">
                        <thead>
                        <tr className="text-left border-b border-gray-300 p-5">
                            <th className="px-5">#</th>
                            <th className="px-5">Groupe</th>
                            <th className="px-5">Nom</th>
                            <th className="px-5">Prénom</th>
                            <th className="px-5">C.d.C</th>
                            <th className="px-5">Fiche visite</th>
                            <th className="px-5">Fiche éval</th>
                            <th className="px-5">Sondage Web</th>
                            <th className="px-5">Rapport rendu</th>
                            <th className="px-5">Soutenance</th>
                            <th className="px-5">Planifier</th>
                            <th className="px-5">Faite</th>
                            <th className="px-5">Début</th>
                            <th className="px-5">Fin</th>
                            <th className="px-5">Note Tech</th>
                            <th className="px-5">Note Com</th>
                            <th className="px-5">#</th>
                        </tr>
                        </thead>
                        <tbody>
                        {students.map((student, index) => (
                                <tr key={student.id} className="border-b border-gray-300 p-5">
                                    <td className="px-5">
                                        {
                                            student.internship ? (
                                                <button className="text-blue-500"
                                                        onClick={() => handleShowShowInternshipDetail(student.internship?.company!, student.internship?.id!)}>Détails
                                                </button>) : (
                                                <button className="text-blue-500"
                                                        onClick={() => handleShowCreateInternshipModal(student.id)}>Créer
                                                    stage
                                                </button>)
                                        }

                                    </td>
                                    <td className="px-5">
                                        <input className="my-4 bg-gray-50 text border border-gray-300" type="text"
                                               defaultValue={student.group}
                                               onBlur={(e) => {
                                                   handleEditStudent({
                                                       ...student,
                                                       group: e.target.value
                                                   })
                                               }}
                                        />
                                    </td>
                                    <td className="px-5">
                                        <input className="bg-gray-50 text border border-gray-300" type="text"
                                               defaultValue={student.lastName}
                                               onBlur={(e) => {
                                                   handleEditStudent({
                                                       ...student,
                                                       lastName: e.target.value
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input className="bg-gray-50 text border border-gray-300" type="text"
                                               defaultValue={student.firstName}
                                               onBlur={(e) => {
                                                   handleEditStudent({
                                                       ...student,
                                                       firstName: e.target.value
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.cahierDesCharges ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       cahierDesCharges: !student.internship?.cahierDesCharges
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.ficheVisite ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       ficheVisite: !student.internship?.ficheVisite
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.ficheEvaluationEntreprise ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       ficheEvaluationEntreprise: !student.internship?.ficheEvaluationEntreprise
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.sondageWeb ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       sondageWeb: !student.internship?.sondageWeb
                                                   })
                                               }}
                                        />
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.rapportRendu ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       rapportRendu: !student.internship?.rapportRendu
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.soutenance ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       soutenance: !student.internship?.soutenance
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.visitePlanifiee ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       visitePlanifiee: !student.internship?.visitePlanifiee
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship} type="checkbox"
                                               checked={student.internship?.visiteFaite ?? false}
                                               onChange={() => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       visiteFaite: !student.internship?.visiteFaite
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship}
                                               className="bg-gray-50 text border border-gray-300" type="date"
                                               defaultValue={student.internship?.startDate}
                                               onChange={(e) => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       startDate: e.target.value
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship}
                                               className="bg-gray-50 text border border-gray-300" type="date"
                                               defaultValue={student.internship?.endDate}
                                               onChange={(e) => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       endDate: e.target.value
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship}
                                               className="bg-gray-50 text border border-gray-300" type="number"
                                               min="0" max="20"
                                               defaultValue={student.internship?.noteTech}
                                               onChange={(e) => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       noteTech: Number(e.target.value)
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <input disabled={!student.internship}
                                               className="bg-gray-50 text border border-gray-300" type="number"
                                               min="0" max="20"
                                               defaultValue={student.internship?.noteCom}
                                               onChange={(e) => {
                                                   handleEditInternship({
                                                       ...student.internship as Internship,
                                                       noteCom: Number(e.target.value)
                                                   })
                                               }}/>
                                    </td>
                                    <td className="px-5">
                                        <button className="text-red-500"
                                                onClick={() => deleteStudent(student.id)}>Supprimer étudiant
                                        </button>
                                    </td>
                                </tr>
                            )
                        )}
                        <tr>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <button className="text-blue-500" onClick={handleShowCreateModal}>Ajouter</button>
            </div>
            <Modal show={showCreateModal} onHide={handleCloseCreateModal}>
                <AddUserModal hideModal={handleCloseCreateModal}
                              addStudent={addStudent}></AddUserModal>
            </Modal>
            <Modal show={showCreateInternshipModal} onHide={handleCloseCreateInternshipModal}>
                <AddInternshipModal hideModal={handleCloseCreateInternshipModal}
                                    addInternship={addInternship} studentId={selectedStudentId}
                />
            </Modal>

            <Modal show={showShowInternshipDetail} onHide={handleCloseShowInternshipDetail}>
                {!!selectedInternshipCompany?.id && (
                    <InternshipDetail hideModal={handleCloseShowInternshipDetail}
                                      editCompany={editCompany} company={selectedInternshipCompany}
                                      deleteInternship={deleteInternship}
                                      internshipId={selectedInternshipId}
                    />)
                }
            </Modal>

        </>
    )
}