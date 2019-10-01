import React, {useEffect, useState} from 'react';
import {update} from 'ramda';
import {allUsers, compare} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {Repo, User} from '../../../api/types';

const Compare = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [repos, setRepos] = useState<Repo[]>([]);
    const [loadingUsers, setLoadingUsers] = useState<boolean>(false);

    const [selectedUsers, setSelectedUsers] = useState<[User?, User?]>([undefined, undefined]);
    const [loadingComparison, setLoadingComparison] = useState<boolean>(false);

    const setUser = (n: number) => (user: User) => {
        const newUsers = update(n, user, selectedUsers);
        setSelectedUsers(newUsers as [User?, User?]);
    };

    useEffect(() => {
        const getUsers = async () => {
            setLoadingUsers(true);

            const result = await allUsers();

            if (result.status !== 200) {
                AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
            } else {
                const users: User[] = (await result.json()).data;
                setUsers(users);
            }

            setLoadingUsers(false);
        };

        getUsers();
    }, []);

    useEffect(() => {
        const getComparison = async () => {
            if (selectedUsers[0] === undefined || selectedUsers[1] === undefined) return;

            setLoadingComparison(true);
            const result = await compare(selectedUsers[0].id, selectedUsers[1].id);

            if (result.status !== 200) {
                AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
            } else {
                const {data} = await result.json();
                setRepos(data);
            }
            setLoadingComparison(false);
        };

        getComparison();
    }, [selectedUsers]);

    console.log(selectedUsers);
    return <Layout loadingUsers={loadingUsers} loadingComparison={loadingComparison} selectedUsers={selectedUsers}
                   users={users} repos={repos} setUser={setUser}/>;
};

export default Compare;