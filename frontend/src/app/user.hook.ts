import {useCallback, useContext, useEffect, useState} from "react";
import {EnvironmentContext} from "../environment/environment.context";
import {Company, Internship, NewInternship, NewInternshipResponse, Student} from "./interfaces";

export function useUser() {
    const apiUrl = useContext(EnvironmentContext).apiUrl;

    const [students, setStudents] = useState<Student[]>([])
    const getInternship = useCallback(async (id: string) => {
        const response = await fetch(`${apiUrl}/students/${id}/internships`, {
            credentials: "include",
            mode: "cors"
        })
        const internship = (await response.json())[0] as Internship;
        if (internship) {
            internship.startDate = new Date(internship.startDate).toISOString().slice(0, 10);
            internship.endDate = new Date(internship.endDate).toISOString().slice(0, 10);
        }
        return internship;
    }, [])
    const getAll = useCallback(async () => {
        const response = await fetch(`${apiUrl}/students`, {
            credentials: "include",
            mode: "cors",
        })
        const data = await response.json() as Pick<Student, 'id' | 'firstName' | 'lastName' | 'group'>[];
        const newStudents: Student[] = [];
        for (const student of data) {
            const newStudent = {internship: await getInternship(student.id), ...student} as Student;
            newStudents.push(newStudent);
        }
        return newStudents;
    }, [])

    const editInternship = useCallback(async (internship: Internship) => {
        internship.startDate = new Date(internship.startDate).toISOString().slice(0, 10);
        internship.endDate = new Date(internship.endDate).toISOString().slice(0, 10);
        const body = JSON.stringify(internship);
        const response = await fetch(`${apiUrl}/internships/${internship.id}`, {
            body,
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Content-Length": body.length.toString(),
            },
            method: "PUT",
            mode: "cors"
        })
        if (response.status === 204) {
            setStudents((students) => {
                const newStudents = [...students];
                const studentIndex = newStudents.findIndex((student) => student.internship?.id === internship.id);
                newStudents[studentIndex] = {...newStudents[studentIndex], internship};
                return newStudents;
            })
        }
    }, [students]);

    const editStudent = useCallback(async (student: Student) => {
        const body = JSON.stringify(student);
        const response = await fetch(`${apiUrl}/students/${student.id}`, {
            body,
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Content-Length": body.length.toString(),
            },
            method: "PUT",
            mode: "cors"
        })
        if (response.status === 204) {
            setStudents((students) => {
                const newStudents = [...students];
                const studentIndex = newStudents.findIndex((student) => student.id === student.id);
                newStudents[studentIndex] = {...student, ...newStudents[studentIndex]};
                return newStudents;
            })
        }
    }, [students]);


    const addStudent = async (student: Pick<Student, 'group' | 'firstName' | 'lastName'>) => {
        const body = JSON.stringify(student);
        const response = await fetch(`${apiUrl}/students`, {
            body,
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Content-Length": body.length.toString(),
            },
            method: "POST",
            mode: "cors"
        })
        if (response.status === 201) {
            const newStudent = await response.json() as Student;
            setStudents((students) => {
                const newStudents = [...students];
                newStudents.push(newStudent);
                return newStudents;
            })
        }
    }

    const createCompany = async (company: Pick<Company, 'name' | 'address'>) => {
        const body = JSON.stringify(company);
        const response = await fetch(`${apiUrl}/companies`, {
            body,
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Content-Length": body.length.toString(),
            },
            method: "POST",
            mode: "cors"
        })
        return await response.json() as Company;

    }

    const addInternship = async (internship: NewInternship) => {
        const fullInternship: Omit<Internship, 'id' | 'company'> & {companyId: string, studentId: string} = {
            cahierDesCharges: false,
            companyId: (await createCompany({name: internship.name, address: internship.address})).id,
            ficheEvaluationEntreprise: false,
            ficheVisite: false,
            noteCom: 0,
            noteTech: 0,
            rapportRendu: false,
            sondageWeb: false,
            soutenance: false,
            visiteFaite: false,
            visitePlanifiee: false,
            ...internship
        }
        const body = JSON.stringify(fullInternship);
        const response = await fetch(`${apiUrl}/internships`, {
            body,
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Content-Length": body.length.toString(),
            },
            method: "POST",
            mode: "cors"
        })
        if (response.status === 201) {
            const newInternship = await response.json() as NewInternshipResponse;
            newInternship.startDate = new Date(newInternship.startDate).toISOString().slice(0, 10);
            newInternship.endDate = new Date(newInternship.endDate).toISOString().slice(0, 10);
            setStudents((students) => {
                const newStudents = [...students];
                const studentIndex = newStudents.findIndex((student) => student.id === newInternship.student.id);
                newStudents[studentIndex] = {...newStudents[studentIndex], internship: newInternship};
                return newStudents;
            })
        }
    }

    const editCompany = async (company: Company) => {
        const body = JSON.stringify(company);
        const response = await fetch(`${apiUrl}/companies/${company.id}`, {
            body,
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Content-Length": body.length.toString(),
            },
            method: "PUT",
            mode: "cors"
        })
        if (response.status === 204) {
            setStudents((students) => {
                const newStudents = [...students];
                const studentIndex = newStudents.findIndex((student) => student.internship?.company.id === company.id) ;
                newStudents[studentIndex] = {...newStudents[studentIndex], internship: {...newStudents[studentIndex].internship as Internship, company}};
                return newStudents;
            })
        }
    }

    const deleteInternship = async (internshipId: string) => {
        const response = await fetch(`${apiUrl}/internships/${internshipId}`, {
            credentials: "include",
            method: "DELETE",
            mode: "cors"
        })
        if (response.status === 204) {
            setStudents((students) => {
                const newStudents = [...students];
                const studentIndex = newStudents.findIndex((student) => student.internship?.id === internshipId);
                newStudents[studentIndex] = {...newStudents[studentIndex], internship: undefined};
                return newStudents;
            })
        }
    }

    const deleteStudent = async (studentId: string) => {
        const response = await fetch(`${apiUrl}/students/${studentId}`, {
            credentials: "include",
            method: "DELETE",
            mode: "cors"
        })
        if (response.status === 204) {
            setStudents((students) => {
                const newStudents = [...students];
                const studentIndex = newStudents.findIndex((student) => student.id === studentId);
                newStudents.splice(studentIndex, 1);
                return newStudents;
            })
        }
    }

    useEffect(() => {
        (async () => {
            setStudents(await getAll())
        })()
    }, [getAll])

    return {
        students,
        editInternship,
        editStudent,
        addStudent,
        addInternship,
        editCompany,
        deleteInternship,
        deleteStudent
    }
}