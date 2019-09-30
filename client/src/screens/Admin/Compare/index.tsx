import React, {useEffect, useState} from 'react';
import {allUsers, compare} from '../../../api';
import {removeFromFavorites} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {User, SearchReposPayload, Repo} from '../../../api/types';

const Compare = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [repos, setRepos] = useState<Repo[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);

    const getUsers = async () => {
        setLoading(true);

        const result = await allUsers();

        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        } else {
            const users: User[] = (await result.json()).data;
            setUsers(users);
        }

        setLoading(false);
    };

    const compareUsers = async (id1: string, id2: string) => {
        setLoading(true);
        const result = await compare(id1, id2);
        const {data}: SearchReposPayload = await result.json();
        setRepos(data);
        setLoading(false);
    }

    useEffect(() => {
        getUsers();
    }, []);

    return <Layout error={error} loading={loading} users={users} repos={repos} compareFunction={compareUsers}/>;
};

export default Compare;