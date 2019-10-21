import React, {useEffect, useState} from 'react';
import {update} from 'ramda';
import {allUsers, compare} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {Repo, User} from '../../../api/types';

const Compare = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [repos, setRepos] = useState<Repo[]>([]);
    const [languages, setLanguages] = useState<String[]>([]);
    const [loadingUsers, setLoadingUsers] = useState<boolean>(false);

    const [selectedUsers, setSelectedUsers] = useState<[User[], User[]]>([[], []]);
    const [loadingComparison, setLoadingComparison] = useState<boolean>(false);

    const setUser = (n: number) => (user: User) => {
        console.log(selectedUsers);
        const array = selectedUsers[n];
        array.push(user);
        const newUsers = update(n, array, selectedUsers);
        setSelectedUsers(newUsers as [User[], User[]]);
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
            if (selectedUsers[0] === [] || selectedUsers[1] === []) return;
            setLoadingComparison(true);
            const result = await compare(
                                selectedUsers[0].map(x => x.id).join(","),
                                selectedUsers[1].map(x => x.id).join(",")
                            );

            if (result.status === 500) {
                AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
            } else {
                const {data} = await result.json();
                setRepos(data.sharedRepos);
                setLanguages(data.sharedLanguages);
            }
            setLoadingComparison(false);
        };

        getComparison();
    }, [selectedUsers]);

    console.log(selectedUsers);
    return <Layout loadingUsers={loadingUsers} loadingComparison={loadingComparison} selectedUsers={selectedUsers}
                   users={users} repos={repos} languages={languages} setUser={setUser}/>;
};

export default Compare;