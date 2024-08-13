import { Registration } from './registration';

export interface Course {
    id?: string;
    name: string;
    totalVacancies: number;
    init: string;
    end: string;
    minimumStudentAge: number;
    registrations?: Registration[];
}