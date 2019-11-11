export enum Role {
    anon = 'ANONYMOUS',
    user = 'USER',
    admin = 'ADMIN'
}

export type User = {
    id: string
    role: Role
    email: string
    lastLogin: number
    favRepos: Repo[]
    favoriteLanguage: string
}

export type MePayload = {
    message: string
    error: string | null
    data: User
}

export type Repo = {
    id: string
    name: string
    numForks: number
    numStars: number
    language: string
    favCount: number | undefined
    added: number | undefined 
}

export type ContributorGithub = {
    id: number;
    login: string;
    avatarUrl: string;
    htmlUrl: string;
    contributions: number;
}

export type Parameter = 'forks' | 'stars' | 'size' | 'topics' | 'followers'
export type Operator = 'gt' | 'geq' | 'lt' | 'leq'
export type Filter = {
    param: Parameter
    op: Operator
    value: number
}

export type SearchReposPayload = {
    message: string
    error: string | null
    data: Repo[]
}

export type Commit = {
    sha: string
    message: string
    url: string
    date: string
    authorName: string
    authorEmail: string
}

export type StatusPayload = {
    message: string
    error: string | null
}
