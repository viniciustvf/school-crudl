import { PersonType } from "./enums/personType";

export interface Person {
    id?: string;
    name: string;
    cpf: string;
    password?: string;
    birthDate: string;
    personType: PersonType;
}
