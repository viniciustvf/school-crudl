import { Course } from './course';
import { Person } from './person';

export interface Registration {
    id?: string;
    personId?: string;
    personName?: string;
    person?: Person
    courseId?: string;
    courseName?: string;
    course?: Course;
    registrationDate: string;
}
