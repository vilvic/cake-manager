export interface CakesListResponse {
    payload: Cake[]
    errors: Error[]
}

export interface AddCakeResponse {
    payload: Cake
    errors: Error[]
}

export interface Cake {
    id: string
    title: string
    description: string
    imageUrl: string
}

export interface Error {
    field: string
    message: string
}

export enum View {
    CakesList,
    AddCake
}