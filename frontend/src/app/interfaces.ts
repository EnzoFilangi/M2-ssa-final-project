export interface User {
    id: string;
    firstName: string;
    lastName: string;
}

export interface Student {
    firstName: string;
    group: string;
    id: string;
    lastName: string;
    internship?: Internship;
}

export interface Internship {
    cahierDesCharges: boolean;
    company: Company;
    endDate: string;
    ficheEvaluationEntreprise: boolean;
    ficheVisite: boolean;
    noteCom: number;
    noteTech: number;
    rapportRendu: boolean;
    sondageWeb: boolean;
    soutenance: boolean;
    startDate: string;
    visiteFaite: boolean;
    visitePlanifiee: boolean;
    id: string;
}

export interface Company {
    address: string;
    id: string;
    name: string;
}

export type NewInternship =
    Pick<Internship, 'endDate' | 'startDate'>
    & Pick<Company, 'address' | 'name'>
    & { studentId: string }

export type NewInternshipResponse = Internship & { student: Omit<Student, 'internship'> }